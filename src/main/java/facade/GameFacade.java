package facade;

import db.DatabaseConnection;
import decorator.*;
import model.factory.GrammarQuestionFactory;
import model.factory.QuestionFactory;
import model.factory.VocabularyQuestionFactory;
import model.questions.Question;
import strategy.*;
import observer.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameFacade {
    private QuestionFactory grammarFactory;
    private QuestionFactory vocabularyFactory;
    private RepetitionStrategy repetitionStrategy;
    private Question currentQuestion;
    private Subject observers;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    private int[] cooldowns;
    private int[] wrongAnswerCounts;

    private int currentQuestionIndex = -1;
    private GameComponent gamification;

    public GameFacade() {
        grammarFactory = new GrammarQuestionFactory();
        vocabularyFactory = new VocabularyQuestionFactory();
        gamification = new StreakDecorator(new BonusScoreDecorator(new BaseGame()));
        observers = new QuestionSubject();
        observers.addObserver(new ConsoleObserver());
    }
    public void setRepetitionStrategy(RepetitionStrategy strategy) {
        this.repetitionStrategy = strategy;
    }

    public void startGame(String testType) {
        int totalQuestions = getTotalQuestions(testType);
        if (totalQuestions <= 0) {
            currentQuestion = null;
            return;
        }

        cooldowns = new int[totalQuestions];
        wrongAnswerCounts = new int[totalQuestions];

        correctAnswers = 0;
        incorrectAnswers = 0;
        currentQuestionIndex = -1;

        for (int i = 0; i < totalQuestions; i++) {
            cooldowns[i] = 0;
            wrongAnswerCounts[i] = 0;
        }

        nextQuestion(testType);
    }

    private void loadQuestion(String testType, int index) {
        if (index < 0) {
            currentQuestion = null;
            return;
        }

        if (testType.equalsIgnoreCase("Grammar")) {
            currentQuestion = grammarFactory.createQuestion(index);
        } else {
            currentQuestion = vocabularyFactory.createQuestion(index);
        }

        currentQuestionIndex = index;

        if (currentQuestion != null) {
            observers.notifyObservers(testType + " question displayed (index " + index + ")");
        }
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void nextQuestion(String testType) {
        if (cooldowns == null || isTestComplete()) {
            currentQuestion = null;
            return;
        }

        int n = cooldowns.length;

        for (int i = 0; i < n; i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
            }
        }

        int nextIndex = -1;
        for (int i = 0; i < n; i++) {
            if (cooldowns[i] == 0) {
                nextIndex = i;
                break;
            }
        }

       if (nextIndex == -1) {
            int bestCooldown = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (cooldowns[i] != -1 && cooldowns[i] < bestCooldown) {
                    bestCooldown = cooldowns[i];
                    nextIndex = i;
                }
            }

            if (nextIndex != -1) {
                cooldowns[nextIndex] = 0;
            }
        }


        if (nextIndex == -1) {
            currentQuestion = null;
            return;
        }

        loadQuestion(testType, nextIndex);
    }

    public void checkAnswer(String selectedAnswer) {
        if (currentQuestion == null || cooldowns == null) return;
        if (currentQuestionIndex < 0 || currentQuestionIndex >= cooldowns.length) return;

        int i = currentQuestionIndex;
        int wrongCount = wrongAnswerCounts[i];

        if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {

            if (cooldowns[i] != -1) {
                correctAnswers++;
            }


            StringBuilder msg = new StringBuilder();
            msg.append("Correct answer! +10 XP\n");

            gamification.onCorrect();

            String event = gamification.getEventMessage();
            if (event != null) {
                msg.append(event).append("\n");
            }

            msg.append("[Observer] Total score: ").append(gamification.getScore()).append(" XP");

            observers.notifyObservers(msg.toString());

            cooldowns[i] = -1;
            wrongAnswerCounts[i] = 0;

        } else {
            wrongCount++;
            wrongAnswerCounts[i] = wrongCount;

            gamification.onIncorrect();
            observers.notifyObservers("Incorrect answer! - 5 XP ");
            observers.notifyObservers("Total score:" + gamification.getScore() + "XP");

            if (wrongCount >= 3) {
                if (cooldowns[i] != -1) {
                    incorrectAnswers++;
                }
                cooldowns[i] = -1;
                observers.notifyObservers("Question " + i + " removed after 3 incorrect attempts.");
                observers.notifyObservers("Total score:" + gamification.getScore() + " XP");
                return;
            }

            int delay = repetitionStrategy.calculateRepetitionDelay(wrongCount);
            if (delay < 0) delay = 0;
            cooldowns[i] = delay;
        }
    }


    public boolean isTestComplete() {
        if (cooldowns == null) return false;

        for (int value : cooldowns) {
            if (value != -1) return false;
        }
        return true;
    }


    public String getTestResult() {
        return "Correct Answers: " + correctAnswers +
                "\nIncorrect Answers: " + incorrectAnswers;
    }

    private int getTotalQuestions(String testType) {
        int total = 0;
        String query = testType.equalsIgnoreCase("Grammar") ?
                "SELECT COUNT(*) FROM grammar_questions" :
                "SELECT COUNT(*) FROM vocabulary_questions";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void reset() {

        correctAnswers = 0;
        incorrectAnswers = 0;

        gamification = new StreakDecorator(
                new BonusScoreDecorator(
                        new BaseGame()
                ));

        if (cooldowns != null) {
            for (int i = 0; i < cooldowns.length; i++) {
                cooldowns[i] = 0;
                wrongAnswerCounts[i] = 0;
            }
        }

        currentQuestionIndex = -1;
        currentQuestion = null;

        observers.notifyObservers("");
    }


    public int getScore() {
        return gamification.getScore();
    }

}
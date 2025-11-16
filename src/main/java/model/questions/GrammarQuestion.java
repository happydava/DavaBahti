package model.questions;

import java.util.List;

public class GrammarQuestion implements Question {
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public GrammarQuestion(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }


    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getQuestionText() {
        return questionText;
    }


}

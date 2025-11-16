package model.builder;

import model.questions.GrammarQuestion;
import model.questions.Question;

import java.util.List;

public class GrammarQuestionBuilder implements QuestionBuilder {

    private String questionText;
    private List<String> options;
    private String correctAnswer;

    @Override
    public QuestionBuilder setQuestionText(String text) {
        this.questionText = text;
        return this;
    }

    @Override
    public QuestionBuilder setOptions(List<String> options) {
        this.options = options;
        return this;
    }

    @Override
    public QuestionBuilder setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
        return this;
    }

    @Override
    public QuestionBuilder setImagePath(String path) {
        return this;
    }

    @Override
    public Question build() {
        return new GrammarQuestion(questionText, options, correctAnswer);
    }
}

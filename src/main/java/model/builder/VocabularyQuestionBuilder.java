package model.builder;

import model.questions.Question;
import model.questions.VocabularyQuestion;

import java.util.List;

public class VocabularyQuestionBuilder implements QuestionBuilder {

    private String word;
    private String imagePath;
    private List<String> options;
    private String correctAnswer;

    @Override
    public QuestionBuilder setQuestionText(String text) {
        this.word = text;
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
        this.imagePath = path;
        return this;
    }

    @Override
    public Question build() {
        return new VocabularyQuestion(word, imagePath, options, correctAnswer);
    }
}

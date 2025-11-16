package model.questions;

import java.util.List;

public class VocabularyQuestion implements Question {
    private String imagePath;
    private List<String> options;
    private String correctAnswer;

    public VocabularyQuestion(String word, String imagePath, List<String> options, String correctAnswer) {
        this.imagePath = imagePath;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }


    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getQuestionText() {
        return "What is this?";
    }
}

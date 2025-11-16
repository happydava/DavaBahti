package model.questions;

import java.util.List;

public interface Question {
    String getCorrectAnswer();
    List<String> getOptions();
    String getQuestionText();
}

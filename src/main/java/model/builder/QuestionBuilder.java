package model.builder;

import model.questions.Question;
import java.util.List;

public interface QuestionBuilder {
    QuestionBuilder setQuestionText(String text);
    QuestionBuilder setOptions(List<String> options);
    QuestionBuilder setCorrectAnswer(String answer);
    QuestionBuilder setImagePath(String path); // для Vocabulary
    Question build();
}

import java.util.ArrayList;
import java.util.List;

public class LessonModule {
    private List<WordLesson> wordLessons = new ArrayList<>();
    private List<WordLesson> grammarTests = new ArrayList<>();

    public void addWordLesson(String word, String translation) {
        wordLessons.add(new WordLesson(word, translation, "", new String[]{}, "")); // Используем конструктор с 5 параметрами
    }

    public void addGrammarTest(String question, String correctAnswer) {
        grammarTests.add(new WordLesson(question, correctAnswer, "", new String[]{}, "")); // Используем конструктор с 5 параметрами
    }

    public List<WordLesson> getWordLessons() {
        return wordLessons;
    }

    public List<WordLesson> getGrammarTests() {
        return grammarTests;
    }
}

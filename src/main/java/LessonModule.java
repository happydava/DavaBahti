import java.util.ArrayList;
import java.util.List;

public class LessonModule {
    private List<WordLesson> wordLessons = new ArrayList<>();
    private List<WordLesson> grammarTests = new ArrayList<>();

    public void addWordLesson(String spanishWord, String englishTranslation) {
        wordLessons.add(new WordLesson(spanishWord, englishTranslation));
    }

    public void addGrammarTest(String question, String correctAnswer) {
        grammarTests.add(new WordLesson(question, correctAnswer));
    }

    public List<WordLesson> getWordLessons() {
        return wordLessons;
    }

    public List<WordLesson> getGrammarTests() {
        return grammarTests;
    }
}
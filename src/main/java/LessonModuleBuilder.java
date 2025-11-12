public interface LessonModuleBuilder {
    void buildWordLesson(String spanishWord, String englishTranslation);
    void buildGrammarTest(String question, String correctAnswer);
    LessonModule getModule();
}

public class LessonBuilder implements LessonModuleBuilder {
    private LessonModule module;

    public LessonBuilder() {
        this.module = new LessonModule();
    }

    @Override
    public void buildWordLesson(String spanishWord, String englishTranslation) {
        module.addWordLesson(spanishWord, englishTranslation);
    }

    @Override
    public void buildGrammarTest(String question, String correctAnswer) {
        module.addGrammarTest(question, correctAnswer);
    }

    @Override
    public LessonModule getModule() {
        return module;
    }
}

public class SimpleSpacedRepetition implements SpacedRepetitionAlgorithm {
    @Override
    public int calculateNextReviewInterval(int currentInterval) {
        return currentInterval + 1; // Простая реализация
    }
}

public class ProgressTracker implements ProgressObserver {
    @Override
    public void updateProgress(String message) {
        System.out.println("Progress update: " + message);
        // Здесь можно добавить логику сохранения прогресса
    }
}

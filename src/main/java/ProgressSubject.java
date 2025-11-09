import java.util.ArrayList;
import java.util.List;

public class ProgressSubject {
    private List<ProgressObserver> observers = new ArrayList<>();

    public void addObserver(ProgressObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProgressObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (ProgressObserver observer : observers) {
            observer.updateProgress(message);
        }
    }
}

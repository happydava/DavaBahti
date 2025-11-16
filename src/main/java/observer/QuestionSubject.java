package observer;

import java.util.ArrayList;
import java.util.List;

public class QuestionSubject implements Subject {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String msg) {
        for (Observer o : observers) {
            o.update(msg);
        }
    }
}

package decorator;

public abstract class GameComponent {
    public abstract void onCorrect();
    public abstract void onIncorrect();
    public int getScore() {
        return 0;
    }

    public String getEventMessage() {
        return null;
    }
}

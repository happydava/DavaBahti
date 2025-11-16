package decorator;

public abstract class GamificationDecorator extends GameComponent {

    protected GameComponent game;

    public GamificationDecorator(GameComponent game) {
        this.game = game;
    }

    @Override
    public void onCorrect() {
        game.onCorrect();
    }

    @Override
    public void onIncorrect() {
        game.onIncorrect();
    }

    @Override
    public int getScore() {
        return game.getScore();
    }

    @Override
    public String getEventMessage() {
        return game.getEventMessage();
    }


}

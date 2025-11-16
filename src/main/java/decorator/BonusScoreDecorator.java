package decorator;

public class BonusScoreDecorator extends GamificationDecorator {

    public BonusScoreDecorator(GameComponent game) {
        super(game);
    }

    @Override
    public void onCorrect() {
        super.onCorrect();
        game.onCorrect();
    }

    @Override
    public void onIncorrect() {
        super.onIncorrect();
    }
}

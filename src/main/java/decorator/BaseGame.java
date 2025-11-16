package decorator;

public class BaseGame extends GameComponent {

    protected int score = 0;

    @Override
    public void onCorrect() {
        score += 5;
    }

    @Override
    public void onIncorrect() {
        score -= 5;
        if( score < 0 ){
            score = 0;
        }
    }

    @Override
    public int getScore() {
        return score;
    }

}

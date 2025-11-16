package decorator;

public class StreakDecorator extends GamificationDecorator {

    private int streak = 0;
    private String eventMessage;

    public StreakDecorator(GameComponent game) {
        super(game);
    }

    @Override
    public void onCorrect() {
        streak++;

        if (streak == 3) {
            eventMessage = "[Observer] 🔥 Streak bonus! +20 XP";
            game.onCorrect();
            game.onCorrect();
        } else {
            eventMessage = null;
        }

        super.onCorrect();
    }

    @Override
    public void onIncorrect() {
        streak = 0;
        eventMessage = null;
        super.onIncorrect();
    }

    @Override
    public String getEventMessage() {
        return eventMessage;
    }
}

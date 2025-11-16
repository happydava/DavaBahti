package strategy;

public class HardRepetitionStrategy implements RepetitionStrategy {

    @Override
    public int calculateRepetitionDelay(int incorrectAnswers) {
        if (incorrectAnswers == 1) return 3;
        if (incorrectAnswers == 2) return 2;
        return 0;
    }
}

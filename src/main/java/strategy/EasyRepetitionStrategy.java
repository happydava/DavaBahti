package strategy;

public class EasyRepetitionStrategy implements RepetitionStrategy {

    @Override
    public int calculateRepetitionDelay(int incorrectAnswers) {
        if (incorrectAnswers == 1) return 2;
        if (incorrectAnswers == 2) return 2;
        return 0;
    }
}

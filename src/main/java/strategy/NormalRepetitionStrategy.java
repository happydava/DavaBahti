package strategy;

public class NormalRepetitionStrategy implements RepetitionStrategy {

    @Override
    public int calculateRepetitionDelay(int incorrectAnswers) {
        if (incorrectAnswers == 1) {
            return 2;
        } else if (incorrectAnswers == 2) {
            return 1;
        } else {
            return 0;
        }
    }

}
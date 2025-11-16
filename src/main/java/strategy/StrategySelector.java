package strategy;

public class StrategySelector {

    public static RepetitionStrategy getStrategy(String name) {
        switch (name.toLowerCase()) {
            case "easy":
                return new EasyRepetitionStrategy();
            case "normal":
                return new NormalRepetitionStrategy();
            case "hard":
                return new HardRepetitionStrategy();
            default:
                return new NormalRepetitionStrategy();
        }
    }
}

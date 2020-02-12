package segelzwerg.sporttooolbox.IUnits.pace;

public class PaceFactory {
    public static Pace createPaceFromUnit(float pace, String paceUnit) {
        switch (paceUnit) {
            case "minutesPerKilometer":
                return new MinutesPerKilometer(pace);
            case "minutesPerHundredMeters":
                return new MinutesPerHundredMeters(pace);
        }
        throw new IllegalArgumentException("The unit was invalid: " + paceUnit);
    }
}

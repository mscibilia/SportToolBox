package segelzwerg.sporttooolbox.web.pace;

import lombok.Getter;
import lombok.ToString;
import segelzwerg.sporttooolbox.iunits.pace.MinutesPerHundredMeters;
import segelzwerg.sporttooolbox.iunits.pace.MinutesPerKilometer;
import segelzwerg.sporttooolbox.iunits.pace.Pace;

@Getter
@ToString

public class PacePresenter {
    private MinutesPerHundredMeters minutesPerHundredMeters;
    private MinutesPerKilometer minutesPerKilometer;

    public PacePresenter(Pace pace) {
        minutesPerKilometer = (MinutesPerKilometer) pace.toMinutesPerKilometer();
        minutesPerHundredMeters = (MinutesPerHundredMeters) pace.toMinutesPerHundredMeters();
    }
}

package segelzwerg.sporttooolbox.iunits;

import lombok.EqualsAndHashCode;
import segelzwerg.sporttooolbox.iunits.pace.Pace;
import segelzwerg.sporttooolbox.iunits.speed.Speed;

/**
 * Represents distance in kilometers and meters
 */
@EqualsAndHashCode
public class Distance {
    /**
     * factor to convert miles into yards
     */
    public static final double MILES_TO_YARDS = 1760.0;
    /**
     * factor to convert fathoms to nautical miles
     */
    public static final double FATHOMS_TO_NAUTICAL_MILES = 1 / 1013.3333334;
    private final float kilometer;
    private final float meter;

    /**
     * Constructor
     *
     * @param kilometer - the distance in kilometers
     * @throws IllegalArgumentException - if kilometer is negative
     */
    public Distance(float kilometer) {
        this(kilometer, 0);
        if (kilometer < 0.0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructor
     *
     * @param kilometer - the distance in kilometers
     * @param meter     - and meters.  meters will be added to the kilometers
     */
    public Distance(float kilometer, float meter) {
        double total = Math.round((kilometer + meter / 1000f) * 1000f) / 1000f;
        int absKilometer = (int) Math.floor(total);
        float absMeter = Math.round((total - absKilometer) * 1000f * 10f) / 10f;
        this.kilometer = absKilometer;
        this.meter = absMeter;
    }

    public static Distance createWithMajorUnit(int major, String majorUnit) {
        return createWithOtherThanSIUnits(major, 0, majorUnit, "meter");
    }

    /**
     * Factory Method
     *
     * @param major     - the distance in the major unit
     * @param minor     - the distance in the minor unit
     * @param majorUnit - the major unit itself
     * @param minorUnit - the minor unit itself
     * @return Distance
     */
    public static Distance createWithOtherThanSIUnits(int major, int minor, String majorUnit, String minorUnit) {
        float kilometer = (float) convertMajorToKilometer(major, majorUnit);
        float meter = (float) convertMinorToMeter(minor, minorUnit) / 1000f;
        double total = Math.floor((kilometer + meter) * 1000f) / 1000f;
        int absKilometer = (int) Math.floor(total);
        int absMeter = (int) Math.round((total - absKilometer) * 10000) / 10;
        return new Distance(absKilometer, absMeter);
    }

    private static double convertMinorToMeter(float minor, String minorUnit) {
        switch (minorUnit) {
            case "yards":
                return minor * 0.9144;
            case "fathom":
                return minor * 1.8288;
            case "meter":
                return minor;
            default:
                throw new IllegalArgumentException("This is not a valid unit: " + minorUnit);
        }
    }

    private static double convertMajorToKilometer(float major, String majorUnit) {
        switch (majorUnit) {
            case "miles":
                return major * 1.609344;
            case "nautical":
                return major * 1.852;
            case "kilometer":
                return major;
            default:
                throw new IllegalArgumentException("This is not a valid unit: " + majorUnit);
        }
    }

    /**
     * @param distance float miles with decimals
     * @return a distance object
     */
    public static Distance createWithMiles(float distance) {
        int miles = (int) Math.floor(distance);
        int yards = (int) (Math.round((distance - miles) * MILES_TO_YARDS * 100f) / 100f);
        return Distance.createWithOtherThanSIUnits(miles, yards, "miles", "yards");
    }

    /**
     * Add distance
     *
     * @param toAdd distance to add
     * @return new distance
     */
    public Distance addDistance(Distance toAdd) {
        float kilometer = this.kilometer + toAdd.kilometer;
        float meter = this.meter + toAdd.meter;

        return new Distance(kilometer, meter);
    }

    /**
     * Compute speed for specific time
     *
     * @param time Amount of time
     * @return calculated speed
     */
    public Speed computeSpeed(Time time) {
        return time.computeSpeed(kilometer, meter);
    }

    /**
     * Compute pace given a specific time
     *
     * @param time amount of time
     * @return calculated pace
     */
    public Pace computePace(Time time) {
        return time.computePace(kilometer, meter);
    }

    /**
     * computes the time for a given speed
     *
     * @param speed {@link Speed}
     * @return {@link Time}
     */
    public Time computeTime(Speed speed) {
        return speed.computeTime(kilometer, meter);
    }
}

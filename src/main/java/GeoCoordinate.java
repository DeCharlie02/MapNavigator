import java.io.Serializable;
import java.util.Objects;

public class GeoCoordinate implements Serializable {
    private double latitude;
    private double longitude;
    private static final long serialVersionUID = 1L;

    public GeoCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double distanceTo(GeoCoordinate other) {
        final double earthRadius = 6371; // Radius of the earth in km
        double latDiff = Math.toRadians(other.latitude - this.latitude);
        double lonDiff = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c; // Distance in km
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoCoordinate that = (GeoCoordinate) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeoCoordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

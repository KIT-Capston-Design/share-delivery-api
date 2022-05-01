package com.kitcd.share_delivery_api.utils.geometry;

import org.springframework.stereotype.Component;

/**
 * Haversine Formula
 * φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
 * λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2 )
 */

@Component
public class GeometryUtil {

    // baseLocation으로부터 distance만큼 azumuth으로 떨어진 좌표를 계산 // azimuth : 방위각
    public Location calculate(Location baseLocation, Double distance, Double azimuth) {

        Double radianLatitude = toRadian(baseLocation.getLatitude());
        Double radianLongitude = toRadian(baseLocation.getLongitude());
        Double radianAngle = toRadian(azimuth);

        Double distanceRadius = distance / 6371.01;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) + cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude =
                radianLongitude + Math.atan2(
                        sin(radianAngle) * sin(distanceRadius) * cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude)
                );

        longitude = normalizeLongitude(longitude);

        return new Location(toDegree(latitude), toDegree(longitude));

    }

    private Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    private Double normalizeLongitude(Double longitude) {
        return (longitude + 540) % 360 - 180;
    }
}
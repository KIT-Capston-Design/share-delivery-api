package com.kitcd.share_delivery_api.utils.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometriesFactory {

    private static final GeometryFactory factory4326 = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);

    public static Point createPoint(Double pLatitude, Double pLongitude){
        // inverted parameters are OK
        return factory4326.createPoint(new Coordinate(pLongitude, pLatitude));
    }

    public static LineString createLineString(Coordinate[] pCoordsArray){
        return factory4326.createLineString(pCoordsArray);
    }

}
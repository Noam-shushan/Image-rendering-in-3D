package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * interface to get intersection points of ray with some geometry
 * @author Noam Shushan
 */
public interface Intersectable {

    /**
     * Helper class
     * In order to get color for each geometry shape individually
     * within a scene, we want to calculate color
     * at a certain point for the geometry
     */
    public static class GeoPoint{
        /**
         * the geometry that we find the color of a certain point
         */
        public Geometry geometry;
        /**
         * the point on the geometry that we get the color from
         */
        public Point3D point;

        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

    /**
     * find intersections of ray with geometry shape
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<GeoPoint> findGeoIntersections(Ray ray);

    /**
     * find intersections of ray with geometry shape
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

}

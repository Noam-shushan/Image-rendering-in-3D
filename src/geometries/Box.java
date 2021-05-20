package geometries;
import primitives.*;

import java.util.List;

//TODO need to think of this
public class Box extends Geometry{
    //final Point3D[] _points;
    final Polygon[] _corners;

    /**
     *
     * @param points
     */
    public Box(Point3D[] points) {
        if(points == null){
            throw new IllegalArgumentException("no points entered");
        }
        if(points.length != 8){
            throw new IllegalArgumentException("Box most have 8 points");
        }
        //TODO
        _corners = new Polygon[6];
        for(int i = 0; i < 6; i += 2){
            _corners[i] = new Polygon(points[i], points[i+1], points[i+2], points[i+3]);
        }
    }

    /**
     * get the normal that stand on point in some geometry
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    @Override
    public Vector getNormal(Point3D point) {
        //TODO
        return null;
    }

    /**
     * find intersections of ray with geometry shape
     *
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        //TODO
        return null;
    }
}

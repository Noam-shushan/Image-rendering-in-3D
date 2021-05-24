package geometries;
import primitives.*;

import java.util.List;
import static primitives.Util.*;

//TODO need to think of this
public class Box extends Geometry{
    /**
     *
     */
    final Polygon[] _corners;

    private final int NUMBER_OF_CORNERS = 6;
    private final int NUMBER_OF_POINTS = 8;

    /**
     *
     * @param points
     */
    public Box(Point3D... points) {
        if(points == null){
            throw new IllegalArgumentException("no points entered");
        }
        if(points.length != NUMBER_OF_POINTS){
            throw new IllegalArgumentException("Box most have 8 points");
        }
        _corners = new Polygon[NUMBER_OF_CORNERS];
//        for(int i = 0; i < NUMBER_OF_CORNERS; i += 2){
//            _corners[i] = new Polygon(points[i], points[i+1], points[i+2], points[i+3]);
//        }
        _corners[0] = new Polygon(points[0], points[1], points[2], points[3]);
        _corners[1] = new Polygon(points[4], points[5], points[6], points[7]);
        _corners[2] = new Polygon(points[0], points[3], points[7], points[4]);
        _corners[3] = new Polygon(points[0], points[1], points[5], points[4]);
        _corners[4] = new Polygon(points[1], points[2], points[6], points[5]);
        _corners[5] = new Polygon(points[2], points[3], points[7], points[6]);
    }

    /**
     * get the normal that stand on point in some geometry
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    @Override
    public Vector getNormal(Point3D point) {
        for(var corner: _corners){
            Point3D firstPointOfCorner = corner._vertices.get(0);
            if(firstPointOfCorner.equals(point)){
                continue;
            }

            Vector horizontalVecToPlane = point.subtract(firstPointOfCorner);
            Vector normal = corner.getNormal(null);

            if(isZero(normal.dotProduct(horizontalVecToPlane))){
                return normal;
            }
        }

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

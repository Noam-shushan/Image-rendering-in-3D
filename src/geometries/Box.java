package geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

//TODO need to think of this
public class Box extends Geometry{
    /**
     *
     */
    final Polygon[] _corners;

    private double _depth;

    private static final int NUMBER_OF_CORNERS = 6;
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
        _corners[0] = new Polygon(points[0], points[1], points[2], points[3]);
        _corners[1] = new Polygon(points[4], points[5], points[6], points[7]);
        _corners[2] = new Polygon(points[0], points[3], points[7], points[4]);
        _corners[3] = new Polygon(points[0], points[1], points[5], points[4]);
        _corners[4] = new Polygon(points[1], points[2], points[6], points[5]);
        _corners[5] = new Polygon(points[2], points[3], points[7], points[6]);
    }

    public Box(Point3D p1, Point3D p2, Point3D p3, Point3D p4, double depth){
        _corners = new Polygon[NUMBER_OF_CORNERS];
        try{
            _corners[0] = new Polygon(p1, p2, p3, p4);
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("ca not create Box because" + ex.getMessage());
        }
        _depth = depth;
        Vector n = _corners[0]._plane._normal;
        Vector scaleVector = n.scale(_depth);

        Point3D p5 = p1.add(scaleVector);
        Point3D p6 = p2.add(scaleVector);
        Point3D p7 = p3.add(scaleVector);
        Point3D p8 = p4.add(scaleVector);
        _corners[1] = new Polygon(p1, p5, p6, p2);
        _corners[2] = new Polygon(p2, p6, p7, p3);
        _corners[3] = new Polygon(p3, p7, p8, p4);
        _corners[4] = new Polygon(p1, p5, p8, p4);
        _corners[5] = new Polygon(p5, p6, p7, p8);
    }

    @Override
    public Geometry setEmission(Color emission) {
        for(var corner : _corners){
            corner.setEmission(emission);
        }
        return this;
    }

    /**
     * setter for the material of the geometry
     * @param material the new material of the geometry
     * @return this geometry
     */
    @Override
    public Geometry setMaterial(Material material) {
        for(var corner : _corners){
            corner.setMaterial(material);
        }
        return this;
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
     * find intersections of ray with the box
     * @param ray ray that cross the box
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for(var corner : _corners){
            var intersections = corner.findGeoIntersections(ray);
            if(intersections != null){
                if(result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(intersections);
            }
        }
        return result;
    }
}

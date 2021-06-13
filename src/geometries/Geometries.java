package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that represents all kinds of geometries <br/>
 * that have in common that they implement the interface
 * of finding points of intersection with a ray. <br/>
 * using the composite design pattern.
 * @author Noam Shushan
 */
public class Geometries implements Intersectable {

    /**
     * all kind of geometries that implements the Intersectable interface
     */
    private List<Intersectable> _intersectables;

    /**
     * Default constructor for Geometries
     */
    public Geometries() {
        // use LinkedList because the use of this list is only for iterate
        // from the start of the list to the end, and adding new items to her
        _intersectables = new LinkedList();
    }

    /**
     * Constructor for Geometries
     * @param intersectables one or more interfaces to add to the geometries list
     */
    public Geometries(Intersectable... intersectables) {
        this();
        add(intersectables);
    }

    /**
     * Add interfaces to the list of the geometries
     * @param intersectables one or more interfaces to add to the geometries list
     */
    public void add(Intersectable... intersectables){
        for(var item : intersectables){
            _intersectables.add(item);
        }
    }

    /**
     * find intersections of ray with geometry shape
     *
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for(var item : _intersectables){
            List<GeoPoint>  itemPoints = item.findGeoIntersections(ray);
            if(itemPoints != null){
                if(result == null){
                    result = new LinkedList();
                }
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}

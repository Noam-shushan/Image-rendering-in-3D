package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that represents all kinds of geometries
 * that have in common that they implement the interface
 * of finding points of intersection with a ray.
 * using the composite design pattern.
 *
 * @author Noam Shushan
 */
public class Geometries  implements Intersectable {
    private List<Intersectable> _interfaces;

    /**
     * Default constructor for Geometries
     */
    public Geometries() {
        // use LinkedList because the use of this list is only for iterate
        // from the start of the list to the end and adding new items to her
        _interfaces = new LinkedList();
    }

    /**
     * Constructor for Geometries
     * @param interfaces one or more interfaces to add to the geometries list
     */
    public Geometries(Intersectable... interfaces) {
        this();
        add(interfaces);
    }

    /**
     * Add interfaces to the list of the geometries
     * @param interfaces one or more interfaces to add to the geometries list
     */
    public void add(Intersectable... interfaces){
        for(var item : interfaces){
            _interfaces.add(item);
        }
    }

    /**
     * @param ray ray that cross the geometries
     * @return list of intersection points that were found in all the geometries
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for(var item : _interfaces){
            List<Point3D> itemPoints = item.findIntersections(ray);
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

package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries  implements Intersectable {
    private List<Intersectable> _interfaces = null;

    public Geometries() {
        _interfaces = new LinkedList();
    }

    public Geometries(Intersectable... interfaces) {
        _interfaces = new LinkedList();
        add(interfaces);
    }

    public void add(Intersectable... interfaces){
        for(var item : interfaces){
            _interfaces.add(item);
        }
    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
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

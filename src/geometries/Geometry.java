package geometries;

import primitives.*;

/**
 * abstract class for all the geometries shape
 * Provides a normal vector for the geometry, emission color and the material
 * @author Noam Shushan
 */
public abstract class Geometry implements Intersectable {
    /**
     * emission color of any geometry
     */
    private Color _emission = Color.BLACK;
    /**
     * the material of the geometry
     */
    private Material material = new Material();

    /**
     * setter for the emission of the geometry
     * @param emission new emission color to the geometry
     * @return this geometry
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * getter fo the emission if the geometry
     * @return the emission if the geometry
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * getter for the material of the geometry
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter for the material of the geometry
     * @param material the new material of the geometry
     * @return this geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * get the normal that stand on point in some geometry
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point3D point);
}

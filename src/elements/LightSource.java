package elements;

import primitives.*;

/**
 *
 */
public interface LightSource {
    /**
     * get the intensity of the light in relation to the distance from the point
     * @param p The point where the light strikes
     * @return the color of the point
     */
    public Color getIntensity(Point3D p);

    /**
     * get the the direction of the light to the point where its strikes
     * @param p The point where the light strikes
     * @return the direction of the light to the point
     */
    public Vector getL(Point3D p);
}

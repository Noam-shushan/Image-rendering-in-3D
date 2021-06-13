package elements;

import primitives.*;

/**
 * interface for light sources in some scene
 * @author Noam Shushan
 */
public interface LightSource {

    /**
     * get the intensity of the light in relation to the distance from the point
     * @param p The point where the light strikes
     * @return the color of the point
     */
    Color getIntensity(Point3D p);

    /**
     * get the the direction of the light to the point where its strikes
     * @param p The point where the light strikes
     * @return the direction of the light to the point
     */
    Vector getL(Point3D p);

    /**
     * get the distance between the starting point of the light source to some point
     * @param point the point to calculate the distance from
     * @return the distance between light and the point
     */
    double getDistance(Point3D point);
}

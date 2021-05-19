package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional Light with constant light intensity and direction
 * @author Noam Shushan
 */
public class DirectionalLight extends Light implements LightSource{
    /**
     * the direction of the light
     */
    private final Vector _direction;

    /**
     * Constructor for Directional Light
     * @param intensity the light intensity
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * get the intensity of the light in relation to the distance from the point
     * in directional light its the same intensity for all points
     * @param p The point where the light strikes
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * get the the direction of the light to the point where its strikes
     * in directional light its just the distance
     * @param p The point where the light strikes
     * @return the direction of the light to the point
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * get the distance between the starting point of the light source to some point
     * @param point the point to calculate the distance from
     * @return the distance between light and the point
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.MAX_VALUE;
    }
}

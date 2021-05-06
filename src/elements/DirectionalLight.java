package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional Light with constant light intensity and direction
 */
public class DirectionalLight extends Light implements LightSource{
    /**
     * the direction of the light
     */
    private final Vector _direction;

    /**
     * Constructor for Directional Light
     * @param intensity
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction;
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
}

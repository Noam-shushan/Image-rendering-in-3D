package elements;

import primitives.*;

/**
 * this class represent a spot light with direction and intensity of the light
 * and also can make a flashlight by focusing the the light
 * @author Noam Shushan
 */
public class SpotLight extends PointLight{
    /**
     * the direction of the light
     */
    private Vector _direction;
    /**
     * focus the light to get a beam of light that is bounded like a flashlight
     */
    private double _focus = 1;

    /**
     * constructor for SpotLight
     *
     * @param intensity the light intensity
     * @param position  Light start location
     * @param direction the direction of the spot
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * get the intensity of the light in relation to the distance from the point
     * Calculate the light beam by calculating
     * the angle between the spot direction and the point direction
     * @param p The point where the light strikes
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        Vector l = getL(p); // direction to the point
        double angle = _direction.dotProduct(l); // the angle between the spot direction and the point direction
        double factor =  angle > 0 ? angle : 0;

        if(_focus != 1){
            factor = Math.pow(factor, _focus); // Reduces light angles
        }

        return super.getIntensity(p).scale(factor);
    }

    /**
     * setter for the focus
     * @param focus the new focus
     * @return this SpotLight
     */
    public SpotLight setFocus(double focus) {
        _focus = focus;
        return this;
    }
}

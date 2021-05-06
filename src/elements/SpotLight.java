package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{
    /**
     * the direction of the light
     */
    private final Vector _direction;

    /**
     * constructor for SpotLight
     *
     * @param intensity the light intensity
     * @param position  Light start location
     * @param kC        A constance coefficient of attenuation of light
     * @param kI        Coefficient of attenuation of light linear dependence
     * @param kQ        Coefficient of attenuation of light quadratic dependence
     * @param direction the direction of the spot
     */
    public SpotLight(Color intensity, Point3D position, double kC, double kI, double kQ, Vector direction) {
        super(intensity, position, kC, kI, kQ);
        _direction = direction;
    }

    /**
     * get the intensity of the light in relation to the distance from the point
     *
     * @param p The point where the light strikes
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        Vector l = p.subtract(_position);
        double angle = _direction.dotProduct(l);
        if(angle > 0){
            return super.getIntensity(p).scale(angle);
        }
        else{
            return super.getIntensity(p);
        }

    }
}

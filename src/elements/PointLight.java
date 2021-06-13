package elements;

import primitives.*;

/**
 * this class represent a point light like normal lamp. <br/>
 * with point that represent the position of the lamp and <br/>
 * the intensity of the light that depends on some variables related to the pong model
 * @author Noam Shushan
 */
public class PointLight extends Light implements LightSource {

    /**
     * The point from which the light comes out
     */
    protected final Point3D _position;
    /**
     * kC - Its purpose is to ensure that the light is not strengthened but weakened
     */
    private double _kC = 1d;
    /**
     * kL - reduce factor of attenuation of light linear dependence
     */
    private double _kL = 0d;
    /**
     * kQ - reduce factor of attenuation of light quadratic dependence
     */
    private double _kQ = 0d;


    /**
     * constructor for PointLight
     * @param intensity the light intensity
     * @param position Light start location
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * get the intensity of the light in relation to the distance from the point
     * @param p The point where the light strikes
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dist = p.distance(_position);

        if(dist <= 0){
            return getIntensity();
        }

        double factor = (_kC + dist * _kL + (dist * dist) * _kQ);

        return getIntensity().reduce(factor);
    }

    /**
     * get the the direction of the light to the point where its strikes
     * @param p The point where the light strikes
     * @return the direction of the light to the point
     */
    @Override
    public Vector getL(Point3D p) {
        Vector dir = p.subtract(_position);
        return dir.normalized();
    }

    /**
     * get the distance between the starting point of the light source to some point
     * @param point the point to calculate the distance from
     * @return the distance between light and the point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    /**
     * setter for kC reduce factor
     * @param kC new kC
     * @return this PointLight
     */
    public PointLight setKc(double kC){
        _kC = kC;
        return this;
    }

    /**
     * setter for kL reduce factor
     * @param kL new kL
     * @return this PointLight
     */
    public PointLight setKl(double kL) {
        _kL = kL;
        return this;
    }

    /**
     * setter for kQ reduce factor
     * @param kQ new kQ
     * @return this PointLight
     */
    public PointLight setKq(double kQ) {
        _kQ = kQ;
        return this;
    }
}

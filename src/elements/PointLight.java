package elements;

import primitives.*;

/**
 * Model omni-directional point source
 * the light is get out from certain point, and it as some attenuation coefficients of light
 */
public class PointLight extends Light implements LightSource {

    /**
     * The point from which the light comes out
     */
    protected final Point3D _position;

    /**
     * kC - Its purpose is to ensure that the light is not strengthened but weakened
     * kL - Coefficient of attenuation of light linear dependence
     * kQ - Coefficient of attenuation of light quadratic dependence
     */
    private final double _kC, _kL, _kQ;

    /**
     * constructor for PointLight
     * @param intensity the light intensity
     * @param position Light start location
     * @param kC A constance coefficient of attenuation of light
     * @param kL Coefficient of attenuation of light linear dependence
     * @param kQ Coefficient of attenuation of light quadratic dependence
     * @throws IllegalArgumentException if kI <= 0 or kQ <= 0
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        if(kL <= 0 || kC <= 0){
            throw new IllegalArgumentException("kI or kC most be greater the 0");
        }
        _position = position;
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }

    /**
     * get the intensity of the light in relation to the distance from the point
     * @param p The point where the light strikes
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);

        if(d <= 0){
            return getIntensity();
        }

        double t = 1 / (_kC + d * _kL + (d*d) * _kQ);

        return getIntensity().scale(t);
    }

    /**
     * get the the direction of the light to the point where its strikes
     *
     * @param p The point where the light strikes
     * @return the direction of the light to the point
     */
    @Override
    public Vector getL(Point3D p) {
        Vector dir = p.subtract(_position);
        return dir.normalize();
    }
}

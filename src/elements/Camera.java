package elements;
import primitives.*;

import java.util.Objects;

import static primitives.Util.*;

/**
 * this class represent camera with location and view plane
 * @author Noam Shushan
 */
public class Camera {
    /**
     * the location of the camera
     */
    private Point3D _p0;
    /**
     * the 3 directions of the camera
     */
    private final Vector _vRight, _vUp, _vTo;
    /**
     * the distance from the camera to the view plane
     */
    private double _distance;
    /**
     * the width of the view plane
     */
    private double _width;
    /**
     * the height of the view plane
     */
    private double _height;

    /**
     * constructor for camera
     * @param p0 the location of the camera
     * @param vTo the direction to the view plane
     * @param vUp the direction up
     * @throws IllegalArgumentException if vTo and vUp is not orthogonal
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        _p0 = p0;

        if(!isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("The vectors 'up' and and 'to' is not orthogonal");
        }

        _vUp = vUp.normalized();
        _vTo = vTo.normalized();
        _vRight = _vTo.crossProduct(_vUp).normalized();
    }

    /**
     * construct ray through a pixel in the view plane
     * nX and nY create the resolution
     * @param nX number of pixels to x axis
     * @param nY number of pixels to y axis
     * @param j index row in the view plane
     * @param i index column in the view plane
     * @return ray that goes through the pixel (j,i)  Ray(p0, Vi,j)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        Point3D pIJ = Pc;

        // 𝑅𝑦 = ℎ/𝑁𝑦
        double rY = alignZero(_height / nY);
        // 𝑅𝑥 = 𝑤/𝑁x
        double rX = alignZero(_width / nX);

        // 𝑥𝑗 = (𝑗 – (𝑁𝑥 − 1)/2) ∙ 𝑅x
        double xJ = alignZero((j - ((nX - 1) / 2d)) * rX);
        // 𝑦𝑖 = −(𝑖 – (𝑁𝑦 − 1)/2) ∙ 𝑅𝑦
        double yI = alignZero(- (i - ((nY - 1) / 2d)) * rY);

        if (xJ != 0) {
            pIJ = pIJ.add(_vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(_vUp.scale(yI));
        }

        //𝒗𝒊,𝒋 = 𝑷𝒊,𝒋 − 𝑷𝟎
        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ);
    }

    /**
     * moving the camera from her location
     * @param up    delta for _vUp vector
     * @param right delta for _vRight vector
     * @param to    delta for _vTo vector
     * @return this
     */
    public Camera moveCamera(double up, double right, double to) {
        if(!isZero(up)) {
            _p0 = _p0.add(_vUp.scale(up));
        }
        if(!isZero(right)) {
            _p0 = _p0.add(_vRight.scale(right));
        }
        if(!isZero(to)) {
            _p0 = _p0.add(_vTo.scale(to));
        }

        return this;
    }

    /**
     * set the view plane size
     * @param width the width of the view plane
     * @param height the height of the view plane
     * @return this camera (like builder pattern)
     * @throws IllegalArgumentException if width or height equal to 0
     */
    public Camera setViewPlaneSize(double width, double height){
        if(isZero(width) || isZero(height)){
            throw new IllegalArgumentException("width or height cannot be zero");
        }

        _width = width;
        _height = height;
        return this;
    }

    /**
     * set the distance from the camera to the view plane
     * @param distance the distance
     * @return this camera (like builder pattern)
     * @throws IllegalArgumentException if distance = 0
     */
    public Camera setDistance(double distance){
        if(isZero(distance)){
            throw new IllegalArgumentException("distance cannot be zero");
        }

        _distance = distance;
        return this;
    }

    /**
     * get the location of the camera
     * @return the location of the camera
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * get the vector direction to the right
     * @return the vector direction to the right
     */
    public Vector get_vRight() {
        return _vRight;
    }
    /**
     * get the vector direction to up
     * @return the vector direction to up
     */
    public Vector get_vUp() {
        return _vUp;
    }
    /**
     * get the vector direction to the view plane
     * @return the vector direction to the view plane
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     * get the distance from the camera to the view plane
     * @return the distance from the camera to the view plane
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * get the width of the view plane
     * @return the width of the view plane
     */
    public double getWidth() {
        return _width;
    }

    /**
     * get the height of the view plane
     * @return the height of the view plane
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return _p0.equals(camera._p0) && _vUp.equals(camera._vUp) && _vTo.equals(camera._vTo);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "location point = " + _p0 +
                ", direction right = " + _vRight +
                ", direction up = " + _vUp +
                ", direction To = " + _vTo +
                ", distance from view plane = " + _distance +
                ", width of the view plane = " + _width +
                ", height of the view plane = " + _height +
                '}';
    }
}

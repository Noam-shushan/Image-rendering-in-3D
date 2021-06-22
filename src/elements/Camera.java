package elements;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * this class represent camera by location <br/>
 * and directions toward, right and up to the scene that lives in a virtual view plane. <br/>
 * The view plane is represent by height and wight
 * @author Noam Shushan
 */
public class Camera {

    /**
     * the location of the camera
     */
    private Point3D _p0;

    /**
     * the the normal in the right direction
     */
    private final Vector _vRight;

    /**
     * the normal in the up direction
     */
    private final Vector _vUp;

    /**
     * the the normal in the toward the scene
     */
    private Vector _vTo;

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
     * the point on the center of the view plane
     */
    private Point3D _centerPoint;

    /**
     *  the radius of the random point to create around <br/>
     *  the center point of the pixel in the view plane <br/>
     *  to calculate the average color of point on some geometry <br/>
     *  as part of anti aliasing improvement
     */
    private static final double BEAN_RADIUS_FOR_ANTI_ALIASING = 0.45d;

    /**
     * constructor for camera
     * @param p0 the location of the camera
     * @param vTo the direction to the view plane
     * @param vUp the direction up
     * @throws IllegalArgumentException if vTo and vUp is not orthogonal
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("The vectors 'up' and and 'to' is not orthogonal");
        }

        _p0 = p0;
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();
        _vRight = _vTo.crossProduct(_vUp).normalized();
    }

    /**
     * construct ray through a pixel in the view plane
     * nX and nY create the resolution
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j index row in the view plane
     * @param i index column in the view plane
     * @return ray that goes through the pixel (j, i)  Ray(p0, Vi,j)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D pIJ = getCenterOfPixel(nX, nY, j, i); // center point of the pixel

        //Vi,j = Pi,j - P0, the direction of the ray to the pixel(j, i)
        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ);
    }

    /**
     * construct bean of rays through a pixel in the view plane
     * nX and nY create the resolution
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j index row in the view plane
     * @param i index column in the view plane
     * @param numOfRays the number of rays to construct
     * @return list of rays that start in the position of the camera and gos to the pixel
     */
    public List<Ray> constructBeanOfRaysThroughPixel(int nX, int nY, int j, int i, int numOfRays){
        Point3D pIJ = getCenterOfPixel(nX, nY, j, i); // center point of the pixel
        Vector vIJ = pIJ.subtract(_p0); // the direction of the ray through the pixel
        Ray ray = new Ray(_p0, vIJ);

        // create the bean from the random point around the center point of the pixel
        return ray.createBean(pIJ, numOfRays, BEAN_RADIUS_FOR_ANTI_ALIASING);
    }

    /**
     * get the center point of the pixel in the view plane
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j index row in the view plane
     * @param i index column in the view plane
     * @return the center point of the pixel
     */
    private Point3D getCenterOfPixel(int nX, int nY, int j, int i){
        // calculate the ratio of the pixel by the height and by the width of the view plane
        // the ratio Ry = h/Ny, the height of the pixel
        double rY = alignZero(_height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(_width / nX);

        // Xj = (j - (Nx -1)/2) * Rx
        double xJ = alignZero((j - ((nX - 1d) / 2d)) * rX);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double yI = alignZero(- (i - ((nY - 1d) / 2d)) * rY);

        Point3D pIJ = _centerPoint;

        if (xJ != 0d) {
            pIJ = pIJ.add(_vRight.scale(xJ));
        }
        if (yI != 0d) {
            pIJ = pIJ.add(_vUp.scale(yI));
        }
        return pIJ;
    }

    /**
     * moving the camera from her location
     * @param newPosition the new position of the camera
     * @param newPointOfView new point of view of the camera
     * @return the new camera from the new position to the new point of view
     */
    public Camera moveCamera(Point3D newPosition, Point3D newPointOfView) {
        // the new vTo of the the camera
        Vector new_vTo = newPointOfView.subtract(newPosition).normalized();
        // the angle between the new vTo and the old
        double theta = new_vTo.dotProduct(_vTo);
        // axis vector for the rotation
        Vector k = _vTo.crossProduct(new_vTo).normalize();

        _vTo = new_vTo;
        _p0 = newPosition;

        return rotateCamera(theta, k);
    }

    /**
     * Rotate the camera by rotating the vectors of the camera directions <br/>
     * According the Rodrigues' rotation formula
     * @param theta angle theta according to the right hand rule in degrees
     * @return this camera after the rotating
     */
    public Camera rotateCamera(double theta) {
        return rotateCamera(theta, _vTo);
    }

    /**
     * Rotate the camera by rotating the vectors of the camera directions <br/>
     * According the Rodrigues' rotation formula
     * @param theta angle theta according to the right hand rule in degrees
     * @param k axis vector for the rotation
     * @return this camera after the rotating
     */
    private Camera rotateCamera(double theta, Vector k) {
        double radianAngle = Math.toRadians(theta);
        double cosTheta = alignZero(Math.cos(radianAngle));
        double sinTheta = alignZero(Math.sin(radianAngle));

        _vRight.rotateVector(k, cosTheta, sinTheta);
        _vUp.rotateVector(k, cosTheta, sinTheta);

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
        // every time that we chang the distance from the view plane
        // we will calculate the center point of the view plane aging
        _centerPoint = _p0.add(_vTo.scale(_distance));
        return this;
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

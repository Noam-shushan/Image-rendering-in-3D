package elements;
import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {
    private final Point3D _p0;
    private final Vector _vRight, _vUp, _vTo;

    private double _distance;
    private double _width;
    private double _height;

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
     * Image center: Pc = p0 + d∙v
     * Ratio (pixel width & height):
     * Ry = h / Ny
     * Rx = w / Nx
     * Pixel[i,j] center:
     * Pi,j = Pc + (Xi∙vRight + Yi∙vUp)
     * Xi = −(i – (Ny − 1) / 2)∙Ry
     * Xj = (j – (Nx − 1) / 2)∙Rx
     *
     * Vi,j = Pi,j − p0
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return Ray(p0, Vi,j)
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

    public Camera setViewPlaneSize(double width, double height){
        if(isZero(width) || isZero(height)){
            throw new IllegalArgumentException("width or height cannot be zero");
        }

        _width = width;
        _height = height;
        return this;
    }

    public Camera setDistance(double distance){
        if(isZero(distance)){
            throw new IllegalArgumentException("distance cannot be zero");
        }

        _distance = distance;
        return this;
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector get_vRight() {
        return _vRight;
    }

    public Vector get_vUp() {
        return _vUp;
    }

    public Vector get_vTo() {
        return _vTo;
    }

    public double getDistance() {
        return _distance;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }
}

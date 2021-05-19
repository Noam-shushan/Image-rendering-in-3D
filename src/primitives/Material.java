package primitives;

/**
 * this class gives factors of material and the texture of the geometry.
 * geometries objects can have the same material.
 * @author Noam Shushan
 */
public class Material {
    /**
     * the diffuse reduce factor
     */
    public double kD = 0d;
    /**
     * the specular reduce factor
     */
    public double kS = 0d;
    /**
     * The amount of shininess that goes from the object in which the light strikes
     */
    public double nShininess = 1d;
    /**
     * the material transparency
     */
    public double kT = 0d;
    /**
     * Reflection of the material
     */
    public double kR = 0d;

    /**
     * setter for the the material transparency
     * @param kT new transparency factor
     * @return this material
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter for the reflection of the material
     * @param kR new reflection factor
     * @return this material
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter for the diffuse reduce factor
     * @param kD the new kD factor
     * @return this material
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for the specular reduce factor
     * @param kS the new kS factor
     * @return this material
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter for the nShininess
     * @param nShininess the new nShininess
     * @return this material
     */
    public Material setShininess(double nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}

package elements;

import primitives.Color;

/**
 * this class represent a ambient light color
 */
public class AmbientLight {

    /**
     * the intensity of the ambient light
     */
    final private Color _intensity;

    /**
     * Constructor for AmbientLight
     * @param Ia Ia is the presentation of the intensity of the light in the pong formula
     * @param Ka Ka is the presentation of the discount factor of the light in the pong formula
     */
    public AmbientLight(Color Ia, double Ka) {
        _intensity = Ia.scale(Ka);
    }

    /**
     * getter for the intensity
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return _intensity;
    }
}

package elements;

import primitives.Color;

/**
 * this class represent a ambient light color
 */
public class AmbientLight extends Light{

    /**
     * Constructor for AmbientLight
     * @param Ia Ia is the presentation of the intensity of the light in the pong formula
     * @param Ka Ka is the presentation of the discount factor of the light in the pong formula
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}

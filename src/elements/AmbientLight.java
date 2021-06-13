package elements;

import primitives.Color;

/**
 * this class represent a ambient light
 * @author Noam Shushan
 */
public class AmbientLight extends Light{

    /**
     * Constructor for AmbientLight
     * @param Ia Ia is the presentation of the intensity of the light in the pong model
     * @param Ka Ka is the presentation of the reduce factor of the light in the pong model
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Default constructor for AmbientLight
     */
    public AmbientLight(){
        super(Color.BLACK);
    }
}

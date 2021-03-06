package elements;

import primitives.Color;

/**
 * abstract class to generate light intensity for all kind of lights
 * @author Noam Shushan
 */
abstract class Light {

    /**
     * the light intensity
     */
    final private Color _intensity;

    /**
     * Constructor for Light
     * @param intensity the light intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter for the intensity
     * @return the light intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}

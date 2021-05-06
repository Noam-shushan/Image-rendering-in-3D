package elements;

import primitives.Color;

/**
 *
 */
abstract class Light {

    /**
     * the intensity of the ambient light
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

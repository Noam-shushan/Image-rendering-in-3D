package primitives;

import java.util.List;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors
 *
 * @author Dan Zilberstein
 */
public class Color {
    /**
     * The internal fields tx`o maintain RGB components as double numbers from 0 to
     * whatever...
     */
    private final double r, g, b;

    public static final Color BLACK = new Color();
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color ORANGE = new Color(255, 200, 0);
    public static final Color PINK = new Color(255, 175, 175);
    public static final Color DARKGREY = new Color(64, 64, 64);
    public static final Color GREY = new Color(128, 128, 128);
    public static final Color LIGHTGREY = new Color(192, 192, 192);
    public static final Color WHITE = new Color(255, 255, 255);

    /**
     * Default constructor - to generate Black Color (privately)
     */
    private Color() {
        r = g = b = 0.0;
    }

    /**
     * Constructor to generate a color according to RGB components Each component in
     * range 0..255 (for printed white color) or more [for lights]
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @throws IllegalArgumentException if r or g or b is lower then
     */
    public Color(double r, double g, double b) {
        if (r < 0 || g < 0 || b < 0)
            throw new IllegalArgumentException("Negative color component is illegal");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Copy constructor for Color
     *
     * @param other the source color
     */
    public Color(Color other) {
        r = other.r;
        g = other.g;
        b = other.b;
    }

    /**
     * Constructor on base of java.awt.Color object
     *
     * @param other java.awt.Color's source object
     */
    public Color(java.awt.Color other) {
        r = other.getRed();
        g = other.getGreen();
        b = other.getBlue();
    }

    /**
     * Color getter - returns the color after converting it into java.awt.Color
     * object During the conversion any component bigger than 255 is set to 255
     *
     * @return java.awt.Color object based on this Color RGB components
     */
    public java.awt.Color getColor() {
        int ir = (int) r;
        int ig = (int) g;
        int ib = (int) b;
        return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
    }

    /**
     * Operation of adding this and one or more other colors (by component)
     *
     * @param colors one or more other colors to add
     * @return new Color object which is a result of the operation
     */
    public Color add(Color... colors) {
        double rr = r;
        double rg = g;
        double rb = b;
        for (Color c : colors) {
            rr += c.r;
            rg += c.g;
            rb += c.b;
        }
        return new Color(rr, rg, rb);
    }

    /**
     * Scale the color by a scalar
     *
     * @param k scale factor
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if k is lower then 0
     */
    public Color scale(double k) {
        if (k < 0)
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        return new Color(r * k, g * k, b * k);
    }

    /**
     * Scale the color by (1 / reduction factor)
     *
     * @param k reduction factor
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if k is lower then 1
     */
    public Color reduce(double k) {
        if (k < 1)
            throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        return new Color(r / k, g / k, b / k);
    }

    /**
     * calculator the average of list of colors
     * @param colors the list of colors
     * @return the average color
     */
    public static Color average(List<Color> colors) {
        Color result = Color.BLACK;
        for(var color : colors){
            result = result.add(color);
        }
        return result.reduce(colors.size());
    }

}

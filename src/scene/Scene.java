package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

import java.util.MissingResourceException;

/**
 * Graphic scene in our 3D model
 * Scene contains geometric shapes and light sources
 * we using the builder pattern design
 * @author Noam Shushan
 */
public class Scene {

    /**
     * name of the scene
     */
    final public String name;
    /**
     * the background color in the scene
     */
    public Color background;
    /**
     * the ambient light in the scene
     */
    public AmbientLight ambientLight;
    /**
     * all kind of geometries elements like sphere, triangle, plane ect'
     */
    public Geometries geometries;

    // private constructor as part of the builder pattern
    private Scene(SceneBuilder builder) {
        name = builder._name;
        background = builder._background;
        ambientLight = builder._ambientLight;
        geometries = builder._geometries;
    }

    /**
     * setter for background color
     * @param background the new background color
     * @return this scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    /**
     * setter for ambient Light
     * @param ambientLight the new ambientLight for the scene
     * @return this scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    /**
     * setter for the geometries
     * @param geometries the new geometries for the scene
     * @return this scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * builder pattern design
     */
    public static class SceneBuilder{

        // set all fields with default values
        final private String _name;
        public Color _background = Color.BLACK;
        public AmbientLight _ambientLight = new AmbientLight(Color.LIGHTGREY, 0);
        public Geometries _geometries = new Geometries();

        /**
         * Constructor for SceneBuilder
         * to get the Scene instance, you must call the methods {@link scene.Scene.SceneBuilder#build}
         * @param name the name of the scene
         */
        public SceneBuilder(String name) {
            _name = name;
        }

        /**
         * build the Scene
         * check if some resources is missing
         * @return the build scene
         * @throws MissingResourceException if the scene is missing one of her resource
         */
        public Scene build(){
            if (_ambientLight == null) {
                throw new MissingResourceException("missing resource of the ambient light",
                        AmbientLight.class.getName(), "");
            }
            if (_background == null) {
                throw new MissingResourceException("missing resource of the background",
                        Color.class.getName(), "");
            }
            if (_geometries == null) {
                throw new MissingResourceException("missing resource of geometries",
                        Geometries.class.getName(), "");
            }

            return new Scene(this);
        }
        /**
         * setter for background color
         * @param background the new background color
         * @return this SceneBuilder
         */
        public SceneBuilder setBackground(Color background) {
            _background = background;
            return this;
        }
        /**
         * setter for ambient Light
         * @param ambientLight the new ambientLight for the scene
         * @return this SceneBuilder
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            _ambientLight = ambientLight;
            return this;
        }
        /**
         * setter for the geometries
         * @param geometries the new geometries for the scene
         * @return this SceneBuilder
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            _geometries = geometries;
            return this;
        }
    }
}

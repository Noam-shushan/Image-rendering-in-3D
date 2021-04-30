package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    final public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    private Scene(SceneBuilder builder) {
        name = builder._name;
        background = builder._background;
        ambientLight = builder._ambientLight;
        geometries = builder._geometries;
    }

    public static class SceneBuilder{
        final private String _name;
        public Color _background = Color.BLACK;
        public AmbientLight _ambientLight = new AmbientLight(Color.LIGHTGREY, 0);
        public Geometries _geometries = new Geometries();

        public SceneBuilder(String name) {
            _name = name;
        }

        public Scene build(){
            return new Scene(this);
        }

        public SceneBuilder setBackground(Color background) {
            _background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            _ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            _geometries = geometries;
            return this;
        }
    }
}

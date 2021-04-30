package renderer;

import primitives.*;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene _scene;

    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}

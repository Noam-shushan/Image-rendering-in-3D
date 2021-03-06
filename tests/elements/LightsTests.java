package elements;

import static org.junit.jupiter.api.Assertions.*;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;


class LightsTests {

    private Scene scene1 = new Scene.SceneBuilder("Test scene").build();
    private Scene scene2 = new Scene.SceneBuilder("Test scene") //
            .setAmbientLight(new AmbientLight(Color.WHITE, 0.15)).build();
    private Camera camera1 = new Camera(
            new Point3D(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(
            new Point3D(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150),
            new Point3D(150, -150, -150),
            new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150),
            new Point3D(-70, 70, -50),
            new Point3D(75, 75, -150));
    private Geometry sphere = new Sphere(50, new Point3D(0, 0, -50)) //
            .setEmission(Color.BLUE) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(
                new Color(500, 300, 0),
                new Point3D(-50, -50, 50))
                .setKc(1).setKl(0.00001).setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(
                new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2))
                .setKc(1).setKl(0.00000001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("sphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(
                new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130))
                .setKc(1).setKl(0.0005).setKq(0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(
                new Color(500, 250, 250),
                new Point3D(10, -10, -130),
                new Vector(-2, -2, -1))
                .setKc(1).setKl(0.000005).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spot light
     */
    @Test
    public void sphereSpotSharp() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(
                new Color(500, 300, 0),
                new Point3D(-50, -50, 50),
                new Vector(1, 1, -2))
                .setKc(1).setKl(0.000005).setKq(0.00000025));

        ImageWriter imageWriter = new ImageWriter("sphereSpotSharp", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a narrow spot light
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(
                new Color(800, 400, 400),
                new Point3D(10, -10, -130),
                new Vector(-2, -2, -1))
                .setKc(1).setKl(0.0000025).setKq(0.00005));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharp", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void testSpotLightWithFocus(){
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(
                new Color(800, 400, 400),
                new Point3D(10, -10, -130),
                new Vector(-2, -2, -1)).setFocus(12)
                .setKc(1).setKl(0.0000025).setKq(0.00005));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharpFocus", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereSpotFocus() {
        scene1.geometries.add(sphere
                .setEmission(new Color(148, 183, 176))
                .setMaterial(sphere.getMaterial().setShininess(150)));
        scene1.lights.add(new SpotLight(
                new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2))
                .setFocus(50)
                .setKc(1).setKl(0.00000001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("sphereSpotWithFocus", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

}
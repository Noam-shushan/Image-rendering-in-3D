package renderer;


import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;


import org.junit.jupiter.api.Test;
import xmlParser.SceneDescriptor;
import xmlParser.XmlParser;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test rendering a basic image
 */
public class RenderTests {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0,1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {

        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90)).build();

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, Color.YELLOW);
        render.writeToImage();
    }

    @Test
    void test1(){
        Scene scene =  new Scene.SceneBuilder("my scene")
                .setAmbientLight(new AmbientLight(new Color(137, 185, 208), 1))
                .setBackground(new Color(192, 185, 208))
                .setGeometries(
                        new Geometries(
                            new Sphere(60, new Point3D(0, 0, -100)),
                            new Triangle(
                                    new Point3D(-70, -70, -100),
                                    new Point3D(70, -70, -100),
                                    new Point3D(0, -200, -100)),
                            new Triangle(
                                    new Point3D(60, 60, -100),
                                    new Point3D(-60, 60, -100),
                                    new Point3D(60, 180, -100)),
                            new Triangle(
                                    new Point3D(-60, 180, -100),
                                    new Point3D(-60, 60, -100),
                                    new Point3D(60, 180, -100))
                        ))
                .build();

        ImageWriter imageWriter = new ImageWriter("basic test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();

    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        var rootElement = XmlParser.readXml("xmlFiles/basicRenderTestTwoColors.xml");
        SceneDescriptor sd = new SceneDescriptor(rootElement,"XML Test scene");
        Scene scene = sd.getSceneResult();

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, Color.YELLOW);
        render.writeToImage();
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(Color.WHITE, 0.2))
                .setGeometries(new Geometries(
                        new Sphere(50, new Point3D(0, 0, -100)), //
                        new Triangle(
                                new Point3D(-100, 0, -100),
                                new Point3D(0, 100, -100),
                                new Point3D(-100, 100, -100)) // up left
                                .setEmission(Color.GREEN),
                        new Triangle(
                                new Point3D(100, 0, -100),
                                new Point3D(0, 100, -100),
                                new Point3D(100, 100, -100)), // up right
                        new Triangle(
                                new Point3D(-100, 0, -100),
                                new Point3D(0, -100, -100),
                                new Point3D(-100, -100, -100)) // down left
                                .setEmission(Color.RED),
                        new Triangle(
                                new Point3D(100, 0, -100),
                                new Point3D(0, -100, -100),
                                new Point3D(100, -100, -100)) // down right
                                .setEmission(Color.BLUE)))
                .build(); //

        scene.geometries.add();

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, Color.WHITE);
        render.writeToImage();
    }
}

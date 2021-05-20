package renderer;
import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;


import org.junit.jupiter.api.Test;

public class MyImages {

    @Test
    void tennisTable(){
        Point3D p1 = new Point3D(-200, -150, -10),
                p2 = new Point3D(-200, 150, -10),
                p3 = new Point3D(200, 150, -10),
                p4 = new Point3D(200, -150, -10);
        Scene scene = new Scene.SceneBuilder("my scene")
                .setBackground(new Color(31, 33, 31))
                .setGeometries(new Geometries(
                        new Polygon(p1, p2, p3, p4)
                        .setEmission(new Color(41, 238, 140))
                        .setMaterial(new Material()
                        .setShininess(200)
                        .setKs(0.3)
                        .setKd(0.6)),
                        new Polygon(
                                new Point3D(0, 150, 10),
                                new Point3D(0, 150, -10),
                                new Point3D(0, -150, -10),
                                new Point3D(0, -150, 10)
                        ).setEmission(new Color(243, 253, 254))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setKt(0.6)),
                        new Sphere(15, new Point3D(120, 20,0))
                        .setEmission(Color.RED)
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                        new Polygon(
                                new Point3D(155, 0, 8),
                                new Point3D(155, 0,0),
                                new Point3D(120, 20, 0),
                                new Point3D(120, 20, 8))
                        .setEmission(new Color(144, 107, 85))
                        .setMaterial(new Material().setShininess(100).setKs(0.5).setKd(0.5))
                ))
                .build();
        scene.lights.add(
                new SpotLight(
                    new Color(700, 400, 400),
                    new Point3D(150, 0, 200),
                    new Vector(-150, -38, -90))
                    .setKl(4E-4).setKq(2E-5));

        Camera camera = new Camera(
                new Point3D(300, 600, 150),
                new Vector(-300, -600, -150),
                new Vector(-12.5,-18.75,100))
                .setViewPlaneSize(150, 150)
                .setDistance(200);

        Render render = new Render()
                .setImageWriter(new ImageWriter("Tennis table", 1500, 1500))
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

        Render render2 = new Render()
                .setImageWriter(new ImageWriter("Tennis table different angle", 1500, 1500))
                .setCamera(camera
                        .moveCamera(10, 20, 0)
                        .rotateCamera(new Vector(-4, -7, -15), 4))
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void tennisTable2(){
        Point3D p1 = new Point3D(-200, -150, -10),
                p2 = new Point3D(-200, 150, -10),
                p3 = new Point3D(200, 150, -10),
                p4 = new Point3D(200, -150, -10);
        Scene scene = new Scene.SceneBuilder("my scene")
                .setBackground(new Color(31, 33, 31))
                .setGeometries(new Geometries(
                        new Polygon(p1, p2, p3, p4)
                                .setEmission(new Color(41, 238, 140))
                                .setMaterial(new Material()
                                        .setShininess(200)
                                        .setKs(0.3)
                                        .setKd(0.6)),
                        new Polygon(
                                new Point3D(0, 150, 10),
                                new Point3D(0, 150, -10),
                                new Point3D(0, -150, -10),
                                new Point3D(0, -150, 10)
                        ).setEmission(new Color(243, 253, 254))
                                .setMaterial(new Material().setKd(0.3).setKs(0.2).setKt(0.6)),
                        new Sphere(15, new Point3D(120, 20,0))
                                .setEmission(Color.RED)
                                .setMaterial(new Material().setKs(0.8).setShininess(60)),
                        new Polygon(
                                new Point3D(155, 0, 8),
                                new Point3D(155, 0,0),
                                new Point3D(120, 20, 0),
                                new Point3D(120, 20, 8))
                                .setEmission(new Color(144, 107, 85))
                                .setMaterial(new Material().setShininess(100).setKs(0.5).setKd(0.5))
                ))
                .build();
        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(150, 0, 200),
                        new Vector(-150, -38, -90))
                        .setKl(4E-4).setKq(2E-5));

        Camera camera = new Camera(
                new Point3D(300, 600, 150),
                new Vector(-300, -600, -150),
                new Vector(-12.5,-18.75,100))
                .setViewPlaneSize(150, 150)
                .setDistance(200);

        Render render = new Render()
                .setImageWriter(new ImageWriter("Tennis table different angle", 1500, 1500))
                .setCamera(camera
                        .moveCamera(50, 30, 40)
                        .rotateCamera(new Vector(-500, -700, -200), 8))
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }
}

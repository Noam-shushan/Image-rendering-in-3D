package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;


import org.junit.jupiter.api.Test;

public class MyImages {

    Scene scene = new Scene.SceneBuilder("Tennis table")
            .setBackground(new Color(31, 33, 31))
            .setGeometries(new Geometries(
                    new Polygon(
                            new Point3D(-200, -150, -10),
                            new Point3D(-200, 150, -10),
                            new Point3D(200, 150, -10),
                            new Point3D(200, -150, -10))
                            .setEmission(new Color(41, 238, 140))
                            .setMaterial(new Material()
                                    .setShininess(200)
                                    .setKs(0.3)
                                    .setKd(0.6)),
                    new Polygon(
                            new Point3D(0, 150, 10),
                            new Point3D(0, 150, -10),
                            new Point3D(0, -150, -10),
                            new Point3D(0, -150, 10))
                            .setEmission(new Color(238, 239, 226))
                            .setMaterial(new Material()
                                    .setKd(0.2)
                                    .setKs(0.2)
                                    .setShininess(30)
                                    .setKt(0.6)),
                    new Sphere(15, new Point3D(120, 20, 0))
                            .setEmission(Color.RED)
                            .setMaterial(new Material()
                                    .setKs(0.8)
                                    .setShininess(60)),
                    new Polygon(
                            new Point3D(155, 0, 8),
                            new Point3D(155, 0, 0),
                            new Point3D(120, 20, 0),
                            new Point3D(120, 20, 8))
                            .setEmission(new Color(144, 107, 85))
                            .setMaterial(new Material()
                                    .setShininess(100)
                                    .setKs(0.5)
                                    .setKd(0.5)),
                    new Polygon(
                            new Point3D(130, 150, -10),
                            new Point3D(135, 150, -10),
                            new Point3D(135, 150, -90),
                            new Point3D(130, 150, -90))
                            .setEmission(new Color(180, 121, 105))
                            .setMaterial(new Material()
                                    .setKd(0.8)),
                    new Polygon(
                            new Point3D(-130, 150, -10),
                            new Point3D(-135, 150, -10),
                            new Point3D(-135, 150, -90),
                            new Point3D(-130, 150, -90))
                            .setEmission(new Color(180, 121, 105))
                            .setMaterial(new Material()
                                    .setKd(0.8)),
                    new Polygon(
                            new Point3D(-130, -150, -10),
                            new Point3D(-135, -150, -10),
                            new Point3D(-135, -150, -90),
                            new Point3D(-130, -150, -90))
                            .setEmission(new Color(180, 121, 105))
                            .setMaterial(new Material()
                                    .setKd(0.8)),
                    new Polygon(
                            new Point3D(-130, 150, -10),
                            new Point3D(-135, 150, -10),
                            new Point3D(-135, 150, -90),
                            new Point3D(-130, 150, -90))
                            .setEmission(new Color(180, 121, 105))
                            .setMaterial(new Material()
                                    .setKd(0.8)),
                    new Sphere(5, new Point3D(100, 35, 0))
                            .setEmission(new Color(218, 222, 100))
                            .setMaterial(new Material()
                                    .setShininess(50)
                                    .setKd(0.5)
                                    .setKs(0.2))))
            .build();

    @Test
    void tennisTable() {
        scene.lights.add(
                new SpotLight(
                        new Color(225, 239, 217),
                        new Point3D(150, 0, 200),
                        new Vector(-150, -38, -90))
                        .setKl(4E-4).setKq(2E-5));

        Camera camera = new Camera(
                new Point3D(300, 600, 150),
                new Vector(-300, -600, -150),
                new Vector(-12.5, -18.75, 100))
                .setViewPlaneSize(150, 150)
                .setDistance(200);

        Render render = new Render()
                .setImageWriter(new ImageWriter("Tennis table", 500, 500))
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

    }

    @Test
    void tennisTableRotateCamera() {
        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(150, 0, 200),
                        new Vector(-150, -38, -90))
                        .setKl(4E-4).setKq(2E-5));

        Camera camera = new Camera(
                new Point3D(300, 600, 150),
                new Vector(-300, -600, -150),
                new Vector(-12.5, -18.75, 100))
                .setViewPlaneSize(150, 150)
                .setDistance(200);

        Render render = new Render()
                .setImageWriter(new ImageWriter("Tennis table different angle", 500, 500))
                .setCamera(camera
                        .moveCamera(new Point3D(500, 0, 500), Point3D.ZERO)
                        .rotateCamera(45)
                )
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }
}

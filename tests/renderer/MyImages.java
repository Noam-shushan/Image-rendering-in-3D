package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;


import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class MyImages {

    Scene scene = new Scene.SceneBuilder("Tennis table")
            .setBackground(new Color(31, 33, 31))
            .setGeometries(new Geometries(
                    new Polygon( // the pinging plane
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
        scene.setLights(
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
                .setRayTracer(new RayTracerBasic(scene))
                .setMultithreading(6)
                .setNumOfRaysInBean(50)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();

    }

    @Test
    void tennisTableRotateCamera() {
        scene.setLights(
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

    @Test
    void cylTest(){
        Scene cylScene = new Scene.SceneBuilder("Cylinder")
                .setGeometries(new Geometries(
                        new Cylinder(10, new Ray(new Point3D(100, 0, 0), new Vector(0,1,0)), 20)
                        .setEmission(Color.GREEN)
                ))
                .build();

        Camera camera = new Camera(Point3D.ZERO,
                new Vector(1,0,0), new Vector(0,1,0))
                .setViewPlaneSize(150, 150)
                .setDistance(200);

        Render render = new Render()
                .setImageWriter(new ImageWriter("Cylinder Test", 500, 500))
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(cylScene));

        render.renderImage();
        render.writeToImage();
    }

    Scene SphereOnMirrorScene = new Scene.SceneBuilder("mirror")
            .setGeometries(
                    new Geometries(
                            new Sphere(18, new Point3D(30, 5,32))
                                    .setEmission(Color.RED)
                                    .setMaterial(new Material()
                                            .setKd(0.3)
                                            .setKr(0.6)
                                            .setKs(1)
                                            .setShininess(8)
                                    ),
                            new Polygon(
                                    new Point3D(80, 60, 50),
                                    new Point3D(80, -60, 50),
                                    new Point3D(10, -60, 10),
                                    new Point3D(10, 60,10))
                                    .setEmission(new Color(152, 206, 180))
                                    .setMaterial(new Material()
                                            .setKr(0.7)
                                            .setKt(0.1)
                                            .setShininess(19)
                                            .setKs(0.9)
                                            .setKd(0.1)
                                    )
                    )
            )
            .setLights(
                    new SpotLight(new Color(253, 255, 180),
                            new Point3D(-30,200, 600),
                            new Vector(50, -100, -470))
                            .setFocus(20)
                            .setKl(0.0005)
                            .setKq(0.000005)


            )
            .build();

    @Test
    void mirrorTestWithImprov(){
        Camera camera = new Camera(new Point3D(-25, 0,45),
                new Vector(1,0,0), new Vector(0,0,1))
                .setViewPlaneSize(200, 200)
                .setDistance(50);



        Render render = new Render()
                .setImageWriter(new ImageWriter("sphere on mirror", 500, 500))
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(SphereOnMirrorScene)
                    .setGlossy()
                    .setDiffuse()
                    .setNumOfRaysInBean(50)
                        )
                //.setNumOfRaysInBean(40)
                .setMultithreading(3)
        ;

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void mirrorTestNoImprov(){
        Camera camera = new Camera(new Point3D(-25, 0,45),
                new Vector(1,0,0), new Vector(0,0,1))
                .setViewPlaneSize(200, 200)
                .setDistance(50);



        Render render = new Render()
                .setImageWriter(new ImageWriter("sphere on mirror no glossy", 500, 500))
                .setCamera(camera
                        //.moveCamera(new Point3D(-25, -40, 50), new Point3D(1, 0,0))
                )
                .setRayTracer(new RayTracerBasic(SphereOnMirrorScene)
                        //.setGlossy()
                        //.setDiffuse()
                )
                //.setNumOfRaysInBean(40)
                .setMultithreading(3)
                ;

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void mirrorTest2(){
        Camera camera = new Camera(new Point3D(-25, 0,45),
                new Vector(1,0,0), new Vector(0,0,1))
                .setViewPlaneSize(200, 200)
                .setDistance(50);
        Scene scene = new Scene.SceneBuilder("mirror")
                .setGeometries(
                        new Geometries(
                                new Sphere(10, new Point3D(5, 5,40))
                                        .setEmission(Color.RED)
                                        .setMaterial(new Material()
                                                .setKd(0.3)
                                                .setKr(0.6)
                                                .setKs(1)
                                                .setShininess(8)
                                        ),
                                new Polygon(
                                        new Point3D(70, 80, 0),
                                        new Point3D(30, -80, 0),
                                        new Point3D(30, -80, 80),
                                        new Point3D(70, 80,80))
                                        .setEmission(new Color(152, 206, 180))
                                        .setMaterial(new Material()
                                                .setKr(0.7)
                                                //.setKt(0.1)
                                                .setShininess(30)
                                                .setKs(1)
                                                .setKd(0.2)
                                        )
                        )
                )
                .setLights(
                        new SpotLight(new Color(253, 255, 180),
                                new Point3D(-50,100, -10),
                                new Vector(70, -100,40))
                                .setFocus(20)
                                .setKl(0.0005)
                                .setKq(0.000005)


                )
                .build();


        Render render = new Render()
                .setImageWriter(new ImageWriter("sphere  and cyl on mirror no glossy", 500, 500))
                .setCamera(camera
                        //.moveCamera(new Point3D(-25, -40, 50), new Point3D(1, 0,0))
                )
                .setRayTracer(new RayTracerBasic(scene)
                        .setGlossy()
                        .setDiffuse()
                        .setNumOfRaysInBean(50)
                )
                .setNumOfRaysInBean(40)
                .setMultithreading(3)
                ;

        render.renderImage();
        render.writeToImage();
    }
}

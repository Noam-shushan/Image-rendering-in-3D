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
                .setMultithreading(3)
                .setAntiAliasing(50)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();

    }

    @Test
    void boxTest(){
        Box box = new Box(
                new Point3D(10, 10, 0),
                new Point3D(0,10,0),
                Point3D.ZERO,
                new Point3D(10,0,0),
                20d
        );
        Scene boxScene = new Scene.SceneBuilder("box")
                .setGeometries(new Geometries(box
                        .setEmission(Color.ORANGE)))
                .setLights(new DirectionalLight(
                        new Color(253, 255, 180) ,new Vector(0,0,-1)))
                .build();
        Camera camera = new Camera(
                new Point3D(-10, 8,30),
                Point3D.ZERO.subtract(new Point3D(-10, 8,30)),
                Point3D.ZERO.subtract(new Point3D(-10, 8,30)).createVerticalVector())
                .setDistance(50)
                .setViewPlaneSize(200, 200)
                ;
        Render render =  new Render()
                .setImageWriter(new ImageWriter("box test", 500, 500))
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(boxScene))
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

    Scene _sphereOnMirrorScene = new Scene.SceneBuilder("mirror")
            .setGeometries(
                    new Geometries(
                            new Sphere(20, new Point3D(40, 10,80))
                                    .setEmission(Color.RED)
                                    .setMaterial(new Material()
                                            .setKd(0.6)
                                            .setKr(0.2)
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
                                            .setKr(0.5)
                                            .setKt(0)
                                            .setShininess(19)
                                            .setKs(0.7)
                                            .setKd(0.2)
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
                .setRayTracer(new RayTracerBasic(_sphereOnMirrorScene)
                    .setGlossy()
                    .setDiffuse()
                    .setNumOfRaysInBean(50)
                        )
                //.setAntiAliasing(40)
                .setMultithreading(3)
                .setDebugPrint()
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
                .setRayTracer(new RayTracerBasic(_sphereOnMirrorScene)
//                        .setGlossy()
//                        .setDiffuse()
//                        .setNumOfRaysInBean(60)
                )
                .setAntiAliasing(60)
                .setMultithreading(3)
                .setDebugPrint()
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
                                new Sphere(15, new Point3D(5, 20,40))
                                        .setEmission(Color.RED)
                                        .setMaterial(new Material()
                                                .setKd(0.7)
                                                .setKr(0.2)
                                                .setKs(1)
                                                .setShininess(8)
                                        ),
                                new Polygon(
                                        new Point3D(80, 80, -50),
                                        new Point3D(40, -80, -50),
                                        new Point3D(40, -80, 80),
                                        new Point3D(80, 80,80))
                                        .setEmission(new Color(152, 206, 180))
                                        .setMaterial(new Material()
                                                .setKr(0.6)
                                                .setKt(0)
                                                .setShininess(20)
                                                .setKs(0.8)
                                                .setKd(0.3)
                                        )
                        )
                )
                .setLights(
                        new PointLight(new Color(253, 255, 180),
                                new Point3D(100,100, -10))
                                .setKl(0.007)
                                .setKq(0.00001)



                )
                .build();


        Render render = new Render()
                .setImageWriter(new ImageWriter("sphere on stand mirror", 500, 500))
                .setCamera(camera
                )
                .setRayTracer(new RayTracerBasic(scene)
                        .setGlossy()
                        .setDiffuse()
                        .setNumOfRaysInBean(50)
                        .setBeanRadiusForGlossy(30)
                )
                //.setAntiAliasing(50)
                .setMultithreading(3)
                .setDebugPrint()
                ;

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void mirrorTest3(){
        Camera camera = new Camera(new Point3D(-25, 0,45),
                new Vector(1,0,0), new Vector(0,0,1))
                .setViewPlaneSize(200, 200)
                .setDistance(50);
        Scene scene = new Scene.SceneBuilder("mirror")
                .setGeometries(
                        new Geometries(
                                new Sphere(15, new Point3D(5, 20,40))
                                        .setEmission(Color.RED)
                                        .setMaterial(new Material()
                                                .setKd(0.7)
                                                .setKr(0.2)
                                                .setKs(1)
                                                .setShininess(8)
                                        ),
                                new Polygon(
                                        new Point3D(80, 80, -50),
                                        new Point3D(40, -80, -50),
                                        new Point3D(40, -80, 80),
                                        new Point3D(80, 80,80))
                                        .setEmission(new Color(152, 206, 180))
                                        .setMaterial(new Material()
                                                .setKr(0.6)
                                                .setKt(0)
                                                .setShininess(20)
                                                .setKs(0.8)
                                                .setKd(0.3)
                                        )
                        )
                )
                .setLights(
                        new PointLight(new Color(253, 255, 180),
                                new Point3D(100,100, -10))
                                .setKl(0.007)
                                .setKq(0.00001)



                )
                .build();


        Render render = new Render()
                .setImageWriter(new ImageWriter("sphere on stand mirror no imp", 500, 500))
                .setCamera(camera
                )
                .setRayTracer(new RayTracerBasic(scene)
                )
                .setAntiAliasing(100)
                .setMultithreading(3)
                .setDebugPrint()
                ;

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void f(){
        var list = new Point3D(1,2,3).createRandomPointsAround(3, 0.5);
        Plane p = new Plane(list.get(0), list.get(1), list.get(2));
        System.out.println(p);
    }

    @Test
    void tt() {
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(1, 0, -1);
        System.out.println(v2.dotProduct(v1));
    }
}
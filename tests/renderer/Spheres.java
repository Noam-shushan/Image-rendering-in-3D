package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import static primitives.Util.*;
import scene.Scene;


import org.junit.jupiter.api.Test;

import java.util.LinkedList;


public class Spheres {

    Camera camera = new Camera(new Point3D(0,30,0), new Vector(1,0,0), new Vector(0,1,0))
            .setDistance(50)
            .setViewPlaneSize(200, 200);

    Geometries smallSpheres;
    Geometries floor;
    Geometries bigSpheres;
    Geometries lamps;
    Geometries walls;

    Scene scene;

    @Test
    void createImage(){
        createScene();
        Render render = new Render()
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(scene))
                .setImageWriter(new ImageWriter("small spheres with anti aliasing",500, 500))
                .setMultithreading(3)
                .setAntiAliasing(100)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }
    @Test
    void createImageWithoutAA(){
        createScene();
        Render render = new Render()
                .setCamera(camera)
                .setRayTracer(new RayTracerBasic(scene))
                .setImageWriter(new ImageWriter("small spheres without anti aliasing",500, 500))
                .setMultithreading(3)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void createImageDifferentAngle(){
        createScene();
        Render render = new Render()
                .setCamera(camera.moveCamera(new Point3D(10, 150, 0), new Point3D(50, 25, 30)))
                .setRayTracer(new RayTracerBasic(scene))
                .setImageWriter(new ImageWriter("small spheres different angle",500, 500))
                .setMultithreading(3)
                //.setAntiAliasing(50)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    /**
     * build the scene
     */
    void createScene(){
        createFloor();
        createdSpheres();
        createLamps();
        createWalls();

        scene = new Scene.SceneBuilder("spheres")
                .setGeometries(
                        new Geometries(
                                floor,
                                smallSpheres,
                                bigSpheres,
                                lamps,
                                walls))
                .setLights(
                        new SpotLight(new Color(244, 248, 176),
                                new Point3D(70, 105, 0),
                                new Vector(0,-1,0))
                            .setFocus(8)
                            .setKq(0.00000001)
                            .setKl(0.0000001),
                        new PointLight(
                                new Color(226, 218, 198),
                                new Point3D(100, 1000, 0))
                            .setKl(0.000005)
                            .setKq(0.000001))
                .build();
    }

    /**
     * create the walls for the scene
     */
    void createWalls(){
        walls = new Geometries();
        var leftWall = new Plane(
                new Point3D(10, 0, -150),
                new Point3D(20, 0, -150),
                new Point3D(30, 50, -150))
                .setEmission(new Color(181, 246, 197))
                .setMaterial(new Material()
                        .setKd(0.6)
                        .setShininess(20)
                        .setKt(0.8));
        var rightWall = new Plane(
                new Point3D(10, 0, 150),
                new Point3D(20, 0, 150),
                new Point3D(30, 50, 150))
                .setEmission(new Color(86, 186, 197))
                .setMaterial(new Material()
                        .setKd(0.6)
                        .setShininess(20)
                        .setKt(0.8));
        walls.add(leftWall, rightWall);
    }

    /**
     * create the lamp for the scene
     */
    void createLamps(){
        lamps = new Geometries();
        Color em = new Color(255,215,0);
        Material mat = new Material()
                .setShininess(20)
                .setKd(0.6)
                .setKs(1)
                .setKr(0.2);
        var firstStand = new Cylinder(2,
                new Ray(new Point3D(70, 170, 0), new Vector(0,-1,0)), 70)
                .setEmission(em)
                .setMaterial(mat);
        var firstTriangle = new Triangle(
                new Point3D(70,105, 0),
                new Point3D(50, 70, -20),
                new Point3D(50, 70, 20))
                .setEmission(em)
                .setMaterial(mat);
        var secondTriangle = new Triangle(
                new Point3D(70,105, 0),
                new Point3D(50, 70, 20),
                new Point3D(90, 70, 20))
                .setEmission(em)
                .setMaterial(mat);
        var thirdTriangle = new Triangle(
                new Point3D(70,105, 0),
                new Point3D(90, 70, 20),
                new Point3D(90, 70, -20))
                .setEmission(em)
                .setMaterial(mat);
        var forthTriangle = new Triangle(
                new Point3D(70,105, 0),
                new Point3D(50, 70, -20),
                new Point3D(90, 70, -20))
                .setEmission(em)
                .setMaterial(mat);
        var lamp = new Sphere(8, new Point3D(70, 90, 0))
                .setEmission(Color.WHITE)
                .setMaterial(new Material()
                .setKs(1)
                .setShininess(2)
                .setKt(0.7)
                .setKd(0.2));
        lamps.add(firstStand, firstTriangle, secondTriangle, thirdTriangle, forthTriangle, lamp);
    }

    /**
     * create random small spheres and glossy bid one
     */
    void createdSpheres(){
        bigSpheres = new Geometries();
        bigSpheres.add(
                new Sphere(28, new Point3D(50, 25, 30))
                .setEmission(new Color(192, 192, 192))
                .setMaterial(new Material()
                .setShininess(20)
                .setKd(0.6)
                .setKs(1)
                .setKr(0.2)));

        smallSpheres = new Geometries();
        var tempList = new LinkedList<Point3D>();
        double radius = 8;
        double x = 0, y = 8, z = 0;
        for(int i = 0; i < 20; i++){
            x = randomWithSeed(20, 100);
            z = randomWithSeed(-80, 80);
            var point = new Point3D(x, y, z);
            tempList.add(point);
            boolean flag = true;
            for(var p : tempList){
                if(!p.equals(point) && p.distance(point) <= radius*2
                        && point.distance(new Point3D(60, 25, 30)) <= 28*2){
                    tempList.remove(point);
                    flag = false;
                    break;
                }
            }
            if(flag) {
                smallSpheres.add(new Sphere(radius, point)
                .setEmission(new Color(random(0, 256), random(0, 256), random(0, 256)))
                .setMaterial(new Material()
                .setShininess(50)
                .setKd(0.8)
                .setKs(0.7)
                .setKr(0.01)));
            }
        }
    }

    /**
     * create the floor for the scene
     */
    void createFloor(){
        floor = new Geometries();
        double z = 80;
        for(int i = 0, k = 0; i < 20; i++, k++){
            double x = 10;
            for(int j = 0; j < 10; j++, k++){
                floor.add(new Polygon(
                        new Point3D(x, 0, z + 10),
                        new Point3D(x + 10,0, z + 10),
                        new Point3D(x + 10, 0, z),
                        new Point3D(x, 0, z))
                        .setEmission(k % 2 == 0 ? new Color(195, 148, 171) : Color.GREY)
                        .setMaterial(new Material()
                        .setShininess(100)
                        .setKd(0.6)
                        .setKs(1)));
                x += 10;
            }
            z -= 10;
        }
    }
}

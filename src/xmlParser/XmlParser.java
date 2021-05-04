package xmlParser;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import elements.AmbientLight;
import geometries.*;
import primitives.*;
import scene.Scene;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This file reads an xml file for a graphic scene in our 3D model
 * @author Noam Shushan
 */
public class XmlParser {

    final static String dir = System.getProperty("user.dir") + "/XmlFiles";

    /**
     * Reads a scene from xml file and returns the scene
     * @param path path to the file
     * @param sceneName name of the scene
     * @return the scene
     */
    public static Scene readSceneFromXml(String path, String sceneName){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Scene scene = null;
        try{
            File file = new File(path);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            var rootElement = doc.getDocumentElement();

            Color background = getColor(rootElement.getAttribute("background-color"));
            AmbientLight ambientLight = getAmbientLight(rootElement);
            Geometries geometries = getGeometries(rootElement);

            scene = new Scene.SceneBuilder(sceneName)
                    .setAmbientLight(ambientLight)
                    .setBackground(background)
                    .setGeometries(geometries).build();


        } catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

        return scene;
    }

    private static AmbientLight getAmbientLight(Element element){
        var ambientLightElement = (Element)element.getElementsByTagName("ambient-light").item(0);
        var color = ambientLightElement.getAttribute("color");
        var ka = ambientLightElement.getAttribute("Ka");
        if(ka == null || ka.isEmpty()){
            ka = "1";
        }
        return new AmbientLight(getColor(color), Double.parseDouble(ka));
    }

    private static Geometries getGeometries(Element element){
        Geometries geometries = new Geometries();
        var geometriesNodeList = element.getElementsByTagName("geometries").item(0).getChildNodes();
        for(int i = 0; i < geometriesNodeList.getLength(); i++){
            Node node = geometriesNodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                geometries.add(getGeometry((Element)node));
            }
        }
        return geometries;
    }

    private static Intersectable getGeometry(Element element){
        Intersectable result = null;
        switch (element.getNodeName()){
            case "sphere" :
                var pc = getPoint(element.getAttribute("center"));
                var radius = Double.parseDouble(element.getAttribute("radius"));
                result = new Sphere(radius , pc);
                break;
            case "triangle" :
                var p0 = getPoint(element.getAttribute("p0"));
                var p1 = getPoint(element.getAttribute("p1"));
                var p2 = getPoint(element.getAttribute("p2"));
                result = new Triangle(p0, p1, p2);
                break;
            default:
                break;
        }
        return result;
    }

    private static Point3D getPoint(String pointFormat){
        String[] point = pointFormat.split(" ");
        return new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
    }

    private static Color getColor(String colorFormat){
        String[] color = colorFormat.split(" ");
        return new Color(Double.parseDouble(color[0]), Double.parseDouble(color[1]), Double.parseDouble(color[2]));
    }

    public Object readFromXmlWithXMLDecoder(String path){
        Object obj = null;
        try{
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            XMLDecoder xml = new XMLDecoder(in);
            obj = xml.readObject();
            xml.close();
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return obj;
    }

    public void saveToXmlWithXMLEncoder(Object obj, String fileName){
        try{
            File file = new File(dir + "./" + fileName + ".xml");
            FileOutputStream out = new FileOutputStream(file);
            XMLEncoder xml = new XMLEncoder(out);
            xml.writeObject(obj);
            xml.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

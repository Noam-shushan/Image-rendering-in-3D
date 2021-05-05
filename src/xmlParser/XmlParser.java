package xmlParser;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This file reads an xml file for a graphic scene in our 3D model
 * @author Noam Shushan
 */
public class XmlParser {

    /**
     * Reads a scene from xml file and returns the scene
     * @param path path to the file
     * @return the root element of the xml file of the scene
     */
    public static Element readXml(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element rootElement = null;
        try{
            File file = new File(path);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            rootElement = doc.getDocumentElement();

        } catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

        return rootElement;
    }

    final static String dir = System.getProperty("user.dir") + "/xmlFiles";

    public static Object readFromXmlWithXMLDecoder(String path){
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

    public static void saveToXmlWithXMLEncoder(Object obj, String fileName){
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

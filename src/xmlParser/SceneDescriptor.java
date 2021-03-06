package xmlParser;

import elements.AmbientLight;
import geometries.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Decodes the necessary values for a scene from the root of an element in an XML file
 * @author Noam Shushan
 */
public class SceneDescriptor {

    /**
     * the root element of the XML file
     */
    private Element _rootElement;

    /**
     * the final result of scene from the XML file
     */
    private Scene _sceneResult;

    /**
     * scene descriptor get the necessary values from root element of xml file
     * @param rootElement the root element of the xml file of the scene
     * @param sceneName the name of the scene
     */
    public SceneDescriptor(Element rootElement, String sceneName) {
        _rootElement = rootElement;

        AmbientLight ambientLight = getAmbientLight();
        Geometries geometries = getGeometries();
        Color background = getBackground();

        _sceneResult = new Scene.SceneBuilder(sceneName)
                .setAmbientLight(ambientLight)
                .setBackground(background)
                .setGeometries(geometries)
                .build();
    }

    /**
     * get the final scene from the xml file
     * @return the final scene
     */
    public Scene getSceneResult() {
        return _sceneResult;
    }

    /**
     * descriptor the background from the xml
     * @return the color of the background
     */
    private Color getBackground(){
        return getColor(_rootElement.getAttribute("background-color"));
    }

    /**
     * descriptor the ambient light from the xml
     * @return the ambient light of the scene
     */
    private AmbientLight getAmbientLight(){
        var ambientLightElement = (Element) _rootElement.getElementsByTagName("ambient-light").item(0);
        var color = ambientLightElement.getAttribute("color");
        var ka = ambientLightElement.getAttribute("Ka");
        if(ka == null || ka.isEmpty()){
            ka = "1";
        }
        return new AmbientLight(getColor(color), Double.parseDouble(ka));
    }

    /**
     * descriptor the geometries from the xml file
     * @return the geometries of the scene
     */
    private Geometries getGeometries(){
        Geometries geometries = new Geometries();
        var geometriesNodeList = _rootElement.getElementsByTagName("geometries").item(0).getChildNodes();
        for(int i = 0; i < geometriesNodeList.getLength(); i++){
            Node node = geometriesNodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                geometries.add(getGeometry((Element)node));
            }
        }
        return geometries;
    }

    /**
     * descriptor for the geometry from element node of the geometries
     * @param elementNode element node of some geometry in the xml file
     * @return the geometry
     */
    private Geometry getGeometry(Element elementNode){
        Geometry result = null;
        switch (elementNode.getNodeName()){
            case "sphere" :
                var pc = getPoint(elementNode.getAttribute("center"));
                var radius = Double.parseDouble(elementNode.getAttribute("radius"));
                result = new Sphere(radius , pc);
                break;
            case "triangle" :
                var p0 = getPoint(elementNode.getAttribute("p0"));
                var p1 = getPoint(elementNode.getAttribute("p1"));
                var p2 = getPoint(elementNode.getAttribute("p2"));
                result = new Triangle(p0, p1, p2);
                break;
            case "plane" :
                var q0 = getPoint(elementNode.getAttribute("q0"));
                var normal = getVector(elementNode.getAttribute("normal"));
                result = new Plane(q0, normal);
                break;
            case "polygon" :
                List<Point3D> vertices = new LinkedList<>();

                var verticesElements = elementNode.getElementsByTagName("vertices")
                        .item(0)
                        .getChildNodes();
                for(int i = 0; i < verticesElements.getLength(); i++){
                    var pElement = verticesElements.item(i);
                    if(pElement.getNodeType() == Node.ATTRIBUTE_NODE){
                        var p = getPoint(((Element)pElement).getAttribute("p" + i));
                        vertices.add(p);
                    }
                }
                result = new Polygon(vertices.toArray(Point3D[]::new));
                break;
            case "cylinder" :
                var pOrigen = getPoint(elementNode.getAttribute("p0"));
                var dir = getVector(elementNode.getAttribute("direction"));
                var radiusCy = Double.parseDouble(elementNode.getAttribute("radius"));
                var height = Double.parseDouble(elementNode.getAttribute("height"));
                result = new Cylinder(radiusCy, new Ray(pOrigen, dir), height);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * get point from the format of the points in the xml file
     * @param pointFormat the format of point in the xml file
     * @return the Point3D object
     */
    private Point3D getPoint(String pointFormat){
        String[] point = pointFormat.split(" ");
        return new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
    }

    /**
     * get color from the format of the colors in the xml file
     * @param colorFormat the format of color in the xml file
     * @return the Color object
     */
    private Color getColor(String colorFormat){
        var p = getPoint(colorFormat);
        return new Color(p.getX(), p.getY(), p.getZ());
    }

    /**
     * get vector from the format of the vectors in the xml file
     * @param pointFormat the format of point in the xml file
     * @return the Vector object
     */
    private  Vector getVector(String pointFormat){
        return new Vector(getPoint(pointFormat));
    }
}

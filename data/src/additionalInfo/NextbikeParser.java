package additionalInfo;


import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NextbikeParser{

	public static NextBikeStop getBikeStopByNumber(int number) {

	NextBikeStop stop = new NextBikeStop();
try {
	URL url = new URL("https://nextbike.net/maps/nextbike-official.xml?city=148");
   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   DocumentBuilder db = dbf.newDocumentBuilder();
   Document doc = db.parse(url.openStream());
   doc.getDocumentElement().normalize();
   
   NodeList nodeLst = doc.getElementsByTagName("place");

    for (int s = 0; s < nodeLst.getLength(); s++) {

     Node fstNode = nodeLst.item(s);

     if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

      Element fstElmnt = (Element) fstNode;
     if (Integer.parseInt(fstElmnt.getAttribute("number"))==number){
    	 stop.setName(fstElmnt.getAttribute("name"));
    	 stop.setLng(Float.parseFloat(fstElmnt.getAttribute("lng")));
    	 stop.setLat(Float.parseFloat(fstElmnt.getAttribute("lat")));
    	 stop.setBikes(Integer.parseInt(fstElmnt.getAttribute("bikes")));
    	 stop.setNumber(number);

    	 return stop;
    	}    
     }
    }
  } catch (Exception e) {
   e.printStackTrace();
  }
return stop;
 } 
	
	public static ArrayList getBikeStop() {
		ArrayList lines = new ArrayList<>();
try {
	URL url = new URL("https://nextbike.net/maps/nextbike-official.xml?city=148");
   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   DocumentBuilder db = dbf.newDocumentBuilder();
   Document doc = db.parse(url.openStream());
   doc.getDocumentElement().normalize();
   
   NodeList nodeLst = doc.getElementsByTagName("place");

    for (int s = 0; s < nodeLst.getLength(); s++) {

     Node fstNode = nodeLst.item(s);

     if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

      Element fstElmnt = (Element) fstNode;
    	 lines.add(fstElmnt.getAttribute("name")+" "+ fstElmnt.getAttribute("number"));
     }
    }
  } catch (Exception e) {
   e.printStackTrace();
  }
return lines;
 } 
	
	public static NextBikeStop getBikeStopByName(String name) {

		NextBikeStop stop = new NextBikeStop();
	try {
		URL url = new URL("https://nextbike.net/maps/nextbike-official.xml?city=148");
	   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   DocumentBuilder db = dbf.newDocumentBuilder();
	   Document doc = db.parse(url.openStream());
	   doc.getDocumentElement().normalize();
	   
	   NodeList nodeLst = doc.getElementsByTagName("place");

	    for (int s = 0; s < nodeLst.getLength(); s++) {

	     Node fstNode = nodeLst.item(s);

	     if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	      Element fstElmnt = (Element) fstNode;
	      
	     if ((fstElmnt.getAttribute("name").equals(name))){
	    	 stop.setName(fstElmnt.getAttribute("name"));
	    	 stop.setLng(Float.parseFloat(fstElmnt.getAttribute("lng")));
	    	 stop.setLat(Float.parseFloat(fstElmnt.getAttribute("lat")));
	    	 stop.setBikes(Integer.parseInt(fstElmnt.getAttribute("bikes")));
	    	 stop.setNumber(Integer.parseInt(fstElmnt.getAttribute("number")));

	    	 return stop;
	    	}    
	     }
	    }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	return stop;
	 } 

	public static ArrayList getBikeStopWithBikes(int left) {

		ArrayList list = new ArrayList();
	try {
		URL url = new URL("https://nextbike.net/maps/nextbike-official.xml?city=148");
	   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   DocumentBuilder db = dbf.newDocumentBuilder();
	   Document doc = db.parse(url.openStream());
	   doc.getDocumentElement().normalize();
	   
	   NodeList nodeLst = doc.getElementsByTagName("place");

	    for (int s = 0; s < nodeLst.getLength(); s++) {

	     Node fstNode = nodeLst.item(s);

	     if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	      Element fstElmnt = (Element) fstNode;
	      
	     if ((Integer.parseInt(fstElmnt.getAttribute("bikes")))>left){
	    	 list.add((fstElmnt.getAttribute("name"))+" "+(fstElmnt.getAttribute("number"))+" "+(fstElmnt.getAttribute("bikes")));
	    	}    
	     }
	    }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	return list;
	 } 
	

}



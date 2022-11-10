import java.io.File;  // Import the File class
import java.net.MalformedURLException; 
import java.net.URL;
import java.io.*;    
import java.net.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;

import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
    import org.w3c.dom.Node;  
    import org.w3c.dom.Element;
    import java.io.FileWriter;  
    import java.io.File; 
    import java.io.IOException;
    import org.json.simple.JSONObject;
    import java.sql.*; 
    import java.sql.Statement;
    

public class UltimateSystem{
  public static void main(String[] args)throws Exception, IOException, ParserConfigurationException, SAXException, MalformedURLException, TransformerConfigurationException,TransformerException {
    
     String url1 = "jdbc:mysql://localhost:3307/admin";
   String username ="root";
   String password ="";
   
   String query2 = "select max(id) from coupon_master";
   Class.forName("com.mysql.cj.jdbc.Driver");  
   Connection con = DriverManager.getConnection(url1,username,password);
   Statement sp = con.createStatement();
  ResultSet rm = sp.executeQuery("select max(id) from coupon_master");
 rm.next();
 int myMaxId = rm.getInt(1);
   System.out.println(myMaxId);
       int x = 1;
   for(x=1; x<=myMaxId; x++){
   Class.forName("com.mysql.cj.jdbc.Driver");  
   Connection cons = DriverManager.getConnection(url1,username,password);
   
   String query = "select coupon_code from coupon_master where id=" + x; 
   String query1 = "select cart_min_value from coupon_master where id=" + x;
      Statement st = cons.createStatement();
   Statement sr = cons.createStatement();
    ResultSet rs = st.executeQuery(query);
     ResultSet rw = sr.executeQuery(query1);
    
    rs.next();
    rw.next();
    String name1 = rw.getString("cart_min_value");
    name1 +=".json";
    String name = rs.getString("coupon_code");
    System.out.println(name1);
    st.close();
    con.close();

     //Fetch the xml file from the website
       URL url = new URL(name);
        URLConnection urlConnection = url.openConnection();
         InputStream in = new BufferedInputStream(urlConnection.getInputStream()); 

    //your code
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse( in );
  
    try{  
    //Creating a JSONObject object
      JSONObject jsonObject = new JSONObject();
        FileWriter file2 = new FileWriter(name1);
          doc.getDocumentElement().normalize();  
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
      NodeList nodeList = doc.getElementsByTagName("metadata");  
    
    int xx = nodeList.getLength();
    System.out.println(xx);
    
    // nodeList is not iterable, so we are using for loop  
      for (int itr = 0; itr < nodeList.getLength(); itr++){ 
        Node node = nodeList.item(itr);  
      if (node.getNodeType() == Node.ELEMENT_NODE){
        Element eElement = (Element) node;
         jsonObject.put("Title", eElement.getElementsByTagName("dc:title").item(0).getTextContent());
         jsonObject.put("Link", eElement.getElementsByTagName("dc:identifier").item(0).getTextContent());
         jsonObject.put("Author", eElement.getElementsByTagName("dc:date").item(0).getTextContent());
         jsonObject.put("Author", eElement.getElementsByTagName("dc:description").item(0).getTextContent());

                    }
      
       try{
         file2.write(jsonObject.toJSONString() + System.getProperty( "line.separator" ));
          file2.write(System.getProperty( "line.separator" ));
              } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();}  
           }  
         }  catch (Exception e){e.printStackTrace();}  
         }
         
          

       }  
     }  
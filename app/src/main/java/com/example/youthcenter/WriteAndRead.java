package com.example.youthcenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WriteAndRead {

    private ContextWrapper contextWrapper;
    Events events = null;
    private String file;
    private static WriteAndRead writeAndRead = null;

    public static WriteAndRead getInstance() {
        if (writeAndRead == null) {
            writeAndRead = new WriteAndRead();
        }
        return writeAndRead;
    }


    public void parseXML(Context context) {
        File xmlFile = new File(context.getFilesDir() + "/data.xml");
        Events eList = Events.getInstance();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("event");
            for(int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String title = element.getAttribute("title");
                    String date = element.getElementsByTagName("date").item(0).getTextContent();
                    String start = element.getElementsByTagName("start").item(0).getTextContent();
                    String end = element.getElementsByTagName("end").item(0).getTextContent();
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    String place = element.getElementsByTagName("place").item(0).getTextContent();
                    String desc = element.getElementsByTagName("desc").item(0).getTextContent();
                    String visitors = element.getElementsByTagName("visitors").item(0).getTextContent();

                    eList.AddToArray(new Event(title, date, start, end, age, place, desc, Integer.parseInt(visitors), 1, false));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeXML(Context context) {
        contextWrapper = new ContextWrapper(context);

        File filePath = context.getFilesDir();
        System.out.println(filePath);
        events = Events.getInstance();

        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath + "/data.xml"));

            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();


            ArrayList<Event> eList = events.geteList();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "events");
            serializer.attribute("", "number", String.valueOf(eList.size()));

            for (Event event : eList) {
                serializer.startTag("", "event");
                serializer.attribute("", "title", event.getTitle());

                serializer.startTag("", "date");
                serializer.text(event.getDate());
                serializer.endTag("", "date");

                serializer.startTag("", "start");
                serializer.text(event.gettStart());
                serializer.endTag("", "start");

                serializer.startTag("", "end");
                serializer.text(event.gettEnd());
                serializer.endTag("", "end");

                serializer.startTag("", "age");
                serializer.text(event.getAge());
                serializer.endTag("", "age");

                serializer.startTag("", "place");
                serializer.text(event.getPlace());
                serializer.endTag("", "place");

                serializer.startTag("", "description");
                serializer.text(event.getDesc());
                serializer.endTag("", "description");

                serializer.startTag("", "visitors");
                serializer.text(String.valueOf(event.getVisitorAmount()));
                serializer.endTag("", "visitors");
                serializer.endTag("", "event");
            }
            serializer.endTag("", "events");
            serializer.endDocument();
            serializer.flush();
            String result = writer.toString();

            fos.write(result.getBytes());
            fos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

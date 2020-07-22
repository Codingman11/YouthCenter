package com.example.youthcenter;

import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

public class WriteAndRead extends AppCompatActivity {

    Events events = null;

    private static WriteAndRead writeAndRead = null;

    public static WriteAndRead getInstance() {
        if (writeAndRead == null) {
            writeAndRead = new WriteAndRead();
        }
        return writeAndRead;
    }


    public WriteAndRead() {

    }


    public void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open(getFilesDir() + "/" + "data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
        } catch (XmlPullParserException | IOException e) {

        }
    }

    public void writeXML() {

        events = Events.getInstance();

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
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

                serializer.startTag("", "age");
                serializer.text(event.getAge());
                serializer.endTag("", "age");

                serializer.startTag("", "place");
                serializer.text(event.getPlace());
                serializer.endTag("", "place");

                serializer.startTag("", "description");
                serializer.text(event.getDesc());
                serializer.endTag("", "description");

                serializer.startTag("", "visitorAmount");
                serializer.text(event.getAge());
                serializer.endTag("", "visitorAmount");
            }
            serializer.endTag("", "event");
            serializer.endDocument();
            String result = writer.toString();


        } catch (Exception e) {

        }
    }


}

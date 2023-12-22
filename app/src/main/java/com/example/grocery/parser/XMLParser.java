package com.example.grocery.parser;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Xml;

import com.example.grocery.Grocery;
import com.example.grocery.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private List<Grocery> groceries = new ArrayList<>();
    private Grocery g;
    private String text;

    public List<Grocery> parse(InputStream inputStream)
    {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "utf-8");
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = parser.getName();

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("Grocery"))
                        {
                            g = new Grocery();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagName.equalsIgnoreCase("Grocery"))
                        {
                            groceries.add(g);
                        }
                        else if(tagName.equalsIgnoreCase("Name"))
                        {
                            g.setName(text);
                        }
                        else if(tagName.equalsIgnoreCase("Price"))
                        {
                            g.setPrice(Float.parseFloat(text));
                        }
                        else if(tagName.equalsIgnoreCase("Perishable"))
                        {
                            g.setPerishable(text.equalsIgnoreCase("YES"));
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            throw new RuntimeException(e);
        }
        return groceries;
    }

}

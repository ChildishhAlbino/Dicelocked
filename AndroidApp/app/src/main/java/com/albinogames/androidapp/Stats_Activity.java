package com.albinogames.androidapp;

import android.graphics.Typeface;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;
import org.xml.sax.helpers.AttributesImpl;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Stats_Activity extends AppCompatActivity {
    private enum Attribute{
        Empty,
        Name,
        GamesPlayed,
        WinPer,
    }
private TextView tvPN;
private TextView tvWP;
private TextView tvGP;

    private XmlPullParser xpp;
    private Attribute a = Attribute.Empty;
    boolean font = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_);
        final Button b = (Button)findViewById(R.id.ToggleFont_Button);
        tvPN = (TextView)findViewById(R.id.PlayerName_text);
        tvWP = (TextView)findViewById(R.id.winPer_text);
        tvGP = (TextView)findViewById(R.id.GamesPlayed_text);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleFont();
            }
        });


        ReadXML();
    }

    private void ReadXML(){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            InputStream f = this.getResources().openRawResource(R.raw.player);
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xpp.setInput(f, null);
            int i = xpp.getEventType();
            while (i != XmlPullParser.END_DOCUMENT){
                ParseXML(i);
                i = xpp.next();
            }
        } catch (XmlPullParserException e){
            e.toString();
            System.out.println("XML error");
        } catch (IOException e) {
            e.toString();
            System.out.println("IO exception");
        }
    }

    private void ParseXML(int i){
        switch(i){
            case XmlPullParser.START_TAG:
                a = ParseAttribute(xpp.getName());
                break;
            case XmlPullParser.TEXT:
                SetTextByEnum(a, xpp.getText());
            default:
                // do nothing
                a = Attribute.Empty;
                break;
        }
    }

    private Attribute ParseAttribute(String s){
        switch(s){
            case "name":
                a = Attribute.Name;
                System.out.println("Got player name!");
                break;
            case "gamesplayed":
                a = Attribute.GamesPlayed;
                System.out.println("Got games played");
                break;
            case "winper":
                a = Attribute.WinPer;
                System.out.println("Got win%");
                break;
        }
        return a;
    }

    private void SetTextByEnum(Attribute a, String s){
        switch(a){
            case Name:
                DisplayPlayerName(s);
                break;
            case GamesPlayed:
                DisplayGamesPlayed(s);
                break;
            case WinPer:
                DisplayWinPer(s);
                break;
            default:
                break;
        }
    }

    private void DisplayPlayerName(String s){
        // set button text to provided string
        tvPN.setText(s+":");
    }

    private void DisplayGamesPlayed(String s){
        // set button text to provided string
        tvWP.setText("Games Played: "+s);
    }
    private void DisplayWinPer(String s){
        // set button text to provided string
        tvGP.setText("Win Percentage: "+s);
    }

    private void ToggleFont(){
        Typeface tf = Typeface.DEFAULT;
        if(font){
            //set player font to ARIAL
            tf = Typeface.create("Arial", Typeface.BOLD);
        }
        else{
            //set player font to SOMETHING ELSE
            tf = getResources().getFont(R.font.eeriot);
        }
        System.out.println(tf.toString());
        font = !font;
       tvPN.setTypeface(tf);
    }
}

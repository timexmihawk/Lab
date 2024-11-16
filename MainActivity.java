package com.bijoy.xmljson;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    //Button btnXml, btnJson;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Button btnXml = findViewById(R.id.btn_xml);
        //Button btnJson = findViewById(R.id.btn_json);
        tvContent = findViewById(R.id.tv_content);

    }

    public void displayXmlContent(View view) {
        try  {
            InputStream inputStream = getResources().openRawResource(R.raw.sample);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            StringBuilder xmlContent = new StringBuilder();
            for (int eventType = parser.getEventType(); eventType != parser.END_DOCUMENT; eventType = parser.next()) {
                if (eventType == parser.START_TAG && "item".equals(parser.getName())) {
                    xmlContent.append(parser.nextText()).append("\n");
                }
            }
            tvContent.setText(xmlContent.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            tvContent.setText("Error reading XML file.");
        }
    }



    public void displayJsonContent(View view) {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.samples);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String jsonContent = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonContent);

            // Retrieve each JSON field and append it to a StringBuilder
            StringBuilder jsonText = new StringBuilder();
            jsonText.append(jsonObject.getString("message")).append("\n");
            jsonText.append(jsonObject.getString("additional_message")).append("\n");
            jsonText.append(jsonObject.getString("extra_info"));

            tvContent.setText(jsonText.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            tvContent.setText("Error reading JSON file.");
        }
    }

}

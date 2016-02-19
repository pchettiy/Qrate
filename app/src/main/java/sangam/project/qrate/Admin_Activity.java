package sangam.project.qrate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Admin_Activity extends AppCompatActivity {

    ArrayList<String> tops;
    Spinner spinner;
    String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent in=getIntent();
        setContentView(R.layout.activity_admin_);
        spinner= (Spinner) findViewById(R.id.spinner2);
        if(in!=null){
            tops=in.getStringArrayListExtra("list");
        }
        ArrayList<String> topics=new ArrayList<>();
        for(int i=1;i<tops.size();i++)
            topics.add(tops.get(i));
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,topics);
         spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topic=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void check(View view){
        Intent intent=new Intent(getApplicationContext(),Admin_check_topics.class);
        intent.putExtra("topic",topic);
        startActivity(intent);
    }


}

package sangam.project.qrate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Public_select extends AppCompatActivity {

    List<String> tracks;
    Spinner spinner;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        spinner= (Spinner) findViewById(R.id.spinner);
        tracks=new ArrayList<>();
        tracks.add("Web dev");
        tracks.add("App dev");
        tracks.add("Python");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,tracks);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intent=new Intent(getApplicationContext(),ViewTopics.class);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    intent.putExtra("track","WebDev");
                }
                else if(position==1){
                    intent.putExtra("track","AppDev");
                }
                else
                    intent.putExtra("track","Python");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void proceed(View v){
        startActivity(intent);
    }
}

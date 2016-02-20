package sangam.project.qrate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin_Allocation extends AppCompatActivity {

    List<String> levelList;
    List<String> resourceKindList;
    Spinner spinnerLevel;
    Spinner spinnerResouceKind;
    EditText editTextDuration;
    RatingBar ratingBar;
    Button buttonSubmit;
    String track="";
    String url="";
    String level="",resourceKind="";
    String rating="";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__allocation);
        levelList = new ArrayList<>(Arrays.asList("Beginner","Intermediate","Advanced"));
        resourceKindList = new ArrayList<>(Arrays.asList("Video","Course","WebTutorial"));
        /*levelList=new ArrayList<>();
        levelList.add("Beginner");
        levelList.add("Intermediate");
        levelList.add("Advanced");
        resourceKindList=new ArrayList<>();
        resourceKindList.add("WebTutorial");
        resourceKindList.add("Course");
        resourceKindList.add("Video");*/
        spinnerLevel = (Spinner)findViewById(R.id.spinner3);
        spinnerResouceKind = (Spinner)findViewById(R.id.spinner4);
        editTextDuration = (EditText)findViewById(R.id.editText2);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        buttonSubmit = (Button)findViewById(R.id.button5);
        Bundle bundle = getIntent().getExtras();
        track = bundle.getString("track");
        url = bundle.getString("url");
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,levelList);
        ArrayAdapter adapterResourceList = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,resourceKindList);
        spinnerLevel.setAdapter(adapterLevel);
        spinnerResouceKind.setAdapter(adapterResourceList);

        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level=parent.getItemAtPosition(position).toString();
                Log.d("level",level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }) ;
        spinnerResouceKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resourceKind=parent.getItemAtPosition(position).toString();
                Log.d("resourcekind",resourceKind);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*spinnerResouceKind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resourceKind = levelList.get(i);
            }
        });*/

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v+"";
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Log.d("rating",rating);
                if(level.equals("")||resourceKind.equals("")||rating.equals(""))
                    Toast.makeText(Admin_Allocation.this,"Please select Properly",Toast.LENGTH_SHORT).show();
                else {
                    new AsyncSendAllocationTask().execute(editTextDuration.getText().toString());
                    //new Async
                }
            }
        });
    }
    public void checkthesite(View v){
        String http="http://";
        if(!url.startsWith("htt"))
            url=http+url;
        Log.d("NEWURL",url);
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public class AsyncSendAllocationTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(Admin_Allocation.this,"Sending","",true,false);
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/storeapproved.php";
            try{

                URL urld=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) urld.openConnection();
                    String postParameters="track="+track.toLowerCase()+"&kind="+resourceKind.toLowerCase()
                                    +"&min="+strings[0].toLowerCase()+"&url="+url+"&level="+level.toLowerCase()
                                    +"&rating="+rating.toLowerCase();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    urlConnection.setFixedLengthStreamingMode(
                            postParameters.getBytes().length);
                    PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                    out.print(postParameters);
                    out.close();
                    InputStream inputStream=urlConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    String line="";

                    while((line=bufferedReader.readLine())!=null){
                        result+=line;
                    }
                    if(result!=null)
                    Log.d("DATA",result);
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String mString) {
            super.onPostExecute(mString);
            Toast.makeText(Admin_Allocation.this,"Submitted Successfully",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }

    }
}

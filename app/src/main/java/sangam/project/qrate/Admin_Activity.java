package sangam.project.qrate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);
        spinner= (Spinner) findViewById(R.id.spinner);
        Intent i=getIntent();
        if(i!=null){
            email=i.getStringExtra("track");}
        new AsyncGetTopics().execute(email);
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,tops);
       // spinner.setAdapter(dataAdapter);
    }

    public class AsyncGetTopics extends AsyncTask<String,Void,ArrayList>{


        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList<String> topic_list=new ArrayList<>();
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/admintopics.php";
            //String downloadurl="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=44db6a862fba0b067b1930da0d769e98";
            try{

                URL url=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    String postParameters="email="+params[0];
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
                    String result="";
                    while((line=bufferedReader.readLine())!=null){
                        result+=line;
                    }
                    Log.d("DATA",result);
                    inputStream.close();
                    JSONObject details=new JSONObject(result);
                    //for(int i=0;i<testdetails.length();i++){
                    /*
                        JSONObject stuff=testdetails.getJSONObject("coord");
                        String detail=stuff.getString("lon");
                        test.add(detail);
                        */
                    JSONArray topics=details.getJSONArray("topics");
                    for(int i=0;i<topics.length();i++){
                        Log.d("topic",topics.get(i).toString());
                        topic_list.add(topics.get(i).toString());
                    }

                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return topic_list;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            if(arrayList==null){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            else{
                tops=arrayList;
            }
            //listView.setAdapter(new Adapter_Course(getContext(),arrayList));
            //pd.dismiss();

            //spinner.setAdapter(dataAdapter);
        }
    }
}

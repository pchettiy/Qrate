package sangam.project.qrate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class Submit_URL extends AppCompatActivity {

    Context context;
    String url_to;
    EditText edturl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__url);

        edturl= (EditText) findViewById(R.id.url_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this.getApplicationContext();

    }
    public void submit(View v){
        url_to=edturl.getText().toString();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(getApplicationContext());
                pd.setMessage("Submitting...");
                pd.show();

                // start time consuming background process here
            }
        }, 1000);*/
        new Asyncsubmit().execute(url_to);

    }

    public class Asyncsubmit extends AsyncTask<String,Void,String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params ) {

            String result = null;
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/unapproved.php";
            //String downloadurl="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=44db6a862fba0b067b1930da0d769e98";
            try{

                URL url=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    String postParameters="url="+params[0];
                    Log.d("URL",params[0]);
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
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            if(string==null){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if(string.contains("200")) {
                Toast.makeText(getApplicationContext(), "Successfully posted", Toast.LENGTH_SHORT).show();

            }
            //pd.dismiss();
        }
    }
}

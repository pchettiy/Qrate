package sangam.project.qrate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Admin_check extends AppCompatActivity {


    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check);
        email= (EditText) findViewById(R.id.editText);
    }
    public void checkadmin(View V){
        String mail=email.getText().toString();
        new AsyncAdminCheck().execute(mail);

    }
    public class AsyncAdminCheck extends AsyncTask<String,Void,ArrayList> {




        @Override
        protected ArrayList doInBackground(String... params ) {

            String result ="";
            ArrayList adminstatus=new ArrayList();
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/admintopics.php";
            //String downloadurl="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=44db6a862fba0b067b1930da0d769e98";
            try{

                URL url=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    String postParameters="email="+params[0];
                    Log.d("Email",params[0]);
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
                    JSONObject details=new JSONObject(result);
                    Log.d("JSONOBJECT",details.toString());
                    adminstatus.add(details.getString("status"));

                    JSONArray array=details.getJSONArray("topics");
                    for(int i=0;i<array.length();i++)
                        adminstatus.add(array.get(i));


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return adminstatus;
        }




        @Override
        protected void onPostExecute(ArrayList string) {
            super.onPostExecute(string);
            if(string==null){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            else if(string.get(0).toString().contains("0")) {
                Toast.makeText(getApplicationContext(), "You are a normal user", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),Public_select.class);
                startActivity(intent1);
            }
            else{
                Toast.makeText(getApplicationContext(),"You are an admin",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Admin_Activity.class);
                intent.putExtra("list",string);
                startActivity(intent);
            }
            //pd.dismiss();
        }
    }
}

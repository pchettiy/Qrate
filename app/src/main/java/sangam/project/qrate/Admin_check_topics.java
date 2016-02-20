package sangam.project.qrate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class Admin_check_topics extends AppCompatActivity {

    ProgressDialog pd;
    ArrayList<AdminTopic> admintopicslist;
    String track;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if(intent!=null){
            track=intent.getStringExtra("topic");
        }
        pd = ProgressDialog.show(this,"Loading","",false,false);
        new AsyncGetTitleAndUrl().execute(track);
        setContentView(R.layout.activity_admin_check_topics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView= (ListView) findViewById(R.id.listView3);

    }

    public class AsyncGetTitleAndUrl extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/storeapproved.php";
            try{

                URL url=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    String postParameters="topic="+strings[0];
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Logging", s);
            try {
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    AdminTopic adminTopic = new AdminTopic();
                    adminTopic.title = object.getString("topic");
                    adminTopic.url = object.getString("url");
                    admintopicslist.add(adminTopic);
                }
            AdminAdapter adminAdapter = new AdminAdapter(Admin_check_topics.this,admintopicslist);
            listView.setAdapter(adminAdapter);
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class AdminAdapter extends ArrayAdapter{
        Context context;
        ArrayList<AdminTopic> topics;
        public AdminAdapter(Context context, ArrayList<AdminTopic> arrayList) {
            super(context, R.layout.admin_topics_single_item,arrayList);
            this.topics=arrayList;
            this.context=context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View v=inflater.inflate(R.layout.admin_topics_single_item,parent,false);
            TextView title= (TextView) v.findViewById(R.id.title_text);
            title.setText(topics.get(position).title);
            RelativeLayout cardlayout= (RelativeLayout) v.findViewById(R.id.card_layout);
            cardlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_check_topics.this,Admin_Allocation.class);
                    intent.putExtra("track",track);
                    intent.putExtra("url",admintopicslist.get(position).url);
                    startActivity(intent);
                }
            });
            return v;
        }
    }
}

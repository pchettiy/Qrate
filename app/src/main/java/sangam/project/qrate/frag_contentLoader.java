package sangam.project.qrate;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class frag_contentLoader extends Fragment {

    ListView listView;
    ArrayList<Topic> topiclist;
    String level;
    String track;
    public frag_contentLoader() {
        // Required empty public constructor
    }
    public static frag_contentLoader newInstance(String param,String track){
        frag_contentLoader fragment=new frag_contentLoader();
        Bundle args=new Bundle();
        args.putString("param",param);
        args.putString("track",track);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            level =getArguments().getString("param");
            track=getArguments().getString("track");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_frag_content_loader, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
        new Asyncgetdata().execute(track, level);


        return view;

    }

    public class Asyncgetdata extends AsyncTask<String,Void,ArrayList<Topic>> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Topic> doInBackground(String... params ) {

            ArrayList<Topic> result=new ArrayList<>();
            String downloadurl="https://spider.nitt.edu/~praba1110/qrate/tracks.php";
            //String downloadurl="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=44db6a862fba0b067b1930da0d769e98";
            try{

                URL url=new URL(downloadurl);
                try{
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    String postParameters="track="+params[0]+"&level="+params[1];
                    Log.d("LEVEl",params[1]);
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
                    String res ="";
                    while((line=bufferedReader.readLine())!=null){
                        res+=line;
                    }
                    Log.d("RES",res);
                    JSONArray array=new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        Topic topic=new Topic();
                        topic.url= object.getString("url");
                        topic.title=object.getString("topic");
                        topic.level=object.getInt("level");
                        topic.mins=object.getString("time");Log.d("time",object.getString("time"));
                        topic.kind=object.getString("kind");
                        topic.rating=object.getString("rating");
                        if(level.contentEquals("beginner")&&topic.level==0||
                                level.contentEquals("intermediate")&&topic.level==1||
                                level.contentEquals("expert")&&topic.level==2)
                        result.add(topic);
                    }
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return result;
        }




        @Override
        protected void onPostExecute(ArrayList<Topic> string) {
            super.onPostExecute(string);
            Log.d("topic",string.get(0).title);
            if(string==null){
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            topiclist=string;
            listView.setAdapter(new Topic_Adapter(getContext(),topiclist));
            //pd.dismiss();
        }
    }



}

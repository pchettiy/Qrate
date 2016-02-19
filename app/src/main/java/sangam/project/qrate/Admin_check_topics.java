package sangam.project.qrate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Admin_check_topics extends AppCompatActivity {

    ArrayList<AdminTopic> admintopicslist;
    String track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if(intent!=null){
            track=intent.getStringExtra("topic");
        }
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
        ListView listView= (ListView) findViewById(R.id.listView3);
        listView.setAdapter(new AdminAdapter(this,admintopicslist));

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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View v=inflater.inflate(R.layout.admin_topics_single_item,parent,false);
            TextView title= (TextView) v.findViewById(R.id.title_text);
            title.setText(topics.get(position).title);
            RelativeLayout cardlayout= (RelativeLayout) v.findViewById(R.id.card_layout);
            cardlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return v;
        }
    }
}

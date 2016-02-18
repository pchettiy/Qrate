package sangam.project.qrate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by praba1110 on 16/2/16.
 */
public class Topic_Adapter extends ArrayAdapter {
    ArrayList<Topic> topics;
    Context context;
    public Topic_Adapter(Context context, ArrayList<Topic> topics) {

        super(context, R.layout.cardview,topics);
        this.topics=topics;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View v=inflater.inflate(R.layout.cardview,parent,false);
        TextView title= (TextView) v.findViewById(R.id.info_text);
        TextView mins= (TextView) v.findViewById(R.id.textView2);
        RelativeLayout cardlayout= (RelativeLayout) v.findViewById(R.id.card_layout);
        cardlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=topics.get(position).url;
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        title.setText(topics.get(position).title);
        mins.setText(topics.get(position).title);

        return v;
    }
}

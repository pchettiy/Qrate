package sangam.project.qrate;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class frag_contentLoader extends Fragment {


    String mParam;
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
            mParam=getArguments().getString("param");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_content_loader, container, false);

    }

}

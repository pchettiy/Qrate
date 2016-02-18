package sangam.project.qrate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class First_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
    }
    public void admin_check(View v){
        Intent intent=new Intent(getApplicationContext(),Admin_check.class);
        startActivity(intent);
    }
    public void public_go(View v){
        Intent intent=new Intent(getApplicationContext(),Public_select.class);
        startActivity(intent);

    }
}

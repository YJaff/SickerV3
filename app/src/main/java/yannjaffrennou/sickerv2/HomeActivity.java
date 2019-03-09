package yannjaffrennou.sickerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yannjaffrennou.sickerv2.R;

public class HomeActivity extends AppCompatActivity {
    public static final String BODY = "yannjaffrennou.sickerv3.body";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View v) {
        View view = v;
        switch (view.getId()) {
            case R.id.imageHead :
                changeActivity("head");
                break;

            case R.id.imageShoulder :
                changeActivity("shoulder");
                break;

            case R.id.imageElbows :
                changeActivity("elbow");
                break;

            case R.id.imageWrist :
                changeActivity("wrist");
                break;

            case R.id.imageHands :
                changeActivity("hand");
                break;

            case R.id.imageKnee :
                changeActivity("knee");
                break;

            case R.id.imageFeet :
                changeActivity("feet");
                break;

            default: changeActivity("defaut");


        }
    }

    public void changeActivity(String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BODY, body);
        startActivity(intent);
    }


}

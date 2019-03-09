package yannjaffrennou.sickerv2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import yannjaffrennou.sickerv2.R;

public class MainActivity extends AppCompatActivity {


    int windowwidth;
    int screenCenter, screenCenterY;
    int x_cord, y_cord, x, y, xCard, yCard;
    int Likes = 0;
    public RelativeLayout parentView;
    float alphaValue = 0, rotation;
    private Context context;
    int likeCpt;
    String body;
    public static final String BODY = "yannjaffrennou.sickerv3.body";

    ArrayList<UserDataModel> userDataModelArrayList;

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        body = intent.getStringExtra(HomeActivity.BODY);

        context = MainActivity.this;

        parentView = (RelativeLayout) findViewById(R.id.main_layoutview);

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();

        screenCenter = windowwidth / 2;
        screenCenterY = getWindowManager().getDefaultDisplay().getHeight() / 2;
        System.out.println(screenCenterY);

        userDataModelArrayList = new ArrayList<>();

        getArrayData();
        likeCpt = 0;


        for (int i = 0; i < userDataModelArrayList.size(); i++) {

            LayoutInflater inflate =
                    (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View containerView = inflate.inflate(R.layout.custom_tinder_layout, null);

            RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);


            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            containerView.setLayoutParams(layoutParams);

            containerView.setTag(i);

//            m_view.setRotation(i);
//            containerView.setPadding(0, i, 0, 0);

            LayoutParams layoutTvParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


            // ADD dynamically like TextView on image.
            final TextView tvLike = new TextView(context);
            tvLike.setLayoutParams(layoutTvParams);
            tvLike.setPadding(30, 10, 30, 10);
            tvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvLike.setText("YES");
            tvLike.setGravity(Gravity.CENTER);
            tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvLike.setTextSize(25);
            tvLike.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
            tvLike.setX(20);
            tvLike.setY(100);
            tvLike.setRotation(-20);
            tvLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvLike);


//            ADD dynamically dislike TextView on image.
            final TextView tvUnLike = new TextView(context);
            tvUnLike.setLayoutParams(layoutTvParams);
            tvUnLike.setPadding(30, 10, 30, 10);
            tvUnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnunlikeback));
            tvUnLike.setText("NO");
            tvUnLike.setGravity(Gravity.CENTER);
            tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvUnLike.setTextSize(25);
            tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            tvUnLike.setX(windowwidth - 200 - tvUnLike.getWidth());
            System.out.println("Version 1: "+(900-20-tvUnLike.getWidth())+" Version 2 : "+(windowwidth - 200 - tvUnLike.getWidth()));
            tvUnLike.setY(100);
            tvUnLike.setRotation(20);
            tvUnLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvUnLike);


            TextView tvName = (TextView) containerView.findViewById(R.id.tvName);
            //LinearLayout linearLayout = (LinearLayout) containerView.findViewById(R.id.linear_layout);


            tvName.setText(userDataModelArrayList.get(i).getName());
            //relativeLayoutContainer.setBackgroundColor(userDataModelArrayList.get(i).getColor());

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new OnTouchListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            x = (int) event.getRawX();
                            y = (int) event.getRawY();


                            Log.v("On touch", x + " " + y);
                            break;
                        case MotionEvent.ACTION_MOVE:

                            x_cord = (int) event.getRawX();
                            // smoother animation.
                            y_cord = (int) event.getRawY();


                            xCard = (x_cord - x);
                            yCard = (y_cord - y);
                            containerView.setX(xCard);
                            containerView.setY(yCard);

                            if (y <= screenCenterY) {
                                containerView.setRotation((float) ((xCard) * (Math.PI / 128)));
                            } else {
                                containerView.setRotation((float) (-(xCard) * (Math.PI / 128)));
                            }


                            if (xCard >= screenCenter / 4) {
                                tvLike.setAlpha(1);
                                if (xCard >= screenCenter / 2) {
                                    Likes = 2;
                                    tvUnLike.setAlpha(0);
                                } else {
                                    Likes = 0;
                                    tvUnLike.setAlpha(0);
                                }
                            } else {
                                Likes = 0;
                                tvLike.setAlpha(0);
                                if (xCard <= -screenCenter / 4) {
                                    tvUnLike.setAlpha(1);
                                    if (xCard <= -screenCenter / 2) {
                                        Likes = 1;
                                        tvLike.setAlpha(0);
                                    } else {
                                        Likes = 0;
                                        tvLike.setAlpha(0);
                                    }
                                } else {
                                    Likes = 0;
                                    tvUnLike.setAlpha(0);
                                }
                            }


                            break;
                        case MotionEvent.ACTION_UP:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            tvUnLike.setAlpha(0);
                            tvLike.setAlpha(0);

                            if (Likes == 0) {
                                Toast.makeText(context, "NOTHING", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Nothing");
                                containerView.setX(0);
                                containerView.setY(0);
                                containerView.setRotation(0);
                            } else if (Likes == 1) {
                                Toast.makeText(context, "NO", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "UNLIKE");
                                parentView.removeView(containerView);
                                likeCpt++;
                            } else if (Likes == 2) {
                                Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Liked");
                                parentView.removeView(containerView);
                                likeCpt++;
                            }
                            if (likeCpt >= userDataModelArrayList.size()) {
                                nextActivity();
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }

            });

            parentView.addView(containerView);

        }


    }

    public void clickOnLayout(View view) {
        System.out.println("clique sur le LAYOUT");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    private void getArrayData() {
        String[] str = new String[20];
        switch (body) {
            case "head":
                str = getResources().getStringArray(R.array.not_implemented);
                break;
            case "shoulder":
                str = getResources().getStringArray(R.array.shoulder);
                break;
            case "elbow":
                str = getResources().getStringArray(R.array.elbow);
                break;
            case "wrist":
                str = getResources().getStringArray(R.array.wrist);
                break;
            case "hand":
                str = getResources().getStringArray(R.array.hand);
                break;
            case "knee":
                str = getResources().getStringArray(R.array.knee);
                break;
            case "feet":
                str = getResources().getStringArray(R.array.feet);
                break;
            default:
                str = getResources().getStringArray(R.array.not_implemented);
                break;
        }


        for (int i = 0; i < str.length; i++) {
            if (str[i] != "") {
                UserDataModel model = new UserDataModel();
                model.setName(str[i]);
                userDataModelArrayList.add(model);
            }
        }

        Collections.reverse(userDataModelArrayList);

    }

    public void bypass(View view) {
        nextActivity();
    }

    public void nextActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(BODY, body);
        startActivity(intent);
    }

    public void moveCard(View view, float rotation, int x, int y) {
        view.setRotation(rotation);
        view.setX(x);
        view.setY(y);
    }
}
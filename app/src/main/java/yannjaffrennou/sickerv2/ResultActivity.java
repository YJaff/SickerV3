package yannjaffrennou.sickerv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yannjaffrennou.sickerv2.R;

public class ResultActivity extends AppCompatActivity {
    ArrayList<DoctorDataModel> doctorDataModelArrayList;
    LinearLayout parentView;
    LockableScrollView scrollView;
    int x_cord, y_cord, x, y, xCard, i;
    int screenCenter, screenCenterY;
    int windowwidth;
    int Likes = 0;
    private Context context;
    float alphaValue = 0;
    boolean scrollBlocker;
    View lastContainerView;
    TextView lasttvLike;
    TextView lasttvUnlike;
    String body;
    TextView tvResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        doctorDataModelArrayList = new ArrayList<>();
        parentView = (LinearLayout) findViewById(R.id.doctor_linear_layout);
        scrollView = (LockableScrollView) findViewById(R.id.scrollView);

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenter = windowwidth / 2;
        screenCenterY = getWindowManager().getDefaultDisplay().getHeight() / 2;
        context = ResultActivity.this;
        body = getIntent().getStringExtra(MainActivity.BODY);
        tvResult = (TextView) findViewById(R.id.textView6);
        tvResult.setText("There is a probability of 73% that you sprined your " + body);
        switch (body) {
            case "head":
                tvResult.setText("This function is not implemented");
                break;
            case "shoulder":
                tvResult.setText("There is a probability of 63% that you dislocated your shoulder");
                break;
            case "elbow":
                tvResult.setText("There is a probability of 74% that you sprained your elbow");
                break;
            case "wrist":
                tvResult.setText("There is a probability of 76% that you sprained your wrist");
                break;
            case "hand":
                tvResult.setText("There is a probability of 82% that you sprained your finger");
                break;
            case "knee":
                tvResult.setText("There is a probability of 78% that you sprained your knee");
                break;
            case "feet":
                tvResult.setText("There is a probability of 84% that you sprained your ankle");
                break;

        }


        getArrayData();

        //Dans la boucle for

        for (int i = 0; i < doctorDataModelArrayList.size(); i++) {
            final View containerView = createCard(doctorDataModelArrayList.get(i));

            parentView.addView(containerView);

            RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);

            LinearLayout.LayoutParams layoutTvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
            tvUnLike.setX(900 - 20 - tvUnLike.getWidth());
            tvUnLike.setY(100);
            tvUnLike.setRotation(20);
            tvUnLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvUnLike);

            scrollBlocker = false;


            relativeLayoutContainer.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    x_cord = (int) event.getRawX();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            lastContainerView = containerView;
                            lasttvLike = tvLike;
                            lasttvUnlike = tvUnLike;
                            x = (int) event.getRawX();
                            y = (int) event.getRawY();


                            Log.v("On touch", String.valueOf(x));
                            break;
                        case MotionEvent.ACTION_MOVE:

                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();
                            if ((Math.abs(x_cord - x)) > Math.abs(y_cord - y)) {
                                scrollView.setScrollingEnabled(false);
                            }
                            // smoother animation.


                            xCard = (x_cord - x);
                            containerView.setX(xCard);


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
                            scrollView.setScrollingEnabled(true);
                            TextView tv = (TextView) containerView.findViewById(R.id.textView);
                            x_cord = (int) event.getRawX();

                            Log.e("X Point", "" + x_cord);
                            tvUnLike.setAlpha(0);
                            tvLike.setAlpha(0);

                            if (Likes == 0) {
                                Toast.makeText(context, "NOTHING", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Nothing");
                                containerView.setX(0);
                            } else if (Likes == 1) {
                                Toast.makeText(context, "NO", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "UNLIKE");
                                //parentView.removeView(containerView);
                                if (tv.getText().equals("Sponsored")) {
                                    containerView.setX(0);
                                } else {
                                    parentView.removeView(containerView);
                                }
                            } else if (Likes == 2) {
                                Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Liked");
                                //parentView.removeView(containerView);

                                //doctorDataModelArrayList.get(i).getSponsored() ? finish() : void;

                                if (tv.getText().equals("Sponsored")) {
                                    finish();
                                } else {
                                    containerView.setX(0);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setCancelable(true);
                                    builder.setTitle("Press Confirm to call this doctor");
                                    builder.setMessage("This call will cost you $0,34 per minute");
                                    builder.setPositiveButton("Confirm",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }

            });


        }


    }


    public View createCard(DoctorDataModel model) {
        LayoutInflater inflate = (LayoutInflater) ResultActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View containerView = inflate.inflate(R.layout.custom_doctor_view, null);
        TextView tvName = (TextView) containerView.findViewById(R.id.tvName);
        ImageView imageView = (ImageView) containerView.findViewById(R.id.imageView);
        TextView tvSponsored = (TextView) containerView.findViewById(R.id.textView);
        TextView tvPrice = (TextView) containerView.findViewById(R.id.textView3);
        TextView tvDiscount = (TextView) containerView.findViewById(R.id.textView4);
        TextView tvDistance = (TextView) containerView.findViewById(R.id.textView5);

        tvName.setText(model.getName());
        tvDiscount.setText(model.getDiscount() == 0 ? "" : ("Schedule an appointement now and get a " + model.getDiscount() + "% discount!"));
        String[] str = getResources().getStringArray(R.array.price);
        tvPrice.setText(str[model.getPrice()]);
        str = getResources().getStringArray(R.array.sponsored);
        tvSponsored.setText(str[model.getSponsored() ? 1 : 0]);
        tvDistance.setText(model.getDistance());
        switch (model.getPhoto()) {
            case "physician0":
                imageView.setImageResource(R.drawable.physician0);
                break;
            case "physician2":
                imageView.setImageResource(R.drawable.physician2);
                break;
            default:
                imageView.setImageResource(R.drawable.physician0);
                break;
        }


        return containerView;

    }


    private void getArrayData() {
        DoctorDataModel model = new DoctorDataModel();
        model.setName("Peter Smith");
        model.setPhoto("physician2");
        model.setDiscount(50);
        model.setPrice(1);
        model.setSponsored(true);
        model.setDistance("5 miles from you");
        this.doctorDataModelArrayList.add(model);


        model = new DoctorDataModel();
        model.setName("Thomas Anderson");
        model.setPhoto("physician0");
        model.setDiscount(0);
        model.setPrice(2);
        model.setSponsored(false);
        model.setDistance("10 miles from you");
        this.doctorDataModelArrayList.add(model);

        model = new DoctorDataModel();
        model.setName("Andrew Foster");
        model.setPhoto("physician0");
        model.setDiscount(0);
        model.setPrice(5);
        model.setSponsored(false);
        model.setDistance("7 miles from you");
        this.doctorDataModelArrayList.add(model);
    }

    public void finish() {
        LayoutInflater inflate = (LayoutInflater) ResultActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View containerViewFinal = inflate.inflate(R.layout.result_card, null);
        parentView.removeAllViews();
        parentView.addView(containerViewFinal);
    }
}
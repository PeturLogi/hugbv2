package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    private Button mLoginButton;

    private static final int REQUEST_CODE_LOGIN = 0;
    private GestureDetectorCompat gestureObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //gesture object class
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {
        //SimpleOnGestureListener is the listener for what we want to do and how we do it

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if(event2.getX() > event1.getX()) {
                //for left to right swipe
                Intent intent = new Intent(
                        SearchActivity.this, LoginActivity.class);
                finish(); //finish is used to stop history for SearchActivity class
                startActivity(intent);
            } else if (event2.getX() < event1.getX()) {
                //for right to left swipe
            }
            return true;
        }
    }
}

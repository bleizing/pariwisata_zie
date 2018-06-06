package bleizing.pariwisata.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import bleizing.pariwisata.PrefManager;
import bleizing.pariwisata.R;

public class SplashScreenActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        PrefManager prefManager = new PrefManager(this);

        if (prefManager.isFirstTimeLaunch()) {
            intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}

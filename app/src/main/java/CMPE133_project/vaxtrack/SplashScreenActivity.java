package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Animation top_animation;
    Animation bottom_animation;

    ImageView logo_image;
    TextView logo_text, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo_image = findViewById(R.id.ivLogo_image);
        logo_text = findViewById(R.id.tvLogo_text);
        slogan = findViewById(R.id.tvSlogan);

        logo_image.setAnimation(top_animation);
        logo_text.setAnimation(bottom_animation);
        slogan.setAnimation(bottom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, ServicesActivity.class);
                startActivity(intent);
            }
        }, 4000);
    }
}
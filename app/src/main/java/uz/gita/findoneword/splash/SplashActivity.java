package uz.gita.findoneword.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import uz.gita.findoneword.ui.MenuActivity;
import uz.gita.findoneword.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
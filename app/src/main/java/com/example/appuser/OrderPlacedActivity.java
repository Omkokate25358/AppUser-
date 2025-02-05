package com.example.appuser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class OrderPlacedActivity extends AppCompatActivity {
    LottieAnimationView orderPlaced;
    Animation fadeInAnim;
    TextView title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_placed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        orderPlaced = findViewById(R.id.orderPlaced);
        title = findViewById(R.id.orderPlacedTitle);

        fadeInAnim = AnimationUtils.loadAnimation(OrderPlacedActivity.this, R.anim.fadein);
        orderPlaced.setAnimation(fadeInAnim);
        title.setAnimation(fadeInAnim);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OrderPlacedActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2100);


    }
}
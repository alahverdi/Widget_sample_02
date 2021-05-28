package com.alroid.appwidgetmfttask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.alroid.appwidgetmfttask.database.TaskDbHelper;
import com.alroid.appwidgetmfttask.entity.Task;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView tv_tittle, tv_details;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        //handle intent and get data from db:
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            String tittle = intent.getStringExtra("tittle");
            Task task = new TaskDbHelper(this).select(tittle);

            tv_tittle.setText(task.getTittle());
            tv_details.setText(task.getDetails());

            Toast.makeText(this, "item clicked : " + task.getId(), Toast.LENGTH_SHORT).show();

        }
    }

    private void initViews() {
        tv_tittle = findViewById(R.id.tv_tittle);
        tv_details = findViewById(R.id.tv_details);
        lottieAnimationView = findViewById(R.id.lottie_details);
    }

    public void onclick_ok(View view) {
        Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
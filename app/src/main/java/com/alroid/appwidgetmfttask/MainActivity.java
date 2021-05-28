package com.alroid.appwidgetmfttask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.alroid.appwidgetmfttask.adapter.ListViewWidgetService;
import com.alroid.appwidgetmfttask.database.TaskDbHelper;
import com.alroid.appwidgetmfttask.entity.Task;
import com.alroid.appwidgetmfttask.widget.MyAppWidget;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_tittle, et_details;
    Button btn_save;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        et_tittle = findViewById(R.id.et_tittle);
        et_details = findViewById(R.id.et_details);
        btn_save = findViewById(R.id.btn_save);
        lottieAnimationView = findViewById(R.id.lottie_main);
    }

    public void onclick_save(View view) {
        //get info from user inputs:
        String tittle = et_tittle.getText().toString();
        String details = et_details.getText().toString();

        //set data in database:
        if (isValidInputs(et_tittle.getText().toString())) {
            Task task = new Task(tittle, details);
            new TaskDbHelper(this).insert(task);

            //update all widgets on homeScreen:
            updateWidget(this);

            //close the activity:
            finish();
        }
    }

    public static void updateWidget(Context context) {
        Intent i = new Intent(context, MyAppWidget.class);
        i.setAction(MyAppWidget.DATABASE_CHANGED);
        context.sendBroadcast(i);
    }

    private boolean isValidInputs(String tittle) {
        if (tittle.isEmpty()) {
            et_tittle.setError("Please Enter a Tittle!");
            et_tittle.requestFocus();
            return false;
        }
        return true;
    }

}
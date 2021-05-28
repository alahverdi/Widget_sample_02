package com.alroid.appwidgetmfttask.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alroid.appwidgetmfttask.Const;
import com.alroid.appwidgetmfttask.DetailsActivity;
import com.alroid.appwidgetmfttask.MainActivity;
import com.alroid.appwidgetmfttask.R;
import com.alroid.appwidgetmfttask.adapter.ListViewWidgetService;
import com.alroid.appwidgetmfttask.entity.Task;

public class MyAppWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.alroid.appwidgetmfttask.widget..TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.alroid.appwidgetmfttask.widget..EXTRA_ITEM";
    public static final String DATABASE_CHANGED = "com.alroid.appwidgetmfttask.widget.DATABASE_CHANGED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        for (int i = 0; i < appWidgetIds.length; ++i) {
            // handle listView data:
            Intent intent = new Intent(context, ListViewWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(intent.URI_INTENT_SCHEME)));
            intent.setAction(DATABASE_CHANGED);

            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.listView_widget, intent);
            remoteViews.setEmptyView(R.id.listView_widget, R.id.empty_view);

            // handle click for add button :
            Intent AddClickIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    AddClickIntent,
                    0);
            remoteViews.setOnClickPendingIntent(R.id.btn_add, pendingIntent);

            // template to handle the click listener for each item:
            Intent onclickItems = new Intent(context, DetailsActivity.class);

            PendingIntent clickItemIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(onclickItems)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.listView_widget, clickItemIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        Log.e(Const.TAG_ADAPTER, "onUpdate: ");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }//end onUpdate()

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(Const.TAG_ADAPTER, "onReceive: ");

        String action = intent.getAction();
        if (action.equals(DATABASE_CHANGED) || action.equals(Intent.ACTION_DATE_CHANGED)) {
            //update:
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyAppWidget.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.listView_widget);

        } else {
            super.onReceive(context, intent);
        }

        ///////////////////
        //Toast listView items in homeScreen: (Just fot TEST):
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
    }
}

package com.alroid.appwidgetmfttask.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.alroid.appwidgetmfttask.Const;
import com.alroid.appwidgetmfttask.R;
import com.alroid.appwidgetmfttask.database.TaskDbHelper;
import com.alroid.appwidgetmfttask.entity.Task;

import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // RemoteViewsFactory class:
    class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private List<Task> tasks;
        TaskDbHelper taskDbHelper;

        public ListViewRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            taskDbHelper = new TaskDbHelper(context);
            tasks = taskDbHelper.select();
            Log.e(Const.TAG_ADAPTER, "widget listView Size : " + String.valueOf(tasks.size()));
        }

        //initialize the data set:
        @Override
        public void onCreate() {
            // in onCreate We can setup any connection...
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_lsitview_items);

            int id = tasks.get(position).getId();
            String tittle = tasks.get(position).getTittle();

            remoteViews.setTextViewText(R.id.tv_id_list, String.valueOf(id));
            remoteViews.setTextViewText(R.id.tv_tittle_list, String.valueOf(tittle));

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("tittle", tittle);
            remoteViews.setOnClickFillInIntent(R.id.tv_tittle_list, fillInIntent);
            remoteViews.setOnClickFillInIntent(R.id.tv_id_list, fillInIntent);

            //return the remote view object
            return remoteViews;
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public void onDataSetChanged() {
            tasks = taskDbHelper.select();
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onDestroy() {
            tasks.clear();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }
    }
}

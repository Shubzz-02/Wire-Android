package com.shubzz.wire;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Implementation of App Widget functionality.
 */
public class PanicWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.panic_widget);
//
//        remoteViews.setOnClickPendingIntent(R.id.SOS_image, pendingIntent);
//
//        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        final Intent i = new Intent();
        i.putExtra("data", "Some data");
        i.setAction("com.shubzz.wireparent");
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.getApplicationContext().sendBroadcast(i);


//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.panic_widget);
//        //views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}


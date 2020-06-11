package com.shubzz.wire;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ClickIntentService extends IntentService {

    public static final String ACTION_CLICK = "com.shubzz.wire.click";

    public ClickIntentService() {
        super("ClickIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, ClickIntentService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, ClickIntentService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Clicked ", "null");
        if (intent != null) {
            final String action = intent.getAction();

            Log.d("Clicked ", action);
            if (ACTION_CLICK.equals(action)) {
                handleClick();
            }
        }
    }

    private void handleClick() {
        final Intent i = new Intent();
        i.putExtra("data", "Some data");
        i.setAction("com.shubzz.wireparent");
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(i);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, PanicWidget.class));
        for (int appWidgetId : widgetIds) {
            PanicWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

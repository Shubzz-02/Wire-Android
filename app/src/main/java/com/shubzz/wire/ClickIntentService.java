package com.shubzz.wire;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ClickIntentService extends IntentService {
    private SessionHandler sessionHandler;
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
//        final Intent i = new Intent();
//        i.putExtra("data", "Some data");
//        i.setAction("com.shubzz.wireparent");
//        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        getApplicationContext().sendBroadcast(i);

        final String KEY_STATUS = "status";
        final String KEY_MESSAGE = "message";
        final String KEY_USERNAME = "username";
        final String KEY_FULL_NAME = "full_name";
        final String KEY_uq = "key";
        final String KEY_sos = "SOSkey";
        String update_location = "http://192.168.43.98/wire/SOSUpdate.php";
        sessionHandler = new SessionHandler(this);
        String[] details = sessionHandler.getDetails();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_FULL_NAME, details[0]);
            request.put(KEY_USERNAME, details[1]);
            request.put(KEY_uq, details[2]);
            request.put(KEY_sos, "1");
            //Log.d("Request", request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, update_location, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt(KEY_STATUS) == 0) {
                        //Toast.makeText(getApplicationContext(),response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(),response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

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

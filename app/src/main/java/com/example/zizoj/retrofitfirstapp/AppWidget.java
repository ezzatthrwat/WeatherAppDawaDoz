package com.example.zizoj.retrofitfirstapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.zizoj.retrofitfirstapp.Network.model.Weathers;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private static Weathers weathers;

    String MY_PREFS_NAME_2 = "prefwidgetapp";

    String cityText ;
    String tempnum;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        weathers = new Weathers();

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME_2, Context.MODE_PRIVATE);
             cityText = prefs.getString("cityname", "Press me");
             tempnum = prefs.getString("temp" , "11");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.setOnClickPendingIntent(R.id.LayoutwidgetID, pendingIntent);
            views.setCharSequence(R.id.appwidget_text, "setText", cityText);
            views.setCharSequence(R.id.TextTemp,"setText",tempnum);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME_2, Context.MODE_PRIVATE);
        cityText = prefs.getString("cityname", "Press me");
         tempnum = prefs.getString("temp" , "11");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME_2, Context.MODE_PRIVATE);
         cityText = prefs.getString("cityname", "Press me");
         tempnum = prefs.getString("temp" , "11");
    }

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        CharSequence widgetText = weathers.getLocation().getName();
//        CharSequence WidgetTextTemp = weathers.getCurrent().getTempC().toString();
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//        views.setTextViewText(R.id.Appwidget_temp ,);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }
}


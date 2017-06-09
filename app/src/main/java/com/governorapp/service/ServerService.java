package com.governorapp.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.governorapp.R;
import com.governorapp.activity.GovernorActivity;
import com.governorapp.config.Configuration;
import com.governorapp.server.Server;
import com.governorapp.util.Utils;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by macoli on 17/6/9.
 */

public class ServerService extends Service {

    public static void startServerService(Context context){
        Intent intent = new Intent(context , ServerService.class) ;
        context.startService(intent) ;
    }

    /**
     * The NanoHttpd server.
     * <p/>
     * The HTTP server hosting the Governor application.
     */
    protected Server server;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(110, buildNotification());
        startHttpServer() ;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }

    private Notification buildNotification(){
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, GovernorActivity.class);

        try {
            builder.setContentIntent(PendingIntent.
                getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("Governor has started")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("http://" + Utils.getDeviceWifiAddress(this) + ":8080") // 设置上下文内容
                .setWhen(System.currentTimeMillis());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;
        return notification ;
    }

    private void startHttpServer(){
        try {
            Configuration config = new Configuration();

            config.loadPreferences(PreferenceManager.getDefaultSharedPreferences(this));

            InputStream configStream = getAssets().open("config.xml");
            Document configXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configStream);
            config.loadXml(configXml);



            try {
                server = new Server(this, config);
                server.start();
            } catch (IOException e) {
                Log.e("com.governorapp", "exception when launching NanoHttpd", e);
            }
        } catch (Exception e) { // multi-catch only came in JRE 7 :(
            Log.e("com.governorapp", "exception when parsing configuration", e);
        }
    }

}

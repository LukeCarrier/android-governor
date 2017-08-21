package com.governorapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.governorapp.R;
import com.governorapp.config.Configuration;
import com.governorapp.server.Server;
import com.governorapp.service.ServerService;
import com.governorapp.util.Constants;
import com.governorapp.util.Utils;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Governor activity.
 * <p/>
 * This is the entry point to the Android application.
 */
public class GovernorActivity extends Activity {
    /**
     * View element containing Governor's URL.
     * <p/>
     * We populate this element with the device's WiFi IP address and the server's port number when
     * the application first starts.
     */
    protected EditText governor_address;

    /**
     * View element containing the device's WiFi status.
     * <p/>
     * This element will be populated with explanatory text when we're trying to determine the
     * device's WiFi state or have experienced issues doing so.
     */
    protected TextView device_wifi_status;

    /**
     * Application appContext.
     * <p/>
     * We have to pass the application context to the server so that it's able to retrieve assets
     * from our *.jar.
     */
    protected Context appContext;



    /**
     * Prepare activity state on creation.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDir() ;

        setContentView(R.layout.activity_governor);

        appContext = getApplicationContext();
        governor_address = (EditText) findViewById(R.id.governor_address);
        device_wifi_status = (TextView) findViewById(R.id.device_wifi_status);

        ServerService.startServerService(this);


    }

    /**
     * init upload diractionary
     * */
    private void initDir(){
        File uploadFile = new File(Constants.UPLOAD_PATH) ;
        if (!uploadFile.exists()){
            uploadFile.mkdirs() ;
        }
    }

    /**
     * Prepare activity state on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();

        try {
            // This will screw on IPv6 (need to enclose IP with [])
            governor_address.setText("http://" + Utils.getDeviceWifiAddress(this) + ":8080");
            device_wifi_status.setVisibility(View.GONE);
        } catch (UnknownHostException e) {
            device_wifi_status.setText(R.string.device_wifi_address_error);
            device_wifi_status.setTextColor(getResources().getColor(R.color.error));
        }
    }

    /**
     * Come on, we're going home.
     */
    @Override
    protected void onPause() {
        super.onPause();


    }

    /**
     * Inflate options menu.
     *
     * @param menu The menu to insert our options into.
     * @return Always true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.governor, menu);
        return true;
    }

    /**
     * Handle action bar click.
     *
     * @param item Selected menu item.
     * @return True if handled, else false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

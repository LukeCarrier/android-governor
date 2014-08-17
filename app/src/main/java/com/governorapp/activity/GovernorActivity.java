package com.governorapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import javax.xml.parsers.DocumentBuilderFactory;

import com.governorapp.R;
import com.governorapp.config.Configuration;
import com.governorapp.server.Server;

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
     * The NanoHttpd server.
     * <p/>
     * The HTTP server hosting the Governor application.
     */
    protected Server server;

    /**
     * Prepare activity state on creation.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_governor);

        appContext = getApplicationContext();
        governor_address = (EditText) findViewById(R.id.governor_address);
        device_wifi_status = (TextView) findViewById(R.id.device_wifi_status);
    }

    /**
     * Prepare activity state on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();

        try {
            InputStream configStream = getAssets().open("config.xml");
            Document configXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configStream);
            Configuration config = Configuration.fromXml(configXml);

            try {
                // This will screw on IPv6 (need to enclose IP with [])
                governor_address.setText("http://" + getDeviceWifiAddress() + ":" + config.getPort());
                device_wifi_status.setVisibility(View.GONE);
            } catch (UnknownHostException e) {
                device_wifi_status.setText(R.string.device_wifi_address_error);
                device_wifi_status.setTextColor(getResources().getColor(R.color.error));
            }

            try {
                server = new Server(appContext, config);
                server.start();
            } catch (IOException e) {
                Log.e("com.governorapp", "exception when launching NanoHttpd", e);
            }
        } catch (Exception e) { // multi-catch only came in JRE 7 :(
            Log.e("com.governorapp", "exception when parsing configuration", e);
        }
    }

    /**
     * Come on, we're going home.
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (server != null) {
            server.stop();
        }
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

    /**
     * Get a human readable representation of the device's WiFi address.
     * <p/>
     * Exists mainly because Java is stupid and requires us to actually care about endianness.
     *
     * @return A human readable IP address.
     * @throws UnknownHostException
     */
    protected String getDeviceWifiAddress() throws UnknownHostException {
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        InetAddress address = InetAddress.getByAddress(ipByteArray);

        return address.getHostAddress();
    }
}

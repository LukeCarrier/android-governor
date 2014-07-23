package im.carrier.luke.governor.activity;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import im.carrier.luke.governor.R;

public class GovernorActivity extends Activity {
    protected EditText device_wifi_address;
    protected TextView device_wifi_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governor);

        device_wifi_address = (EditText) findViewById(R.id.device_wifi_address);
        device_wifi_status  = (TextView) findViewById(R.id.device_wifi_status);

        try {
            device_wifi_address.setText(getDeviceWifiAddress());
            device_wifi_status.setVisibility(View.GONE);
        } catch (UnknownHostException e) {
            device_wifi_status.setText(R.string.device_wifi_address_error);
            device_wifi_status.setTextColor(getResources().getColor(R.color.error));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.governor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    protected String getDeviceWifiAddress() throws UnknownHostException {
        WifiManager wifiMgr  = (WifiManager) getSystemService(WIFI_SERVICE);
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

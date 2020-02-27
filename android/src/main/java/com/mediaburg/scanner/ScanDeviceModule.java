package com.mediaburg.scanner;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import android.device.ScanDevice;

public class ScanDeviceModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private final ReactApplicationContext reactContext;

    ScanDevice sm;
    private final static String SCAN_ACTION = "scan.rcv.message";
    private String barcodeStr;

    private final BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            byte[] aimid = intent.getByteArrayExtra("aimid");
            barcodeStr = new String(barocode, 0, barocodelen);
            sm.stopScan();

            WritableMap params = Arguments.createMap();
            params.putString("data", barcodeStr);
            triggerEvent("scan/received", params);
        }
    };

    public ScanDeviceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
   
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "ScanDevice";
    }

    @Override
    public void initialize() {
        try {
            sm = new ScanDevice();
            sm.setOutScanMode(0);
            sm.openScan();
        } catch (Exception e) {
            Log.e("SCAN_DEVICE", "This is not a compatible scan device");
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        reactContext.registerReceiver(mScanReceiver, filter);
    }

    @Override
    public void onHostPause() {
        sm.closeScan();
        reactContext.unregisterReceiver(mScanReceiver);
    }

    @Override
    public void onHostResume() {
        if (sm != null) {
            sm.openScan();
            IntentFilter filter = new IntentFilter();
            filter.addAction(SCAN_ACTION);
            reactContext.registerReceiver(mScanReceiver, filter);
        }
    }

    @Override
    public void onHostDestroy() {
        if (sm != null) {
            sm.stopScan();
            sm.setScanLaserMode(8);
            sm.closeScan();
        }
    }

    private void triggerEvent(String eventName, WritableMap params) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }
}

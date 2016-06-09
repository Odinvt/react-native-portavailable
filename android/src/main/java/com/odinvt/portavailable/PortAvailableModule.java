package com.odinvt.portavailable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortAvailableModule extends ReactContextBaseJavaModule {
    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 0xFFFF;

    public PortAvailableModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNPortAvailable";
    }

    @ReactMethod
    public void available(int port, Promise promise) {
        boolean available;
        try {
            available = check(port);

            promise.resolve(available);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void range(int min_port, int max_port, int stop, Promise promise) {
        WritableArray ports = new WritableNativeArray();

        try {
            int found = 0;
            for(int i = min_port; i <= max_port; i++) {
                if(stop > 0 && found == stop) {
                    break;
                }
                if(check(i)) {
                    ports.pushInt(i);
                    found++;
                }
            }

            promise.resolve(ports);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    public boolean check(int port) throws IllegalArgumentException, IOException {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        boolean result = false;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            result = true;
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                    throw new IOException(e);
                }
            }
        }

        return result;
    }
}

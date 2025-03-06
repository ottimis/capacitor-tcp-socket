package com.ottimis.capacitor.socket;

import android.Manifest;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@NativePlugin(
        permissions={
                Manifest.permission.ACCESS_NETWORK_STATE
        }
)
public class SocketTCP extends Plugin {

    private Socket socket;
    private DataOutputStream mBufferOut;
    private List<Socket> clients = new ArrayList<Socket>();

    @PluginMethod()
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    @PluginMethod()
    public void connect(PluginCall call) {
        String ipAddress = call.getString("ipAddress");
        JSObject data = call.getData();
        Integer port = data.getInteger("port");

        if (port == null)   {
            call.reject("La porta non può essere vuota");
            return;
        }

        try {
            if (socket != null && socket.isConnected())   {
                socket.close();
            }
            socket = new Socket(ipAddress, port);
            clients.add(socket);
        } catch (IOException e) {
            Log.d("Connection failed", e.getMessage());
            call.reject(e.getMessage());
            return;
        }

        JSObject ret = new JSObject();
        ret.put("success", true);
        ret.put("client", clients.size() - 1);
        call.success(ret);
    }

    @PluginMethod()
    public void send(final PluginCall call) {
        final Integer client = call.getInt("client");
        String msg = call.getString("data");
        String[] dataAr = msg.split(",");
        final byte b[] = new byte[dataAr.length];
        int c;
        for (int i = 0; i < dataAr.length; i++) {
            c = Integer.parseInt(dataAr[i]);
            b[i] = (byte) (c & 0xff);
        }

        Log.d("ArrayByte", b.toString());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final Socket socket = clients.get(client);
                    if (!socket.isConnected()) {
                        socket.close();
                        call.reject("Socket not connected");
                        return;
                    }
                    mBufferOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    if (mBufferOut != null) {
                        mBufferOut.write(b);
                        mBufferOut.flush();
                        // mBufferOut.close();
                    }
                } catch (IOException e) {
                    call.reject(e.getMessage());
                    return;
                }
            }
        };

        Socket socket = clients.get(client);
        if (!socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                call.reject("Generic error");
            }
            call.reject("Socket not connected");
            return;
        }

        Thread thread = new Thread(runnable);
        thread.start();

        JSObject ret = new JSObject();
        ret.put("success", true);
        call.success(ret);
    }

    @PluginMethod()
    public void sendRaw(final PluginCall call) {
        final Integer client = call.getInt("client");
        final String msg = call.getString("data");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final Socket socket = clients.get(client);
                    mBufferOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    if (mBufferOut != null) {
                        mBufferOut.write(msg.getBytes());
                        mBufferOut.flush();
                        // mBufferOut.close();
                    }
                } catch (IOException e) {
                    call.reject(e.getMessage());
                    return;
                }
            }
        };

        Socket socket = clients.get(client);
        if (!socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                call.reject("Generic error");
            }
            call.reject("Socket not connected");
            return;
        }
        Thread thread = new Thread(runnable);
        thread.start();

        JSObject ret = new JSObject();
        ret.put("success", true);
        call.success(ret);
    }

    @PluginMethod()
    public void disconnect(PluginCall call) {
        final Integer client = call.getInt("client");
        if (clients.isEmpty())  {
            call.reject("Socket not connected");
            return;
        }
        final Socket socket = clients.get(client);
        try {
            if (!socket.isConnected()) {
                socket.close();
                call.reject("Socket not connected");
            }
            socket.close();
        } catch (IOException e) {
            call.reject(e.getMessage());
        }

        JSObject ret = new JSObject();
        ret.put("success", true);
        ret.put("client", client);
        call.success(ret);
    }
}

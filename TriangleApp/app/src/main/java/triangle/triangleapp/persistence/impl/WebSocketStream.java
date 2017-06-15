package triangle.triangleapp.persistence.impl;

import android.util.Log;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import triangle.triangleapp.helpers.MediaFileHelper;
import triangle.triangleapp.persistence.StreamAdapter;

/**
 * Created by Kevin Ly on 6/15/2017.
 */

public class WebSocketStream implements StreamAdapter {
    private static final String URL = "ws://145.49.30.86:1234/send";
    private static final String PROTOCOL = "ws";
    private WebSocket mWebSocket;
    private boolean mIsConnected;

    /**
     * makes an async websocket connection
     */
    public WebSocketStream(){
        AsyncHttpClient.getDefaultInstance()
                .websocket(URL, PROTOCOL, new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        mIsConnected = true;
                        mWebSocket = webSocket;
                    }
                });
    }

    /**
     * gets boolean isConnected, default null, can never return false
     * @return connectionstate
     */
    public boolean isConnected(){
        return mIsConnected;
    }

    /**
     * sends the stream using websocket
     * @param fileName name of file
     */
    @Override
    public void sendFile(String fileName) {
        try{
            if (mIsConnected){
                byte[] bytesToSend = MediaFileHelper.getBytesFromFile(fileName);
                mWebSocket.send(bytesToSend);
            }
        }catch (Exception ex){
            Log.e("WebSocket/sendStream", "Error while sending stream.", ex);
        }
    }
}




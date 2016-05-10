package cursevoicelib.wsclient;

import java.util.ArrayList;
import java.util.List;
import cursevoicelib.helpers.GsonHelper;
import cursevoicelib.util.log.Log;
import cursevoicelib.wsclient.beans.Packet;
import cursevoicelib.wsclient.beans.ReceivedMessageBean;


public class Interpretator {
    private final Client mClient;
    private final List<ClientListener> mListeners = new ArrayList<ClientListener>();
    
    public Interpretator(Client client) {
        mClient = client;
    }
    
    public void addListener(ClientListener listener) {
        mListeners.add(listener);
    }
    
    public void removeListener(ClientListener listener) {
        mListeners.remove(listener);
    }
    
    public void interpretate(String fullJson) {
        try {
            Packet packet = GsonHelper.fromJson(fullJson, Packet.class);
            if (packet.TypeID == -635182161) {
                for (ClientListener l : mListeners) {
                    l.onMessage(mClient, GsonHelper.fromJson(fullJson, ReceivedMessageBean.class));
                }
            }
        } catch (Exception e) {
            Log.warn("Interpretate", e);
        }
    }
}


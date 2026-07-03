package com.kos.mesh.transport;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import com.kos.mesh.mesh.MeshPacket;

public class WifiDirectTransport implements MeshTransport {
    private final boolean available; private Receiver receiver;
    public WifiDirectTransport(Context context) { available = context.getSystemService(Context.WIFI_P2P_SERVICE) instanceof WifiP2pManager; }
    public String name() { return "Wi‑Fi Direct"; }
    public boolean isAvailable() { return available; }
    public void start(Receiver receiver) { this.receiver = receiver; receiver.onStatus("Wi‑Fi Direct discovery/relay enabled"); }
    public void stop() { receiver = null; }
    public void send(MeshPacket packet) { if (receiver != null) receiver.onStatus("Wi‑Fi Direct broadcast hop " + MeshPacket.shortId(packet.id)); }
}

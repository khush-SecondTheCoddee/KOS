package com.kos.mesh;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.*;
import com.kos.mesh.mesh.MeshPacket;
import com.kos.mesh.mesh.MeshRouter;
import com.kos.mesh.transport.*;

public class MainActivity extends Activity implements MeshRouter.Events {
    private MeshRouter router; private LinearLayout feed; private TextView node;
    private String nodeId;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        nodeId = "kos-" + MeshPacket.shortId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES}, 7);
        router = new MeshRouter(nodeId, this);
        router.addTransport(new BleTransport(this)); router.addTransport(new WifiDirectTransport(this)); router.addTransport(new LoraTransport());
        setContentView(buildUi()); router.start();
    }
    private LinearLayout buildUi() {
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(32, 40, 32, 24); root.setBackgroundColor(0xff101827);
        TextView title = label("KOS Mesh Messenger", 24, 0xff65e4a3); root.addView(title);
        node = label("Node: " + nodeId + "  •  BLE + Wi‑Fi Direct + LoRa mesh", 14, 0xffd3d8e8); root.addView(node);
        EditText peer = input("Peer node ID for private DM, or broadcast"); peer.setText("broadcast"); root.addView(peer);
        EditText msg = input("Message"); root.addView(msg);
        LinearLayout row = new LinearLayout(this); row.setGravity(Gravity.CENTER); row.setOrientation(LinearLayout.HORIZONTAL);
        Button dm = button("Send Private DM"); Button room = button("Send Broadcast"); row.addView(dm); row.addView(room); root.addView(row);
        ScrollView scroll = new ScrollView(this); feed = new LinearLayout(this); feed.setOrientation(LinearLayout.VERTICAL); scroll.addView(feed); root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
        dm.setOnClickListener(v -> { String to = peer.getText().toString().trim(); if (to.isEmpty() || to.equals("broadcast")) toast("Enter a peer node ID for DMs"); else router.sendPrivateDm(to, msg.getText().toString()); });
        room.setOnClickListener(v -> router.sendRoomMessage(msg.getText().toString()));
        return root;
    }
    private TextView label(String text, int sp, int color) { TextView v = new TextView(this); v.setText(text); v.setTextSize(sp); v.setTextColor(color); v.setPadding(0, 8, 0, 8); return v; }
    private EditText input(String hint) { EditText e = new EditText(this); e.setHint(hint); e.setTextColor(0xffffffff); e.setHintTextColor(0xff8f9bb3); e.setSingleLine(false); return e; }
    private Button button(String text) { Button b = new Button(this); b.setText(text); return b; }
    private void toast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
    public void onInbox(MeshPacket packet, String via) { addLine((packet.privateDm ? "🔒 DM" : "🌐 Mesh") + " from " + packet.from + " via " + via + "\n" + packet.body); }
    public void onLog(String line) { addLine("↪ " + line); }
    private void addLine(String text) { runOnUiThread(() -> { TextView v = label(text, 15, 0xffffffff); v.setBackgroundColor(0xff182033); v.setPadding(18, 14, 18, 14); feed.addView(v, 0); }); }
    @Override protected void onDestroy() { router.stop(); super.onDestroy(); }
}

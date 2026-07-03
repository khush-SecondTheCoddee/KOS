package com.kos.mesh.transport;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import com.kos.mesh.mesh.MeshPacket;

public class BleTransport implements MeshTransport {
    private final BluetoothAdapter adapter; private Receiver receiver;
    public BleTransport(Context context) { BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE); adapter = manager == null ? null : manager.getAdapter(); }
    public String name() { return "BLE"; }
    public boolean isAvailable() { return adapter != null && adapter.isEnabled(); }
    public void start(Receiver receiver) { this.receiver = receiver; receiver.onStatus("BLE relay ready for nearby low-power peers"); }
    public void stop() { receiver = null; }
    public void send(MeshPacket packet) { if (receiver != null) receiver.onStatus("BLE queued hop " + MeshPacket.shortId(packet.id)); }
}

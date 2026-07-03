package com.kos.mesh.transport;

import com.kos.mesh.mesh.MeshPacket;

public class LoraTransport implements MeshTransport {
    private Receiver receiver; private boolean running;
    public String name() { return "LoRa bridge"; }
    public boolean isAvailable() { return true; }
    public void start(Receiver receiver) { this.receiver = receiver; running = true; receiver.onStatus("LoRa bridge in simulation mode; attach USB/BLE radio driver for real packets"); }
    public void stop() { running = false; receiver = null; }
    public void send(MeshPacket packet) { if (running && receiver != null) receiver.onStatus("LoRa long-range hop " + MeshPacket.shortId(packet.id)); }
}

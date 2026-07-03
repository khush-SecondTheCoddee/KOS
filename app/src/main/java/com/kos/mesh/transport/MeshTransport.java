package com.kos.mesh.transport;

import com.kos.mesh.mesh.MeshPacket;

public interface MeshTransport {
    String name();
    boolean isAvailable();
    void start(Receiver receiver);
    void stop();
    void send(MeshPacket packet);
    interface Receiver { void onPacket(MeshPacket packet, MeshTransport via); void onStatus(String status); }
}

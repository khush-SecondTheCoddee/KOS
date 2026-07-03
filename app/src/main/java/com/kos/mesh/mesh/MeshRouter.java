package com.kos.mesh.mesh;

import com.kos.mesh.transport.MeshTransport;
import java.util.*;

public class MeshRouter implements MeshTransport.Receiver {
    private final String nodeId; private final List<MeshTransport> transports = new ArrayList<>(); private final Set<String> seen = new HashSet<>(); private final Events events;
    public interface Events { void onInbox(MeshPacket packet, String via); void onLog(String line); }
    public MeshRouter(String nodeId, Events events) { this.nodeId = nodeId; this.events = events; }
    public void addTransport(MeshTransport transport) { transports.add(transport); }
    public void start() { for (MeshTransport t: transports) if (t.isAvailable()) t.start(this); else events.onLog(t.name() + " unavailable"); }
    public void stop() { for (MeshTransport t: transports) t.stop(); }
    public void sendPrivateDm(String to, String message) { relay(new MeshPacket(nodeId, to, message, 8, true, nodeId), null); }
    public void sendRoomMessage(String message) { relay(new MeshPacket(nodeId, "broadcast", message, 8, false, nodeId), null); }
    public void onPacket(MeshPacket packet, MeshTransport via) { relay(packet, via); }
    public void onStatus(String status) { events.onLog(status); }
    private void relay(MeshPacket packet, MeshTransport inbound) {
        if (!seen.add(packet.id)) return;
        boolean mine = packet.to.equals(nodeId) || packet.to.equals("broadcast") || packet.from.equals(nodeId);
        if (mine) events.onInbox(packet, inbound == null ? "local" : inbound.name());
        if (packet.canHop()) for (MeshTransport t: transports) if (t != inbound && t.isAvailable()) { t.send(packet.forwardedBy(nodeId)); events.onLog("Hopped " + MeshPacket.shortId(packet.id) + " over " + t.name() + " TTL=" + (packet.ttl - 1)); }
    }
}

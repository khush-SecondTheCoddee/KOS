package com.kos.mesh.mesh;

public class MeshPacketTest {
    public static void main(String[] args) {
        MeshPacket packet = new MeshPacket("alice", "bob", "hello mesh", 3, true, "alice");
        MeshPacket decoded = MeshPacket.decode(packet.encode());
        if (!decoded.from.equals("alice") || !decoded.to.equals("bob") || !decoded.body.equals("hello mesh") || !decoded.privateDm || decoded.ttl != 3) {
            throw new AssertionError("packet encode/decode failed");
        }
        MeshPacket hop = decoded.forwardedBy("relay");
        if (hop.ttl != 2 || !"relay".equals(hop.previousHop) || !hop.canHop()) {
            throw new AssertionError("packet forwarding failed");
        }
    }
}

package com.kos.mesh.mesh;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

public class MeshPacket {
    public final String id, from, to, body, previousHop;
    public final long createdAt;
    public final int ttl;
    public final boolean privateDm;

    public MeshPacket(String from, String to, String body, int ttl, boolean privateDm, String previousHop) {
        this.id = UUID.randomUUID().toString(); this.from = from; this.to = to; this.body = body;
        this.ttl = ttl; this.privateDm = privateDm; this.previousHop = previousHop; this.createdAt = System.currentTimeMillis();
    }
    private MeshPacket(String id, String from, String to, String body, int ttl, boolean privateDm, String previousHop, long createdAt) {
        this.id = id; this.from = from; this.to = to; this.body = body; this.ttl = ttl; this.privateDm = privateDm; this.previousHop = previousHop; this.createdAt = createdAt;
    }
    public MeshPacket forwardedBy(String nodeId) { return new MeshPacket(id, from, to, body, ttl - 1, privateDm, nodeId, createdAt); }
    public boolean canHop() { return ttl > 1; }
    public String preview() { return privateDm ? "Private DM " + shortId(id) + " → " + to : body; }
    public String encode() { return String.join("|", id, from, to, Base64.getEncoder().encodeToString(body.getBytes(StandardCharsets.UTF_8)), String.valueOf(ttl), String.valueOf(privateDm), previousHop == null ? "" : previousHop, String.valueOf(createdAt)); }
    public static MeshPacket decode(String wire) {
        String[] p = wire.split("\\|", -1);
        return new MeshPacket(p[0], p[1], p[2], new String(Base64.getDecoder().decode(p[3]), StandardCharsets.UTF_8), Integer.parseInt(p[4]), Boolean.parseBoolean(p[5]), p[6].isEmpty() ? null : p[6], Long.parseLong(p[7]));
    }
    public static String shortId(String value) { try { byte[] hash = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)); return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8); } catch (Exception e) { return value.substring(0, Math.min(8, value.length())); } }
}

# KOS Mesh

KOS Mesh is a native Android prototype for resilient off-grid messaging. It combines multiple transports into one store-and-forward mesh router:

- **BLE** for low-power nearby hops.
- **Wi‑Fi Direct** for higher-throughput peer clusters.
- **LoRa bridge** abstraction for long-range radios, currently shipped as a simulation-ready adapter until a board-specific driver is connected.
- **Mesh message hopping** with duplicate suppression and TTL-based forwarding.
- **Private DMs** addressed to a peer node ID plus broadcast room messaging.

## Build

```bash
make build
```

The default build compiles the app logic and Android-facing Java sources on the JVM using lightweight Android API stubs in `app/src/androidStub/java`. This keeps CI/build validation working even where the Android SDK or Google Maven plugin repository is unavailable. To produce an installable APK, swap the app module back to the Android Gradle Plugin in an Android SDK environment.

## Notes

This app uses platform APIs directly in Java to keep the prototype compact. Real LoRa operation requires integrating the target radio module transport (USB serial, BLE UART, or vendor SDK) behind `MeshTransport`.

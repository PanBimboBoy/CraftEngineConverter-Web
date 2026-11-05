/**
 * Interface for deferred loading/unloading of packet handler modules.
 * Used to register, enable, or disable protocol logic as the plugin lifecycle changes.
 */
package fr.robie.craftengineconverter.api.packet;

public interface PacketLoader {
    void onLoad();
    void onEnable();
    void onDisable();
}

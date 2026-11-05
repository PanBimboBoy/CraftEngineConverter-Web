package fr.robie.craftengineconverter.common.utils.enums.ia;

import java.util.Collection;

public enum IAModelsKeys {
    BOW("normal", "pulling_0", "pulling_1", "pulling_2"),
    FISHING_ROD("normal", "cast"),
    CROSSBOW("normal", "pulling_0", "pulling_1", "pulling_2", "rocket", "arrow"),
    TRIDENT("normal", "throwing"),
    SHIELD("normal", "blocking");
    private final String[] keys;

    IAModelsKeys(String... keys) {
        this.keys = keys;
    }

    public String[] getKeys() {
        return this.keys;
    }

    public boolean containsAny(Collection<String> keysToCheck) {
        for (String keyToCheck : keysToCheck) {
            for (String key : this.keys) {
                if (key.equalsIgnoreCase(keyToCheck)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getKeysCount() {
        return this.keys.length;
    }
}

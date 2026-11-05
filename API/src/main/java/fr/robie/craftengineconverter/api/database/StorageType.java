/**
 * Enumeration of supported persistent storage backends for block/conversion data.
 * NONE disables persistent storage (pure in-memory mode).
 */
package fr.robie.craftengineconverter.api.database;

public enum StorageType {
    NONE,
    SQLITE,
    MYSQL,
    MARIADB
}

package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class SimpleStorageBlockBehavior implements BlockBehavior {
    private String title;
    private Integer rows;
    private String openSound;
    private String closeSound;
    private Boolean hasSignal;
    private Boolean allowInput;
    private Boolean allowOutput;

    public SimpleStorageBlockBehavior() {
    }

    public SimpleStorageBlockBehavior setTitle(String title) {
        this.title = title;
        return this;
    }

    public SimpleStorageBlockBehavior setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public SimpleStorageBlockBehavior setOpenSound(String openSound) {
        this.openSound = openSound;
        return this;
    }

    public SimpleStorageBlockBehavior setCloseSound(String closeSound) {
        this.closeSound = closeSound;
        return this;
    }

    public SimpleStorageBlockBehavior setHasSignal(boolean hasSignal) {
        this.hasSignal = hasSignal;
        return this;
    }

    public SimpleStorageBlockBehavior setAllowInput(boolean allowInput) {
        this.allowInput = allowInput;
        return this;
    }

    public SimpleStorageBlockBehavior setAllowOutput(boolean allowOutput) {
        this.allowOutput = allowOutput;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "simple_storage_block");
        if (this.title != null) {
            data.put("title", this.title);
        }
        if (this.rows != null) {
            data.put("rows", this.rows);
        }
        if (this.openSound != null || this.closeSound != null) {
            Map<String, Object> sounds = new HashMap<>();
            if (this.openSound != null) {
                sounds.put("open", this.openSound);
            }
            if (this.closeSound != null) {
                sounds.put("close", this.closeSound);
            }
            data.put("sounds", sounds);
        }
        if (this.hasSignal != null) {
            data.put("has-signal", this.hasSignal);
        }
        if (this.allowInput != null) {
            data.put("allow-input", this.allowInput);
        }
        if (this.allowOutput != null) {
            data.put("allow-output", this.allowOutput);
        }
        return data;
    }
}

package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class ItemFrameBlockBehavior implements BlockBehavior {
    private String position;
    private Boolean glow;
    private Boolean invisible;
    private Boolean renderMapItem;
    private String putSound;
    private String takeSound;
    private String rotateSound;

    public ItemFrameBlockBehavior() {
    }

    public ItemFrameBlockBehavior setPosition(String position) {
        this.position = position;
        return this;
    }

    public ItemFrameBlockBehavior setGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemFrameBlockBehavior setInvisible(boolean invisible) {
        this.invisible = invisible;
        return this;
    }

    public ItemFrameBlockBehavior setRenderMapItem(boolean renderMapItem) {
        this.renderMapItem = renderMapItem;
        return this;
    }

    public ItemFrameBlockBehavior setPutSound(String putSound) {
        this.putSound = putSound;
        return this;
    }

    public ItemFrameBlockBehavior setTakeSound(String takeSound) {
        this.takeSound = takeSound;
        return this;
    }

    public ItemFrameBlockBehavior setRotateSound(String rotateSound) {
        this.rotateSound = rotateSound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "item_frame_block");
        if (this.position != null) {
            data.put("position", this.position);
        }
        if (this.glow != null) {
            data.put("glow", this.glow);
        }
        if (this.invisible != null) {
            data.put("invisible", this.invisible);
        }
        if (this.renderMapItem != null) {
            data.put("render-map-item", this.renderMapItem);
        }
        if (this.putSound != null || this.takeSound != null || this.rotateSound != null) {
            Map<String, Object> sounds = new HashMap<>();
            if (this.putSound != null) {
                sounds.put("put", this.putSound);
            }
            if (this.takeSound != null) {
                sounds.put("take", this.takeSound);
            }
            if (this.rotateSound != null) {
                sounds.put("rotate", this.rotateSound);
            }
            data.put("sounds", sounds);
        }
        return data;
    }
}

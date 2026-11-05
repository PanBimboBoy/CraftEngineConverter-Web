package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class TrapdoorBlockBehavior implements BlockBehavior {
    private Boolean canOpenWithHand;
    private Boolean canOpenByWindCharge;
    private String openSound;
    private String closeSound;

    public TrapdoorBlockBehavior() {
    }

    public TrapdoorBlockBehavior setCanOpenWithHand(boolean canOpenWithHand) {
        this.canOpenWithHand = canOpenWithHand;
        return this;
    }

    public TrapdoorBlockBehavior setCanOpenByWindCharge(boolean canOpenByWindCharge) {
        this.canOpenByWindCharge = canOpenByWindCharge;
        return this;
    }

    public TrapdoorBlockBehavior setOpenSound(String openSound) {
        this.openSound = openSound;
        return this;
    }

    public TrapdoorBlockBehavior setCloseSound(String closeSound) {
        this.closeSound = closeSound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "trapdoor_block");
        if (this.canOpenWithHand != null) {
            data.put("can-open-with-hand", this.canOpenWithHand);
        }
        if (this.canOpenByWindCharge != null) {
            data.put("can-open-by-wind-charge", this.canOpenByWindCharge);
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
        return data;
    }
}

package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture;

import fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.element.Element;
import fr.robie.craftengineconverter.api.utils.FloatsUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class ItemElement implements Element {
    protected final String item;
    protected boolean applyDyedColor = true;
    protected final FloatsUtils position = new FloatsUtils(3, new float[]{0, 0, 0});

    protected ItemElement(@NotNull String item) { this.item = item; }

    public void setApplyDyedColor(boolean applyDyedColor) { this.applyDyedColor = applyDyedColor; }
    public void setPosition(float x, float y, float z) {
        this.position.setValue(0, x);
        this.position.setValue(1, y);
        this.position.setValue(2, z);
    }

    protected void serialize(Map<String, Object> data) {
        data.put("item", this.item);
        if (!this.applyDyedColor)
            data.put("apply-dyed-color", false);
        if (this.position.isUpdated())
            data.put("position", this.position.toString());
    }
}

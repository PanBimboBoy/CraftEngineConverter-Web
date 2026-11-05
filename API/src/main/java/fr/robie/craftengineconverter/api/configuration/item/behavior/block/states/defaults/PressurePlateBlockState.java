package fr.robie.craftengineconverter.api.configuration.item.behavior.block.states.defaults;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.states.BlockAppearance;
import fr.robie.craftengineconverter.api.configuration.item.behavior.block.states.BlockVariant;
import fr.robie.craftengineconverter.api.configuration.item.behavior.block.states.MultiStateBlock;
import fr.robie.craftengineconverter.api.configuration.item.behavior.block.states.properties.BooleanBlockStateProperty;
import fr.robie.craftengineconverter.api.configuration.item.models.ModelConfiguration;
import org.jetbrains.annotations.NotNull;

public class PressurePlateBlockState extends MultiStateBlock {
    public PressurePlateBlockState(@NotNull String normalState, @NotNull String poweredState, @NotNull ModelConfiguration modelNormal, @NotNull ModelConfiguration modelPowered) {
        BooleanBlockStateProperty poweredProperty = new BooleanBlockStateProperty("powered", false);
        this.addProperty(poweredProperty);

        this.addAppearance("normal", BlockAppearance.visualState(normalState, modelNormal).build());
        this.addAppearance("powered", BlockAppearance.visualState(poweredState, modelPowered).build());

        BlockVariant normalVariant = new BlockVariant("normal");
        normalVariant.addVariantCondition(poweredProperty, false);
        this.addVariant(normalVariant);

        BlockVariant poweredVariant = new BlockVariant("powered");
        poweredVariant.addVariantCondition(poweredProperty, true);
        this.addVariant(poweredVariant);
    }
}

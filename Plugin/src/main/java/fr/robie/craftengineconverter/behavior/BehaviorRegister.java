package fr.robie.craftengineconverter.behavior;

import net.momirealms.craftengine.core.item.behavior.ItemBehaviors;
import net.momirealms.craftengine.core.util.Key;


public class BehaviorRegister extends ItemBehaviors {
    public static final Key ENERGY_BLAST = Key.from("craftengineconverter:energy_blast");

    public static void init() {
        register(ENERGY_BLAST, EnergyBlastItemBehavior.FACTORY);
    }
}

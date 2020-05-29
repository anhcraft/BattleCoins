package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.battle.utils.ConfigurableObject;
import dev.anhcraft.confighelper.annotation.IgnoreValue;
import dev.anhcraft.confighelper.annotation.Key;
import dev.anhcraft.confighelper.annotation.PrettyEnum;
import dev.anhcraft.confighelper.annotation.Schema;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal")
@Schema
public class Coins extends ConfigurableObject {
    @Key("factor")
    @IgnoreValue(ifNull = true)
    @PrettyEnum
    private Factor factor = Factor.NONE;

    @Key("multiplier")
    private double multiplier;

    @NotNull
    public Factor getFactor() {
        return factor;
    }

    public double getMultiplier() {
        return multiplier;
    }
}

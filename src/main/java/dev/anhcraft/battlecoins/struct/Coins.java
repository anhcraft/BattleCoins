package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.config.annotations.Configurable;
import dev.anhcraft.config.annotations.Setting;
import dev.anhcraft.config.annotations.Validation;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal")
@Configurable
public class Coins {
    @Setting
    @Validation(notNull = true, silent = true)
    private Factor factor = Factor.NONE;

    @Setting
    private double multiplier;

    @NotNull
    public Factor getFactor() {
        return factor;
    }

    public double getMultiplier() {
        return multiplier;
    }
}

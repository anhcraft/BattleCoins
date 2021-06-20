package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.config.annotations.Configurable;
import dev.anhcraft.config.annotations.Path;
import dev.anhcraft.config.annotations.Setting;
import dev.anhcraft.config.annotations.Validation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
@Configurable
public class Action {
    public enum Type {
        KILL_ENTITY,
        BREAK_BLOCK,
        PLACE_BLOCK
    }

    @Setting
    @Path("action")
    @Validation(notNull = true)
    private Type actionType;

    @Setting
    private List<String> filter;

    @Setting
    @Validation(notNull = true, silent = true)
    private Map<String, Coins> coins = new HashMap<>();

    @NotNull
    public Type getActionType() {
        return actionType;
    }

    @Nullable
    public List<String> getFilter() {
        return filter;
    }

    @NotNull
    public Collection<Coins> getCoins() {
        return coins.values();
    }
}

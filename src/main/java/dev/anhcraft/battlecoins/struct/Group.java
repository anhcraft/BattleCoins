package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.config.annotations.Configurable;
import dev.anhcraft.config.annotations.Setting;
import dev.anhcraft.config.annotations.Validation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("FieldMayBeFinal")
@Configurable
public class Group {
    private String id;

    @Setting
    @Validation(notNull = true, silent = true)
    private List<String> arenas = new ArrayList<>();

    @Setting
    @Validation(notNull = true, silent = true)
    private Map<String, Action> actions = new HashMap<>();

    public Group(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public List<String> getArenas() {
        return arenas;
    }

    @NotNull
    public Collection<Action> getActions() {
        return actions.values();
    }
}

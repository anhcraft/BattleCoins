package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.battle.utils.ConfigurableObject;
import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.ConfigSchema;
import dev.anhcraft.confighelper.annotation.IgnoreValue;
import dev.anhcraft.confighelper.annotation.Key;
import dev.anhcraft.confighelper.annotation.Schema;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("FieldMayBeFinal")
@Schema
public class Group extends ConfigurableObject {
    private String id;

    @Key("arenas")
    @IgnoreValue(ifNull = true)
    private List<String> arenas = new ArrayList<>();

    @Key("actions")
    @IgnoreValue(ifNull = true)
    private List<Action> actions = new ArrayList<>();

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
    public List<Action> getActions() {
        return actions;
    }

    @Override
    protected @Nullable Object conf2schema(@Nullable Object value, ConfigSchema.Entry entry) {
        if(value != null && entry.getKey().equals("actions")){
            ConfigurationSection cs = (ConfigurationSection) value;
            List<Action> actions = new ArrayList<>();
            Set<String> keys = cs.getKeys(false);
            for(String s : keys){
                try {
                    actions.add(ConfigHelper.readConfig(Objects.requireNonNull(cs.getConfigurationSection(s)), ConfigSchema.of(Action.class)));
                } catch (InvalidValueException e) {
                    e.printStackTrace();
                }
            }
            return actions;
        }
        return value;
    }

    @Override
    protected @Nullable Object schema2conf(@Nullable Object value, ConfigSchema.Entry entry) {
        if(value != null && entry.getKey().equals("actions")){
            ConfigurationSection parent = new YamlConfiguration();
            int i = 0;
            for(Action action : (List<Action>) value){
                YamlConfiguration c = new YamlConfiguration();
                ConfigHelper.writeConfig(c, ConfigSchema.of(Action.class), action);
                parent.set(String.valueOf(i++), c);
            }
            return parent;
        }
        return value;
    }
}

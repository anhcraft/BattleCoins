package dev.anhcraft.battlecoins.struct;

import dev.anhcraft.battle.api.inventory.item.Ammo;
import dev.anhcraft.battle.utils.ConfigurableObject;
import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.ConfigSchema;
import dev.anhcraft.confighelper.annotation.*;
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
public class Action extends ConfigurableObject {
    public enum Type {
        KILL_ENTITY,
        BREAK_BLOCK,
        PLACE_BLOCK
    }

    @Key("action")
    @Validation(notNull = true)
    @PrettyEnum
    private Type actionType;

    @Key("filter")
    private List<String> filter;

    @Key("coins")
    @IgnoreValue(ifNull = true)
    private List<Coins> coins = new ArrayList<>();

    @NotNull
    public Type getActionType() {
        return actionType;
    }

    @Nullable
    public List<String> getFilter() {
        return filter;
    }

    @NotNull
    public List<Coins> getCoins() {
        return coins;
    }

    @Override
    protected @Nullable Object conf2schema(@Nullable Object value, ConfigSchema.Entry entry) {
        if(value != null && entry.getKey().equals("coins")){
            ConfigurationSection cs = (ConfigurationSection) value;
            List<Coins> coins = new ArrayList<>();
            Set<String> keys = cs.getKeys(false);
            for(String s : keys){
                try {
                    coins.add(ConfigHelper.readConfig(Objects.requireNonNull(cs.getConfigurationSection(s)), ConfigSchema.of(Coins.class)));
                } catch (InvalidValueException e) {
                    e.printStackTrace();
                }
            }
            return coins;
        }
        return value;
    }

    @Override
    protected @Nullable Object schema2conf(@Nullable Object value, ConfigSchema.Entry entry) {
        if(value != null && entry.getKey().equals("coins")){
            ConfigurationSection parent = new YamlConfiguration();
            int i = 0;
            for(Coins coins : (List<Coins>) value){
                YamlConfiguration c = new YamlConfiguration();
                ConfigHelper.writeConfig(c, ConfigSchema.of(Coins.class), coins);
                parent.set(String.valueOf(i++), c);
            }
            return parent;
        }
        return value;
    }
}

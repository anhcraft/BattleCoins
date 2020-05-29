package dev.anhcraft.battlecoins;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.anhcraft.battlecoins.struct.Group;
import dev.anhcraft.battlecoins.system.EventListener;
import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.ConfigSchema;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BattleCoins extends JavaPlugin {
    public final Multimap<String, Group> arena2group = HashMultimap.create();

    public void reloadConf(){
        arena2group.clear();
        reloadConfig();
        try {
            for (String s : getConfig().getKeys(false)) {
                ConfigurationSection conf = Objects.requireNonNull(getConfig().getConfigurationSection(s));
                Group group = ConfigHelper.readConfig(conf, ConfigSchema.of(Group.class), new Group(s));
                for (String arena : group.getArenas()) {
                    arena2group.put(arena, group);
                }
            }
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConf();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        Objects.requireNonNull(getCommand("bcoins")).setExecutor((sender, command, label, args) -> {
            if(sender.hasPermission("bcoins.reload")) {
                reloadConf();
                sender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
            }
            return false;
        });
    }
}

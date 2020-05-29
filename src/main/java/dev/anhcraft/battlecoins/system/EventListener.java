package dev.anhcraft.battlecoins.system;

import dev.anhcraft.battle.api.BattleApi;
import dev.anhcraft.battle.api.arena.game.LocalGame;
import dev.anhcraft.battlecoins.BattleCoins;
import dev.anhcraft.battlecoins.struct.Action;
import dev.anhcraft.battlecoins.struct.Factor;
import dev.anhcraft.battlecoins.struct.Group;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EventListener implements Listener {
    private final BattleCoins plugin;

    public EventListener(@NotNull BattleCoins plugin) {
        this.plugin = plugin;
    }

    private void handle(@NotNull LocalGame game, @NotNull Player player, @NotNull Action.Type actionType, @Nullable String enumName, @NotNull CoinCalculator coinCalculator){
        double sum = 0;
        for (Group group : plugin.arena2group.get(game.getArena().getId())) {
            sum += coinCalculator.calculate(group, enumName, actionType);
        }
        Objects.requireNonNull(game.getPlayer(player)).getIgBalance().addAndGet(sum);
    }

    @EventHandler
    public void killEntity(EntityDeathEvent event) {
        LivingEntity ent = event.getEntity();
        Player killer = ent.getKiller();
        if(killer == null) return;
        LocalGame game = BattleApi.getInstance().getArenaManager().getGame(killer);
        if(game == null) return;
        handle(game, killer, Action.Type.KILL_ENTITY, ent.getType().name(), new CoinCalculator()
                .setFactor(Factor.DAMAGE, ent.getLastDamage())
                .setFactor(Factor.MAX_HEALTH, Objects.requireNonNull(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue())
                .setFactor(Factor.HARDNESS, BattleApi.getInstance().getGeneralConfig().getEntityHardness(ent.getType()))
        );
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        LocalGame game = BattleApi.getInstance().getArenaManager().getGame(player);
        if(game == null) return;
        Material mt = event.getBlock().getType();
        handle(game, player, Action.Type.BREAK_BLOCK, mt.name(), new CoinCalculator()
                .setFactor(Factor.HARDNESS, BattleApi.getInstance().getGeneralConfig().getMaterialHardness(mt))
        );
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        LocalGame game = BattleApi.getInstance().getArenaManager().getGame(player);
        if(game == null) return;
        Material mt = event.getBlock().getType();
        handle(game, player, Action.Type.PLACE_BLOCK, mt.name(), new CoinCalculator()
                .setFactor(Factor.HARDNESS, BattleApi.getInstance().getGeneralConfig().getMaterialHardness(mt))
        );
    }
}

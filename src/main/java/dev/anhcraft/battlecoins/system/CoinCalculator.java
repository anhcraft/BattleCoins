package dev.anhcraft.battlecoins.system;

import dev.anhcraft.battlecoins.struct.Action;
import dev.anhcraft.battlecoins.struct.Coins;
import dev.anhcraft.battlecoins.struct.Factor;
import dev.anhcraft.battlecoins.struct.Group;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class CoinCalculator {
    private final Map<Factor, Double> factors = new EnumMap<>(Factor.class);

    public CoinCalculator(){
        factors.put(Factor.NONE, 1d);
    }

    public CoinCalculator setFactor(@NotNull Factor factor, double value){
        factors.put(factor, value);
        return this;
    }

    public double calculate(@NotNull Action action){
        double sum = 0;
        for (Coins coins : action.getCoins()){
            sum += factors.getOrDefault(coins.getFactor(), 1d) * coins.getMultiplier();
        }
        return sum;
    }

    public double calculate(@NotNull Group group, @Nullable String enumName, @NotNull Action.Type actionType){
        return group.getActions().stream()
                .filter(a -> a.getActionType() == actionType)
                .filter(action -> enumName == null || action.getFilter() == null || action.getFilter().stream().anyMatch(s -> s.equalsIgnoreCase(enumName)))
                .mapToDouble(this::calculate).sum();
    }
}

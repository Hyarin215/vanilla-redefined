package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

public class ModGamerules {
    private static final Logger LOGGER = HopefulMod.LOGGER;

    public static void register() {

    }
    public static GameRules.Key<GameRules.BooleanValue> createBoolean(String name, GameRules.Category category, boolean defaultValue){
        return GameRules.register(name, category, GameRules.BooleanValue.create(defaultValue,
                        (minecraftServer, booleanValue)
                                -> LOGGER.info("set value to {}", booleanValue.get())));
    }
    public static final GameRules.Key<GameRules.BooleanValue> SAPLINGS_REPLACE =
    createBoolean("doSaplingsRegrow",GameRules.Category.MISC,false);

    public static final GameRules.Key<GameRules.BooleanValue> FIREWORK_BOOSTING =
    createBoolean("fireworkBoosting",GameRules.Category.PLAYER, false);

    public static final GameRules.Key<GameRules.BooleanValue> LEAVES_FALL =
    createBoolean("doLeavesFall", GameRules.Category.MISC, false);

    public static final GameRules.Key<GameRules.BooleanValue> KEEP_EXP =
    createBoolean("keepExperience", GameRules.Category.PLAYER, true);

}

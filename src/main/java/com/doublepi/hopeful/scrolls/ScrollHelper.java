package com.doublepi.hopeful.scrolls;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.registries.ModResourceRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class ScrollHelper {

    public static void enchant(ItemStack item, Scroll scroll){
        for(Holder<Enchantment> enchantment : scroll.enchantments()){
            boolean itemSupportsEnchantment = item.supportsEnchantment(enchantment);
            boolean isNotMaxLevel = item.getEnchantmentLevel(enchantment)< scroll.maxLevel();
            if(itemSupportsEnchantment && isNotMaxLevel && getScore(item) < enchantabilityToScore(item.getEnchantmentValue())){
                int newLevel = item.getEnchantmentLevel(enchantment) + 1;
                HopefulMod.LOGGER.error("Item enchanted to lvl "+ newLevel+ " score was changed from "+getScore(item)+
                        " to "+ (getScore(item) + scroll.scorePerLevel()));
                item.enchant(enchantment, newLevel);
            }
        }
        setScore(item, getScore(item) + scroll.scorePerLevel());
    }


    public static boolean supportsScroll(ItemStack item, Scroll scroll){
        int maxScore = getMaxScore(item);
        int currentScore = getScore(item);
        if(maxScore == 0)
            return false;

        for(Holder<Enchantment> enchantment : scroll.enchantments()){
            boolean enchantmentSupported = item.supportsEnchantment(enchantment);
            if(!enchantmentSupported)
                continue;

            if(item.getEnchantmentLevel(enchantment) >= scroll.maxLevel())
                continue;

            if(currentScore + scroll.scorePerLevel() <= maxScore)
                return true;

        }
        return false;
    }

    public static int enchantabilityToScore(int enchantability){
        if(enchantability==1)
            return 5;
        return enchantability;
    }

    public static int getMaxScore(ItemStack stack){
        int enchantability = stack.getEnchantmentValue();
        return enchantabilityToScore(enchantability);
    }
    public static int getScore(ItemStack stack){
        if(!stack.has(ModDataComponentTypes.ENCHANTABILITY_STATUS)){
            stack.set(ModDataComponentTypes.ENCHANTABILITY_STATUS,0);
        }
        return stack.get(ModDataComponentTypes.ENCHANTABILITY_STATUS);
    }

    public static void setScore(ItemStack stack, int value){
        stack.set(ModDataComponentTypes.ENCHANTABILITY_STATUS,value);
    }


    public static Scroll randomScroll(ServerLevel level){
        if(!level.registryAccess().lookup(ModResourceRegistries.SCROLL_REGISTRY_KEY).isPresent())
            HopefulMod.LOGGER.error("No registry??");
        List<Holder.Reference<Scroll>> list =
                level.registryAccess().lookup(ModResourceRegistries.SCROLL_REGISTRY_KEY).get().listElements().toList();
        int len = list.size();
        int randomIndex = (int)(Math.random() * len);
        return list.get(randomIndex).value();
    }
}

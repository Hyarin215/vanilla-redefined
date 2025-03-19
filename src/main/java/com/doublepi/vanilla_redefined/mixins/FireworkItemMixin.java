package com.doublepi.vanilla_redefined.mixins;

import com.doublepi.vanilla_redefined.VanillaRedefinedMod;
import com.doublepi.vanilla_redefined.registries.ModGamerules;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkItemMixin {
//    @Inject(method = "useOn", at = @At("HEAD"))
//    public void useOnTest(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir){
//        VanillaRedefinedMod.LOGGER.error("help");
//        context.getPlayer().sendSystemMessage(Component.literal("help"));
//    }
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void useModified(Level level, Player player,
                            InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        if(level.isClientSide())
            return;
        boolean isAllowed = level.getGameRules().getBoolean(ModGamerules.FIREWORK_BOOSTING);
        player.displayClientMessage(Component.translatable("tooltip.vanilla_redefined.disabled_elytra_boosting"), true);
        if(!isAllowed) cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(hand)));
    }
}

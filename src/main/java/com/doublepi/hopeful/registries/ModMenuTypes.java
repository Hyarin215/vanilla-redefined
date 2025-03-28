package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.blocks.ForgeMenu;
import com.doublepi.hopeful.blocks.ForgeScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, HopefulMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ForgeMenu>> FORGE_MENU =
            registerMenuType("forge_menu", ForgeMenu::new);

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.FORGE_MENU.get(), ForgeScreen::new);
    }


    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>>
    registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

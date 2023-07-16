package com.ninni.etcetera.events;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if (name.equals(BuiltInLootTables.BASTION_OTHER)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.BASTION_TREASURE)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.PILLAGER_OUTPOST)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.COTTON_SEEDS.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))).build());
        }
    }

    @SubscribeEvent
    public void onTagUpdated(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(EtceteraItems.EGGPLE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.x(), position.y(), position.z()), entity -> entity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(EtceteraItems.GOLDEN_EGGPLE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.x(), position.y(), position.z()), entity -> entity.setItem(stack));
            }
        });
    }

    @SubscribeEvent
    public void onResourceLoad(AddReloadListenerEvent event) {
        event.addListener(EtceteraVanillaIntegration.CHISELLING_MANAGER);
        event.addListener(EtceteraVanillaIntegration.HAMMERING_MANAGER);
    }

}

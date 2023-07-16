package com.ninni.etcetera.events;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EtceteraEntityType.CHAPPLE.get(), Chicken.createAttributes().build());
    }

    @SubscribeEvent
    public void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(EtceteraEntityType.CHAPPLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SubscribeEvent
    public void onWanderingTraderInit(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        genericTrades.add(1, new BasicItemListing(new ItemStack(Items.EMERALD, 1), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_SEEDS.get()), 1, 12, 0.05f));
        genericTrades.add(1, new BasicItemListing(new ItemStack(Items.EMERALD, 26), ItemStack.EMPTY, new ItemStack(EtceteraItems.TRADER_ROBE.get()), 1, 12, 0.05f));
        genericTrades.add(1, new BasicItemListing(new ItemStack(Items.EMERALD, 20), ItemStack.EMPTY, new ItemStack(EtceteraItems.TRADER_HOOD.get()), 1, 10, 0.05f));
    }

    @SubscribeEvent
    public void onVillagerTraderInit(VillagerTradesEvent event) {
        VillagerProfession type = event.getType();
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        if (type == VillagerProfession.ARMORER) {
            trades.put(2, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2F)));
        }
        if (type == VillagerProfession.WEAPONSMITH) {
            trades.put(2, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2f)));
        }
        if (type == VillagerProfession.TOOLSMITH) {
            trades.put(2, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2f)));
            trades.put(2, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 16), ItemStack.EMPTY, new ItemStack(EtceteraItems.HAMMER.get()), 6, 2, 0.2f)));
        }
        if (type == VillagerProfession.FARMER) {
            trades.put(1, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 2), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_FLOWER.get()), 18, 2, 0.05f)));
            trades.put(1, List.of(new BasicItemListing(new ItemStack(Items.EMERALD, 2), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_SEEDS.get()), 28, 2, 0.05f)));
        }
    }

}

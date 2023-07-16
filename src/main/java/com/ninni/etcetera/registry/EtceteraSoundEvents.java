package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.enums.DrumType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Etcetera.MOD_ID);

    public static final RegistryObject<SoundEvent> BLOCK_DICE_ROLL = register("block.dice.roll");
    public static final RegistryObject<SoundEvent> ITEM_WRENCH_SELECT = register("item.wrench.select");
    public static final RegistryObject<SoundEvent> ITEM_WRENCH_MODIFY = register("item.wrench.modify");
    public static final RegistryObject<SoundEvent> ITEM_WRENCH_FAIL = register("item.wrench.fail");
    public static final RegistryObject<SoundEvent> ITEM_HAMMER_USE = register("item.hammer.use");
    public static final RegistryObject<SoundEvent> ITEM_CHISEL_USE = register("item.chisel.use");
    public static final RegistryObject<SoundEvent> ITEM_HANDBELL_RING = register("item.handbell.ring");
    public static final RegistryObject<SoundEvent> ITEM_TIDEL_ARMOR_EQUIP = register("item.tidal.armor.equip");
    public static final RegistryObject<SoundEvent> BLOCK_CRUMBLING_STONE_CRUMBLE = register("block.crumbling_stone.crumble");
    public static final RegistryObject<SoundEvent> BLOCK_LIGHT_BULB_ON = register("block.light_bulb.on");
    public static final RegistryObject<SoundEvent> BLOCK_LIGHT_BULB_OFF = register("block.light_bulb.off");
    public static final RegistryObject<SoundEvent> ENTITY_CHAPPLE_CONVERT = register("entity.chapple.convert");

    public static final RegistryObject<SoundEvent> BLOCK_DRUM_BEATBOX_HIGH_SOUND = register("block.drum.beatbox.high");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_BEATBOX_MEDIUM_SOUND = register("block.drum.beatbox.medium");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_BEATBOX_LOW_SOUND = register("block.drum.beatbox.low");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DARBUKA_HIGH_SOUND = register("block.drum.darbuka.high");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DARBUKA_MEDIUM_SOUND = register("block.drum.darbuka.medium");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DARBUKA_LOW_SOUND = register("block.drum.darbuka.low");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DHOLAK_HIGH_SOUND = register("block.drum.dholak.high");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DHOLAK_MEDIUM_SOUND = register("block.drum.dholak.medium");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DHOLAK_LOW_SOUND = register("block.drum.dholak.low");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DJEMBE_HIGH_SOUND = register("block.drum.djembe.high");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DJEMBE_MEDIUM_SOUND = register("block.drum.djembe.medium");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_DJEMBE_LOW_SOUND = register("block.drum.djembe.low");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_TABLA_HIGH_SOUND = register("block.drum.tabla.high");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_TABLA_MEDIUM_SOUND = register("block.drum.tabla.medium");
    public static final RegistryObject<SoundEvent> BLOCK_DRUM_TABLA_LOW_SOUND = register("block.drum.tabla.low");


    //TODO sounds
    public static final RegistryObject<SoundEvent> ITEM_BANNER_EQUIP = register("item.banner.equip");
    public static final RegistryObject<SoundEvent> ITEM_ARMOR_EQUIP_COTTON = register("item.armor.equip.cotton");

    public static final SoundType NETHER_BISMUTH_ORE = register("nether_bismuth_ore", 1, 1);
    public static final SoundType BISMUTH_BLOCK = register("bismuth_block", 1, 1);
    public static final SoundType SQUID_LAMP = register("squid_lamp", 1, 1);
    public static final SoundType TERRACOTTA_VASE = register("terracotta_vase", 1, 1);
    public static final SoundType CRUMBLING_STONE = register("crumbling_stone", 1, 1);

	private static SoundType register(String name, float volume, float pitch) {
        return new ForgeSoundType(volume, pitch, register("block." + name + ".break"), register("block." + name + ".step"), register("block." + name + ".place"), register("block." + name + ".hit"), register("block." + name + ".fall"));
    }

    public static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Etcetera.MOD_ID, id)));
    }
}
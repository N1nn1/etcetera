package com.ninni.etcetera.registry;

import static com.ninni.etcetera.Etcetera.MOD_ID;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface EtceteraSoundEvents {

    SoundEvent BLOCK_DICE_ROLL = register("block.dice.roll");
    SoundEvent ITEM_WRENCH_SELECT = register("item.wrench.select");
    SoundEvent ITEM_WRENCH_MODIFY = register("item.wrench.modify");
    SoundEvent ITEM_WRENCH_FAIL = register("item.wrench.fail");
    SoundEvent ITEM_HAMMER_USE = register("item.hammer.use");
    SoundEvent ITEM_CHISEL_USE = register("item.chisel.use");
    SoundEvent ITEM_HANDBELL_RING = register("item.handbell.ring");
    SoundEvent ITEM_TIDEL_ARMOR_EQUIP = register("item.tidal.armor.equip");
    SoundEvent BLOCK_CRUMBLING_STONE_CRUMBLE = register("block.crumbling_stone.crumble");
    SoundEvent BLOCK_LIGHT_BULB_ON = register("block.light_bulb.on");
    SoundEvent BLOCK_LIGHT_BULB_OFF = register("block.light_bulb.off");
    SoundEvent BLOCK_PRICKLY_CAN_OPEN = register("block.prickly_can.open");
    SoundEvent BLOCK_PRICKLY_CAN_CLOSE = register("block.prickly_can.close");
    SoundEvent ENTITY_CHAPPLE_CONVERT = register("entity.chapple.convert");
    SoundEvent ITEM_BANNER_EQUIP = register("item.banner.equip");
    SoundEvent ITEM_BANNER_COLLECT = register("item.banner.collect");
    SoundEvent ITEM_ARMOR_EQUIP_COTTON = register("item.armor.equip.cotton");
    SoundEvent ENTITY_WEAVER_SPIT = register("entity.weaver.spit");
    SoundEvent ENTITY_WEAVER_LAND = register("entity.weaver.land");
    SoundEvent ENTITY_WEAVER_STEP = register("entity.weaver.step");
    SoundEvent ENTITY_WEAVER_IDLE = register("entity.weaver.idle");
    SoundEvent ENTITY_WEAVER_HURT = register("entity.weaver.hurt");
    SoundEvent ENTITY_WEAVER_DEATH = register("entity.weaver.death");
    SoundEvent ENTITY_WEAVER_ATTACK = register("entity.weaver.attack");
    SoundEvent ITEM_ARMOR_SILK_EQUIP = register("item.armor.equip.silk");
    SoundEvent ITEM_ARMOR_ADVENTURER_EQUIP = register("item.armor.equip.adventurer");

    BlockSoundGroup NETHER_BISMUTH_ORE = register("nether_bismuth_ore", 1, 1);
    BlockSoundGroup BISMUTH_BLOCK = register("bismuth_block", 1, 1);
    BlockSoundGroup SQUID_LAMP = register("squid_lamp", 1, 1);
    BlockSoundGroup TERRACOTTA_VASE = register("terracotta_vase", 1, 1);
    BlockSoundGroup CRUMBLING_STONE = register("crumbling_stone", 1, 1);


	private static BlockSoundGroup register(String name, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch, register("block." + name + ".break"), register("block." + name + ".step"), register("block." + name + ".place"), register("block." + name + ".hit"), register("block." + name + ".fall"));
    }

    static SoundEvent register(String id) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
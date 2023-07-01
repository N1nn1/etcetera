package com.ninni.etcetera.sound;

import static com.ninni.etcetera.Etcetera.MOD_ID;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
    SoundEvent ENTITY_CHAPPLE_CONVERT = register("entity.chapple.convert");

    SoundEvent ENTITY_SNAIL_SLIDE = register("entity.snail.slide");
    SoundEvent ENTITY_SNAIL_HURT_HIDDEN = register("entity.snail.hurt_hidden");
    SoundEvent ENTITY_SNAIL_HURT = register("entity.snail.hurt");
    SoundEvent ENTITY_SNAIL_EAT = register("entity.snail.eat");
    SoundEvent ENTITY_SNAIL_DEATH = register("entity.snail.death");
    SoundEvent ENTITY_SNAIL_DEATH_HIDDEN = register("entity.snail.death_hidden");
    SoundEvent ENTITY_SNAIL_SHELL_GROW = register("entity.snail.shell_grow");
    //TODO sounds
    SoundEvent ITEM_BANNER_EQUIP = register("item.banner.equip");
    SoundEvent ENTITY_SNAIL_LAY_EGGS = register("entity.snail.lay_eggs");
    SoundEvent BLOCK_SNAIL_EGGS_HATCH = register("block.snail_eggs.hatch");

    BlockSoundGroup NETHER_BISMUTH_ORE = register("nether_bismuth_ore", 1, 1);
    BlockSoundGroup BISMUTH_BLOCK = register("bismuth_block", 1, 1);
    BlockSoundGroup SQUID_LAMP = register("squid_lamp", 1, 1);
    BlockSoundGroup TERRACOTTA_VASE = register("terracotta_vase", 1, 1);
    BlockSoundGroup CRUMBLING_STONE = register("crumbling_stone", 1, 1);
    BlockSoundGroup MUCUS = register("mucus", 1, 1);
    BlockSoundGroup SNAIL_SHELL = register("snail_shell", 1, 1);

	private static BlockSoundGroup register(String name, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch, register("block." + name + ".break"), register("block." + name + ".step"), register("block." + name + ".place"), register("block." + name + ".hit"), register("block." + name + ".fall"));
    }

    static SoundEvent register(String id) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }
}
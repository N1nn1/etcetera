package com.ninni.etcetera.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

public interface EtceteraSoundEvents {

    SoundEvent BLOCK_NETHER_BISMUTH_ORE_BREAK = nether_bismuth_ore("break");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_STEP  = nether_bismuth_ore("step");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_PLACE = nether_bismuth_ore("place");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_HIT   = nether_bismuth_ore("hit");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_FALL  = nether_bismuth_ore("fall");
    private static SoundEvent nether_bismuth_ore(String type) { return createBlockSound("nether_bismuth_ore", type); }

    SoundEvent BLOCK_BISMUTH_BLOCK_BREAK = bismuth_block("break");
    SoundEvent BLOCK_BISMUTH_BLOCK_STEP  = bismuth_block("step");
    SoundEvent BLOCK_BISMUTH_BLOCK_PLACE = bismuth_block("place");
    SoundEvent BLOCK_BISMUTH_BLOCK_HIT   = bismuth_block("hit");
    SoundEvent BLOCK_BISMUTH_BLOCK_FALL  = bismuth_block("fall");
    private static SoundEvent bismuth_block(String type) { return createBlockSound("bismuth_block", type); }

    SoundEvent BLOCK_GRAVEL_BRICKS_BREAK = gravel_bricks("break");
    SoundEvent BLOCK_GRAVEL_BRICKS_STEP  = gravel_bricks("step");
    SoundEvent BLOCK_GRAVEL_BRICKS_PLACE = gravel_bricks("place");
    SoundEvent BLOCK_GRAVEL_BRICKS_HIT   = gravel_bricks("hit");
    SoundEvent BLOCK_GRAVEL_BRICKS_FALL  = gravel_bricks("fall");
    private static SoundEvent gravel_bricks(String type) { return createBlockSound("gravel_bricks", type); }

    SoundEvent BLOCK_DICE_ROLL  = dice("roll");
    private static SoundEvent dice(String type) { return createBlockSound("dice", type); }

    SoundEvent ITEM_SEXTANT_SUCCESS  = sextant("success");
    SoundEvent ITEM_SEXTANT_FAIL     = sextant("fail");
    private static SoundEvent sextant(String type) { return createItemSound("sextant", type); }

    SoundEvent ITEM_MAGNIFYING_GLASS_USE  = magnifying_glass("use");
    private static SoundEvent magnifying_glass(String type) { return createItemSound("magnifying_glass", type); }

    SoundEvent ITEM_WRENCH_SELECT  = wrench("select");
    SoundEvent ITEM_WRENCH_MODIFY  = wrench("modify");
    SoundEvent ITEM_WRENCH_FAIL    = wrench("fail");
    private static SoundEvent wrench(String type) { return createItemSound("wrench", type); }

    SoundEvent BLOCK_SAND_DRUM_LOW    = drum("sand_low");
    SoundEvent BLOCK_SAND_DRUM_MEDIUM = drum("sand_medium");
    SoundEvent BLOCK_SAND_DRUM_HIGH   = drum("sand_high");
    SoundEvent BLOCK_IRON_BLOCK_DRUM_LOW    = drum("iron_low");
    SoundEvent BLOCK_IRON_BLOCK_DRUM_MEDIUM = drum("iron_medium");
    SoundEvent BLOCK_IRON_BLOCK_DRUM_HIGH   = drum("iron_high");
    SoundEvent BLOCK_PUMPKIN_DRUM_LOW    = drum("pumpkin_low");
    SoundEvent BLOCK_PUMPKIN_DRUM_MEDIUM = drum("pumpkin_medium");
    SoundEvent BLOCK_PUMPKIN_DRUM_HIGH   = drum("pumpkin_high");
    SoundEvent BLOCK_GOLD_BLOCK_DRUM_LOW    = drum("gold_low");
    SoundEvent BLOCK_GOLD_BLOCK_DRUM_MEDIUM = drum("gold_medium");
    SoundEvent BLOCK_GOLD_BLOCK_DRUM_HIGH   = drum("gold_high");
    SoundEvent BLOCK_DRUM_LOW    = drum("low");
    SoundEvent BLOCK_DRUM_MEDIUM = drum("medium");
    SoundEvent BLOCK_DRUM_HIGH   = drum("high");
    private static SoundEvent drum(String type) { return createBlockSound("drum", type); }

    SoundEvent BLOCK_SQUID_LAMP_BREAK = squid_lamp("break");
    SoundEvent BLOCK_SQUID_LAMP_STEP  = squid_lamp("step");
    SoundEvent BLOCK_SQUID_LAMP_PLACE = squid_lamp("place");
    SoundEvent BLOCK_SQUID_LAMP_HIT   = squid_lamp("hit");
    SoundEvent BLOCK_SQUID_LAMP_FALL  = squid_lamp("fall");
    private static SoundEvent squid_lamp(String type) { return createBlockSound("squid_lamp", type); }

    SoundEvent ITEM_ARMOR_EQUIP_TIDAL    = tidal("equip");
    private static SoundEvent tidal(String type) { return createItemSound("tidal.armor", type); }

    SoundEvent BLOCK_TERRACOTTA_VASE_BREAK = terracotta_vase("break");
    private static SoundEvent terracotta_vase(String type) { return createBlockSound("terracotta_vase", type); }


    SoundEvent BLOCK_CRUMBLING_STONE_CRUMBLE = crumbling_stone("crumble");
    private static SoundEvent crumbling_stone(String type) { return createBlockSound("crumbling_stone", type); }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent createBlockSound(String block, String type) { return register("block." + block + "." + type); }
    private static SoundEvent createItemSound(String item, String type) { return register("item." + item + "." + type); }
}

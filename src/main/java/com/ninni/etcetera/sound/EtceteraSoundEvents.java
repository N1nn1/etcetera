package com.ninni.etcetera.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

public interface EtceteraSoundEvents {

    SoundEvent BLOCK_NETHER_BISMUTH_ORE_BREAK = netherBismuthOre("break");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_STEP  = netherBismuthOre("step");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_PLACE = netherBismuthOre("place");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_HIT   = netherBismuthOre("hit");
    SoundEvent BLOCK_NETHER_BISMUTH_ORE_FALL  = netherBismuthOre("fall");
    private static SoundEvent netherBismuthOre(String type) { return createBlockSound("nether_bismuth_ore", type); }

    SoundEvent BLOCK_BISMUTH_BLOCK_BREAK = bismuthBlock("break");
    SoundEvent BLOCK_BISMUTH_BLOCK_STEP  = bismuthBlock("step");
    SoundEvent BLOCK_BISMUTH_BLOCK_PLACE = bismuthBlock("place");
    SoundEvent BLOCK_BISMUTH_BLOCK_HIT   = bismuthBlock("hit");
    SoundEvent BLOCK_BISMUTH_BLOCK_FALL  = bismuthBlock("fall");
    private static SoundEvent bismuthBlock(String type) { return createBlockSound("bismuth_block", type); }

    SoundEvent BLOCK_GRAVEL_BRICKS_BREAK = gravelBricks("break");
    SoundEvent BLOCK_GRAVEL_BRICKS_STEP  = gravelBricks("step");
    SoundEvent BLOCK_GRAVEL_BRICKS_PLACE = gravelBricks("place");
    SoundEvent BLOCK_GRAVEL_BRICKS_HIT   = gravelBricks("hit");
    SoundEvent BLOCK_GRAVEL_BRICKS_FALL  = gravelBricks("fall");
    private static SoundEvent gravelBricks(String type) { return createBlockSound("gravel_bricks", type); }

    SoundEvent BLOCK_PACKED_SILT_BREAK = packedSilt("break");
    SoundEvent BLOCK_PACKED_SILT_STEP  = packedSilt("step");
    SoundEvent BLOCK_PACKED_SILT_PLACE = packedSilt("place");
    SoundEvent BLOCK_PACKED_SILT_HIT   = packedSilt("hit");
    SoundEvent BLOCK_PACKED_SILT_FALL  = packedSilt("fall");
    private static SoundEvent packedSilt(String type) { return createBlockSound("packed_silt", type); }

    SoundEvent BLOCK_SILT_SHINGLES_BREAK = siltShingles("break");
    SoundEvent BLOCK_SILT_SHINGLES_STEP  = siltShingles("step");
    SoundEvent BLOCK_SILT_SHINGLES_PLACE = siltShingles("place");
    SoundEvent BLOCK_SILT_SHINGLES_HIT   = siltShingles("hit");
    SoundEvent BLOCK_SILT_SHINGLES_FALL  = siltShingles("fall");
    private static SoundEvent siltShingles(String type) { return createBlockSound("silt_shingles", type); }

    SoundEvent BLOCK_SILT_POT_BREAK = siltPot("break");
    private static SoundEvent siltPot(String type) { return createBlockSound("silt_pot", type); }

    SoundEvent BLOCK_SILT_BREAK = silt("break");
    SoundEvent BLOCK_SILT_STEP  = silt("step");
    SoundEvent BLOCK_SILT_PLACE = silt("place");
    SoundEvent BLOCK_SILT_HIT   = silt("hit");
    SoundEvent BLOCK_SILT_FALL  = silt("fall");
    private static SoundEvent silt(String type) { return createBlockSound("silt", type); }

    SoundEvent BLOCK_DICE_ROLL  = dice("roll");
    private static SoundEvent dice(String type) { return createBlockSound("dice", type); }

    SoundEvent ITEM_SEXTANT_SUCCESS  = sextant("success");
    SoundEvent ITEM_SEXTANT_FAIL     = sextant("fail");
    private static SoundEvent sextant(String type) { return createItemSound("sextant", type); }

    SoundEvent ITEM_MAGNIFYING_GLASS_USE  = magnifyingGlass("use");
    private static SoundEvent magnifyingGlass(String type) { return createItemSound("magnifying_glass", type); }

    SoundEvent ITEM_WRENCH_SELECT  = wrench("select");
    SoundEvent ITEM_WRENCH_MODIFY  = wrench("modify");
    SoundEvent ITEM_WRENCH_FAIL    = wrench("fail");
    private static SoundEvent wrench(String type) { return createItemSound("wrench", type); }

    SoundEvent ITEM_HAMMER_USE  = hammer("use");
    private static SoundEvent hammer(String type) { return createItemSound("hammer", type); }

    SoundEvent ITEM_CHISEL_USE  = chisel("use");
    private static SoundEvent chisel(String type) { return createItemSound("chisel", type); }

    SoundEvent ITEM_HANDBELL_RING  = handbell("ring");
    private static SoundEvent handbell(String type) { return createItemSound("handbell", type); }

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

    SoundEvent BLOCK_SQUID_LAMP_BREAK = squidLamp("break");
    SoundEvent BLOCK_SQUID_LAMP_STEP  = squidLamp("step");
    SoundEvent BLOCK_SQUID_LAMP_PLACE = squidLamp("place");
    SoundEvent BLOCK_SQUID_LAMP_HIT   = squidLamp("hit");
    SoundEvent BLOCK_SQUID_LAMP_FALL  = squidLamp("fall");
    private static SoundEvent squidLamp(String type) { return createBlockSound("squid_lamp", type); }

    SoundEvent ITEM_ARMOR_EQUIP_TIDAL = tidal("equip");
    private static SoundEvent tidal(String type) { return createItemSound("tidal.armor", type); }

    SoundEvent BLOCK_TERRACOTTA_VASE_BREAK = terracottaVase("break");
    private static SoundEvent terracottaVase(String type) { return createBlockSound("terracotta_vase", type); }


    SoundEvent BLOCK_CRUMBLING_STONE_CRUMBLE = crumblingStone("crumble");
    private static SoundEvent crumblingStone(String type) { return createBlockSound("crumbling_stone", type); }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent createBlockSound(String block, String type) { return register("block." + block + "." + type); }
    private static SoundEvent createItemSound(String item, String type) { return register("item." + item + "." + type); }
}

package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

public class EtceteraSoundEvents {
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, Constants.MOD_ID);

    public static final SoundEvent BLOCK_DICE_ROLL = register("block.dice.roll");

    public static final SoundEvent ITEM_WRENCH_SELECT = register("item.wrench.select");
    public static final SoundEvent ITEM_WRENCH_MODIFY = register("item.wrench.modify");
    public static final SoundEvent ITEM_WRENCH_FAIL = register("item.wrench.fail");

    public static final SoundEvent ITEM_HAMMER_USE = register("item.hammer.use");

    public static final SoundEvent ITEM_CHISEL_USE = register("item.chisel.use");

    public static final SoundEvent ITEM_HANDBELL_RING = register("item.handbell.ring");

    public static final SoundEvent BLOCK_CRUMBLING_STONE_CRUMBLE = register("block.crumbling_stone.crumble");

    public static final SoundEvent BLOCK_LIGHT_BULB_ON = register("block.light_bulb.on");
    public static final SoundEvent BLOCK_LIGHT_BULB_OFF = register("block.light_bulb.off");

    public static final SoundEvent BLOCK_PRICKLY_CAN_OPEN = register("block.prickly_can.open");
    public static final SoundEvent BLOCK_PRICKLY_CAN_CLOSE = register("block.prickly_can.close");

    public static final SoundEvent ITEM_BANNER_EQUIP = register("item.banner.equip");
    public static final SoundEvent ITEM_BANNER_COLLECT = register("item.banner.collect");

    public static final SoundEvent ITEM_TIDEL_ARMOR_EQUIP = register("item.tidal.armor.equip");
    public static final SoundEvent ITEM_ARMOR_EQUIP_COTTON = register("item.armor.equip.cotton");
    public static final SoundEvent ITEM_ARMOR_ADVENTURER_EQUIP = register("item.armor.equip.adventurer");

    public static final SoundEvent ENTITY_CHAPPLE_CONVERT = register("entity.chapple.convert");

    public static final SoundEvent ENTITY_WEAVER_SPIT = register("entity.weaver.spit");
    public static final SoundEvent ENTITY_WEAVER_LAND = register("entity.weaver.land");
    public static final SoundEvent ENTITY_WEAVER_STEP = register("entity.weaver.step");
    public static final SoundEvent ENTITY_WEAVER_IDLE = register("entity.weaver.idle");
    public static final SoundEvent ENTITY_WEAVER_HURT = register("entity.weaver.hurt");
    public static final SoundEvent ENTITY_WEAVER_DEATH = register("entity.weaver.death");
    public static final SoundEvent ENTITY_WEAVER_ATTACK = register("entity.weaver.attack");
    public static final SoundEvent ITEM_ARMOR_SILK_EQUIP = register("item.armor.equip.silk");

    public static final SoundEvent ENTITY_GOLDEN_GOLEM_DEATH = register("entity.golden_golem.death");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_GRANT = register("entity.golden_golem.grant");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_HURT = register("entity.golden_golem.hurt");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_IDLE = register("entity.golden_golem.idle");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_ITEM = register("entity.golden_golem.item");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_LAND = register("entity.golden_golem.land");
    public static final SoundEvent ENTITY_GOLDEN_GOLEM_THROW = register("entity.golden_golem.throw");

    public static final SoundEvent MUSIC_DISC_SQUALL = register("music_disc.squall");

    public static final SoundEvent ENTITY_RUBBER_CHICKEN_SQUEEZE = register("entity.rubber_chicken.squeeze");

    public static final SoundType RUBBER = register("rubber", 1, 1);
    public static final SoundType NETHER_BISMUTH_ORE = register("nether_bismuth_ore", 1, 1);
    public static final SoundType BISMUTH_BLOCK = register("bismuth_block", 1, 1);
    public static final SoundType SQUID_LAMP = register("squid_lamp", 1, 1);
    public static final SoundType TERRACOTTA_VASE = register("terracotta_vase", 1, 1);
    public static final SoundType CRUMBLING_STONE = register("crumbling_stone", 1, 1);

    private static SoundType register(String name, float volume, float pitch) {
        return new SoundType(volume, pitch, register("block." + name + ".break"), register("block." + name + ".step"), register("block." + name + ".place"), register("block." + name + ".hit"), register("block." + name + ".fall"));
    }

    public static SoundEvent register(String id) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(identifier);
        SOUND_EVENTS.register(id, () -> soundEvent);
        return soundEvent;
    }

    public static void init() {
    }
}
package com.ninni.etcetera.stat;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;

import static net.minecraft.stat.Stats.*;

public class EtceteraStats {

    public static final Identifier ROTATE_DICE = register("rotate_dice", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_ITEM_STAND = register("interact_with_item_stand", StatFormatter.DEFAULT);
    public static final Identifier OPEN_PRICKLY_CAN = register("open_prickly_can", StatFormatter.DEFAULT);


    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}

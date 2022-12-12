package com.ninni.etcetera.stat;

import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.stat.Stats.*;

public class EtceteraStats {

    public static final Identifier ROTATE_DICE = register("rotate_dice", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_ITEM_STAND = register("interact_with_item_stand", StatFormatter.DEFAULT);


    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}

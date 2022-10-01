package com.ninni.etcetera.stat;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;
import static net.minecraft.stat.Stats.*;

public class EtceteraStats {

    public static final Identifier ROTATE_DICE = register("rotate_dice", StatFormatter.DEFAULT);
    public static final Identifier OPEN_SILT_POT = register("open_silt_pot", StatFormatter.DEFAULT);


    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    private static <T> StatType<T> register(String id, Registry<T> registry) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registry.STAT_TYPE, identifier, new StatType<T>(registry));
    }
}

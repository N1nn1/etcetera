package com.ninni.etcetera;

import com.ninni.etcetera.block.enums.EtceteraInstrument;
import net.minecraft.block.Block;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.Item;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public interface EtceteraProperties {
    EnumProperty<EtceteraInstrument> INSTRUMENT = EnumProperty.of("instrument", EtceteraInstrument.class);
}

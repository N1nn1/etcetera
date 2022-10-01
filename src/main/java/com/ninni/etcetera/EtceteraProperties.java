package com.ninni.etcetera;

import com.ninni.etcetera.block.enums.EtceteraInstrument;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

@SuppressWarnings("unused")
public interface EtceteraProperties {
    EnumProperty<EtceteraInstrument> INSTRUMENT = EnumProperty.of("instrument", EtceteraInstrument.class);
    BooleanProperty FILLED = BooleanProperty.of("filled");
}

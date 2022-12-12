package com.ninni.etcetera;

import com.ninni.etcetera.block.enums.DrumType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public interface EtceteraProperties {
    EnumProperty<DrumType> DRUM_TYPE = EnumProperty.of("type", DrumType.class);
    BooleanProperty GLASS = BooleanProperty.of("glass");
}
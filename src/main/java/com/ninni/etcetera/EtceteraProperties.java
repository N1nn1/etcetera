package com.ninni.etcetera;

import com.ninni.etcetera.block.enums.EtceteraColumnShape;
import com.ninni.etcetera.block.enums.DrumType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public interface EtceteraProperties {
    EnumProperty<DrumType> DRUM_TYPE = EnumProperty.of("type", DrumType.class);
    BooleanProperty FILLED = BooleanProperty.of("filled");
    EnumProperty<EtceteraColumnShape> SHAPE = EnumProperty.of("shape", EtceteraColumnShape.class);
}
package com.ninni.etcetera.registry;

import com.ninni.etcetera.block.enums.DrumType;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface EtceteraProperties {
    EnumProperty<DrumType> DRUM_TYPE = EnumProperty.create("type", DrumType.class);
    EnumProperty<LightBulbBrightness> BRIGHTNESS = EnumProperty.create("brightness", LightBulbBrightness.class);
    BooleanProperty GLASS = BooleanProperty.create("glass");
    IntegerProperty SOLID = IntegerProperty.create("solid", 1, 3);
}
package com.ninni.etcetera.registry;

import com.ninni.etcetera.block.enums.DrumType;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public interface EtceteraProperties {
    EnumProperty<DrumType> DRUM_TYPE = EnumProperty.create("type", DrumType.class);
    EnumProperty<LightBulbBrightness> BRIGHTNESS = EnumProperty.create("brightness", LightBulbBrightness.class);
    EnumProperty<LightBulbBrightness> WANTED_BRIGHTNESS = EnumProperty.create("wanted_brightness", LightBulbBrightness.class);
    BooleanProperty GLASS = BooleanProperty.create("glass");
    BooleanProperty SOLID = BooleanProperty.create("solid");
}
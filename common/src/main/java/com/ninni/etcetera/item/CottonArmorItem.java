package com.ninni.etcetera.item;

import com.ninni.etcetera.registry.EtceteraArmorMaterials;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class CottonArmorItem extends ArmorItem implements Equipable {
    public static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
            return ArmorItem.dispenseArmor(source, stack) ? stack : super.execute(source, stack);
        }
    };

    boolean sweater;

    public CottonArmorItem(Properties properties, boolean sweater) {
        super(EtceteraArmorMaterials.COTTON, sweater ? ArmorItem.Type.CHESTPLATE : ArmorItem.Type.HELMET, properties);
        this.sweater = sweater;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, world, user, hand);
    }

    @Override
    public @NotNull Holder<SoundEvent> getEquipSound() {
        return BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EtceteraSoundEvents.ITEM_ARMOR_EQUIP_COTTON);
    }
}
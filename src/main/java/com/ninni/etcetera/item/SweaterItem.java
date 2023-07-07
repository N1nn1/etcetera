package com.ninni.etcetera.item;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.World;

public class SweaterItem extends Item implements Equipment {
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior(){
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            return ArmorItem.dispenseArmor(pointer, stack) ? stack : super.dispenseSilently(pointer, stack);
        }
    };


    public SweaterItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public SoundEvent getEquipSound() {
        return EtceteraSoundEvents.ITEM_ARMOR_EQUIP_COTTON;
    }
}

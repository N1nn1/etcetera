package com.ninni.etcetera.client.gui.screen;

import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PricklyCanScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final int rows;

    private PricklyCanScreenHandler(MenuType<?> type, int syncId, Inventory playerInventory, int rows) {
        this(type, syncId, playerInventory, new SimpleContainer(9 * rows), rows);
    }

    public PricklyCanScreenHandler(MenuType<?> type, int syncId, Inventory playerInventory, Container inventory, int rows) {
        super(type, syncId);
        checkContainerSize(inventory, rows * 9);
        this.inventory = inventory;
        this.rows = rows;

        inventory.startOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int j;
        int k;
        for (j = 0; j < this.rows; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
    }

    public static PricklyCanScreenHandler createGeneric9x3(int syncId, Inventory playerInventory) {
        return new PricklyCanScreenHandler(EtceteraScreenHandlerType.PRICKLY_CAN, syncId, playerInventory, 3);
    }

    public static PricklyCanScreenHandler createGeneric9x3(int syncId, Inventory playerInventory, Container inventory) {
        return new PricklyCanScreenHandler(EtceteraScreenHandlerType.PRICKLY_CAN, syncId, playerInventory, inventory, 3);
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (id == 1) {
            this.inventory.clearContent();
            return true;
        }
        return false;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index < this.rows * 9) {
                if (!this.moveItemStackTo(itemStack2, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public Container getInventory() {
        return this.inventory;
    }
}
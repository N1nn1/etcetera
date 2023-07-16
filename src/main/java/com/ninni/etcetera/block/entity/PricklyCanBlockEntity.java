package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.block.PricklyCanBlock;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PricklyCanBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;

    public PricklyCanBlockEntity(BlockPos worldPosition, BlockState state) {
        super(EtceteraBlockEntityType.PRICKLY_CAN.get(), worldPosition, state);
        this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(Level world, BlockPos worldPosition, BlockState state) {
                PricklyCanBlockEntity.this.playSound(SoundEvents.BARREL_OPEN);
                PricklyCanBlockEntity.this.setOpen(state, true);
            }

            @Override
            protected void onClose(Level world, BlockPos worldPosition, BlockState state) {
                PricklyCanBlockEntity.this.playSound(SoundEvents.BARREL_CLOSE);
                PricklyCanBlockEntity.this.setOpen(state, false);
            }

            @Override
            protected void openerCountChanged(Level world, BlockPos worldPosition, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            @Override
            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof PricklyCanScreenHandler pricklyCanScreenHandler) {
                    Container container = pricklyCanScreenHandler.getContainer();
                    return container == PricklyCanBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (!this.trySaveLootTable(nbt)) {
            ContainerHelper.saveAllItems(nbt, this.inventory);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("etcetera.container.prickly_can");
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.inventory);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return PricklyCanScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    void setOpen(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(PricklyCanBlock.OPEN, open), 3);
    }

    void playSound(SoundEvent soundEvent) {
        this.level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }
}

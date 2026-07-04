package com.ninni.etcetera.block.entity;
import net.minecraft.core.HolderLookup;

import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStandBlockEntity extends BlockEntity implements Clearable {
    private final NonNullList<ItemStack> itemsDisplayed = NonNullList.withSize(1, ItemStack.EMPTY);

    public ItemStandBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(EtceteraBlockEntityType.ITEM_STAND.get(), pos, state);
    }

    public NonNullList<ItemStack> getItemsDisplayed() {
        return this.itemsDisplayed;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbtCompound = new CompoundTag();
        ContainerHelper.saveAllItems(nbtCompound, this.itemsDisplayed, provider);
        return nbtCompound;
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        this.itemsDisplayed.clear();
        ContainerHelper.loadAllItems(nbt, this.itemsDisplayed, provider);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbt, provider);
        ContainerHelper.saveAllItems(nbt, this.itemsDisplayed, provider);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean addItem(@Nullable Entity user, ItemStack stack) {
        for (int i = 0; i < this.itemsDisplayed.size(); ++i) {
            ItemStack itemStack = this.itemsDisplayed.get(i);
            if (!itemStack.isEmpty()) continue;
            this.itemsDisplayed.set(i, stack.split(1));
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(user, this.getBlockState()));
            this.sendBlockUpdated();
            return true;
        }
        return false;
    }

    private void sendBlockUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void clearContent() {
        this.itemsDisplayed.clear();
    }
}
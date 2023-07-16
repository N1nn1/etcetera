package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
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
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundtag, this.itemsDisplayed, true);
        return compoundtag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.itemsDisplayed.clear();
        ContainerHelper.loadAllItems(tag, this.itemsDisplayed);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.itemsDisplayed, true);
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
            this.updateListeners();
            return true;
        }
        return false;
    }

    private void updateListeners() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clearContent() {
        this.itemsDisplayed.clear();
    }
}

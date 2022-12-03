package com.ninni.etcetera.entity;

import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class TurtleRaftEntity extends BoatEntity {
    public TurtleRaftEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public TurtleRaftEntity(World world, double x, double y, double z) {
        this(EtceteraEntityType.TURTLE_RAFT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) { }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) { }

    @Override
    protected float getPassengerHorizontalOffset() {
        return -0.15f;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.25;
    }

    @Override
    public void onBubbleColumnSurfaceCollision(boolean drag) { }

    @Override
    public Item asItem() {
        return EtceteraItems.TURTLE_RAFT;
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}

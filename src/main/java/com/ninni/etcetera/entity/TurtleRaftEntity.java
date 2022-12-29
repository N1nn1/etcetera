package com.ninni.etcetera.entity;

import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class TurtleRaftEntity extends BoatEntity {
    //TODO sounds
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(TurtleRaftEntity.class, TrackedDataHandlerRegistry.INTEGER);

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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }


    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(int color) {
        this.dataTracker.set(COLOR, color);
    }

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

    //TODO this always drops the non tinted one
    @Override
    public Item asItem() {
        return EtceteraItems.TURTLE_RAFT;
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}

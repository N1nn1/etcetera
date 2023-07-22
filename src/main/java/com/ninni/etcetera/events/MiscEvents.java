package com.ninni.etcetera.events;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.item.TurtleRaftItem;
import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onBlockModified(BlockEvent.BlockToolModificationEvent event) {
        BlockState state = event.getState();
        ToolAction toolAction = event.getToolAction();
        if (toolAction.equals(ToolActions.AXE_SCRAPE) && state.is(EtceteraBlocks.WAXED_CRUMBLING_STONE.get())) {
            event.setFinalState(EtceteraBlocks.CRUMBLING_STONE.get().withPropertiesOf(state));
        }
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if (name.equals(BuiltInLootTables.BASTION_OTHER)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.BASTION_TREASURE)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.PILLAGER_OUTPOST)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
        }
        if (name.equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))).build());
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.COTTON_SEEDS.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))).build());
        }
    }

    @SubscribeEvent
    public void onTagUpdated(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(EtceteraItems.EGGPLE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.x(), position.y(), position.z()), entity -> entity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(EtceteraItems.GOLDEN_EGGPLE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.x(), position.y(), position.z()), entity -> entity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(EtceteraItems.TURTLE_RAFT.get(), new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            protected ItemStack execute(BlockSource world, ItemStack stack) {
                Direction direction = world.getBlockState().getValue(DispenserBlock.FACING);
                Level level = world.getLevel();
                double d0 = 0.5625D + (double) EtceteraEntityType.TURTLE_RAFT.get().getWidth() / 2.0D;
                double d1 = world.x() + (double)direction.getStepX() * d0;
                double d2 = world.y() + (double)((float)direction.getStepY() * 1.125F);
                double d3 = world.z() + (double)direction.getStepZ() * d0;
                BlockPos blockpos = world.getPos().relative(direction);
                TurtleRaftEntity turtleRaftEntity = new TurtleRaftEntity(level, d0, d1, d2);
                if (stack.getItem() instanceof TurtleRaftItem turtleRaftItem) {
                    turtleRaftEntity.setColor(turtleRaftItem.getColor(stack));
                }
                turtleRaftEntity.setYRot(direction.toYRot());
                double d4;
                if (turtleRaftEntity.canBoatInFluid(level.getFluidState(blockpos))) {
                    d4 = 1.0D;
                } else {
                    if (!level.getBlockState(blockpos).isAir() || !turtleRaftEntity.canBoatInFluid(level.getFluidState(blockpos.below()))) {
                        return this.defaultDispenseItemBehavior.dispense(world, stack);
                    }

                    d4 = 0.0D;
                }

                turtleRaftEntity.setPos(d1, d2 + d4, d3);
                level.addFreshEntity(turtleRaftEntity);
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(BlockSource source) {
                source.getLevel().levelEvent(1000, source.getPos(), 0);
            }
        });
    }

    @SubscribeEvent
    public void onResourceLoad(AddReloadListenerEvent event) {
        event.addListener(EtceteraVanillaIntegration.CHISELLING_MANAGER);
        event.addListener(EtceteraVanillaIntegration.HAMMERING_MANAGER);
    }

}

package com.ninni.etcetera.item;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Objects;


public class MagnifyingGlassItem extends Item {
    int cooldown = 30;
    int fasterCooldown = 10;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public MagnifyingGlassItem(Settings settings) { super(settings); }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
       if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
           if (entity.getHealth() < entity.getMaxHealth()) player.sendMessage(Text.translatable(this.getTranslationKey() + ".actionbar.entity_hurt", entity.getName(), df.format(entity.getHealth() / 2), entity.getMaxHealth() / 2), true);
           else player.sendMessage(Text.translatable(this.getTranslationKey() + ".actionbar.entity", entity.getName(), entity.getHealth() / 2), true);
           player.getItemCooldownManager().set(this, cooldown);
           player.incrementStat(Stats.USED.getOrCreateStat(this));
           player.playSound(EtceteraSoundEvents.ITEM_MAGNIFYING_GLASS_USE, 0.5F, 1);
           spawnParticles(player.getWorld(), entity.getBlockPos());
       }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getBlockPos());
        StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();
        if (!stateManager.getProperties().isEmpty()) {
            if (!world.isClient) return ActionResult.PASS;
            this.getPropeties(context.getPlayer(), state, Objects.requireNonNull(context.getPlayer()).getStackInHand(Hand.MAIN_HAND));
            Objects.requireNonNull(context.getPlayer()).playSound(EtceteraSoundEvents.ITEM_MAGNIFYING_GLASS_USE, 0.5F, 1);
            spawnParticles(world, context.getBlockPos());
        }
        spawnParticles(world, context.getBlockPos());
        context.getPlayer().playSound(EtceteraSoundEvents.ITEM_MAGNIFYING_GLASS_USE, 1, 1);
        if (!world.isClient && context.getPlayer() != null && !this.getName(context.getPlayer(), world.getBlockState(context.getBlockPos()), world, context.getBlockPos(), true, context.getStack())) return ActionResult.FAIL;
        return ActionResult.PASS;
    }

    private void spawnParticles(WorldAccess world, BlockPos pos) {
        Vec3d vec3d = Vec3d.ofCenter(pos);
        net.minecraft.util.math.random.Random random = world.getRandom();
        for (int i = 0; i < 25; ++i) {
            double velX = random.nextGaussian() * 1.5;
            double velY = random.nextGaussian() * 1.5;
            double velZ = random.nextGaussian() * 1.5;
            world.addParticle(ParticleTypes.ENCHANT, vec3d.x, vec3d.y + 1, vec3d.z, velX, velY, velZ);
        }
    }

    @SuppressWarnings("unused")
    private boolean getName(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        player.sendMessage(Text.translatable(state.getBlock().getTranslationKey()), true);
        player.getItemCooldownManager().set(this, cooldown);
        return true;
    }

    private void getPropeties(PlayerEntity player, BlockState state, ItemStack stack) {
        Block block = state.getBlock();
        StateManager<Block, BlockState> stateManager = block.getStateManager();
        String string = Registry.BLOCK.getId(block).toString();
        if (!stateManager.getProperties().isEmpty() && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            NbtCompound nbtCompound = stack.getOrCreateSubNbt("DebugProperty");
            Property<?> property = stateManager.getProperty(nbtCompound.getString(string));
            property = cycle(stateManager.getProperties(), property, player.shouldCancelInteraction());
            nbtCompound.putString(string, property.getName());
            player.getItemCooldownManager().set(this, fasterCooldown);
            player.sendMessage(Text.translatable(this.getTranslationKey() + ".select", state.getBlock().getName(), property.getName(), getValueString(state, property)), true);
        }
    }

    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) { return inverse ? Util.previous(elements, current) : Util.next(elements, current); }
    private static <T extends Comparable<T>> String getValueString(BlockState state, Property<T> property) { return property.name(state.get(property)); }
}

package com.ninni.etcetera.item;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class SextantItem extends Item {
    public SextantItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.getY() >= 60) {
            player.sendMessage(Text.translatable(this.getTranslationKey() + ".actionbar", player.getBlockX(), player.getBlockZ()), true);
            player.getItemCooldownManager().set(this, 30);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            player.playSound(EtceteraSoundEvents.ITEM_SEXTANT_SUCCESS, 1, 1);
            Vec3d vec3d = player.getBoundingBox().getCenter();
            net.minecraft.util.math.random.Random random = world.getRandom();
            for (int i = 0; i < 25; ++i) {
                double velX = random.nextGaussian() * 1.5;
                double velY = random.nextGaussian() * 1.5;
                double velZ = random.nextGaussian() * 1.5;
                world.addParticle(ParticleTypes.ENCHANT, vec3d.x, vec3d.y + 1, vec3d.z, velX, velY, velZ);
            }
            return TypedActionResult.success(player.getStackInHand(hand));
        }
        player.getItemCooldownManager().set(this, 15);
        player.playSound(EtceteraSoundEvents.ITEM_SEXTANT_FAIL, 1, 1);
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}

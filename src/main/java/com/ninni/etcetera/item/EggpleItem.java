package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.EggpleEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EggpleItem extends Item {
    public boolean isGolden;

    public EggpleItem(boolean golden, Properties settings) {
        super(settings);
        this.isGolden = golden;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        if (!world.isClientSide) {
            EggpleEntity eggple = new EggpleEntity(world, user);
            eggple.setItem(itemStack);
            eggple.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0f, 1.5f, 1.0f);
            world.addFreshEntity(eggple);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) itemStack.shrink(1);
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

}

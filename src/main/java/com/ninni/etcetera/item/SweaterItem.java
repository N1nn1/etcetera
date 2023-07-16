package com.ninni.etcetera.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ninni.etcetera.client.model.CottonArmorModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class SweaterItem extends ArmorItem implements Equipable {
    private static final Material material = new Material();
    private static final BiFunction<String, String, ResourceLocation> BI_FUNCTION = (s, s2) -> new ResourceLocation(MOD_ID, "textures/models/armor/cotton_" + s + "_" + s2 + ".png");
    boolean sweater;
    public static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        protected ItemStack execute(BlockSource pointer, ItemStack stack) {
            return ArmorItem.dispenseArmor(pointer, stack) ? stack : super.execute(pointer, stack);
        }

    };

    public SweaterItem(Properties properties, boolean sweater) {
        super(material, sweater ? Type.CHESTPLATE : Type.HELMET, properties);
        this.sweater = sweater;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_40390_) {
        return ImmutableMultimap.of();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return new CottonArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(EtceteraEntityModelLayers.PLAYER_COTTON));
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String s) {
        String name = BuiltInRegistries.ITEM.getKey(this).getPath();
        String type = name.contains("sweater") || name.contains("robe") ? "sweater" : "hat";
        String traderType = name.contains("hood") ? "hood" : "robe";
        String removal = name.replace("cotton_", "").replace("_" + (name.contains("trader") ? traderType : type), "");
        return BI_FUNCTION.apply(type, removal).toString();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, world, user, hand);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return sweater ? EquipmentSlot.CHEST : EquipmentSlot.HEAD;
    }

    @Override
    public SoundEvent getEquipSound() {
        return EtceteraSoundEvents.ITEM_ARMOR_EQUIP_COTTON.get();
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_40392_, ItemStack p_40393_) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }

    private static class Material implements ArmorMaterial {

        @Override
        public int getDurabilityForType(Type p_266807_) {
            return 0;
        }

        @Override
        public int getDefenseForType(Type p_267168_) {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of();
        }

        @Override
        public String getName() {
            return "cotton";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }

}

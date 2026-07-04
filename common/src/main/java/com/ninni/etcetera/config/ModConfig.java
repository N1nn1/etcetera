package com.ninni.etcetera.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.Services;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve("etcetera.json").toFile();
    private static ModConfig INSTANCE;

    // General
    public boolean enableVillagerTrades = true;
    public boolean enableLootTableModifiers = true;
    public boolean enableFlattenableBlocks = true;
    public boolean enableCompostables = true;

    // Chapple
    public int normalAppleLayTimeMin = 6000;
    public int normalAppleLayTimeMax = 12000;
    public int goldenChappleAppleLayTimeMin = 6000;
    public int goldenChappleAppleLayTimeMax = 12000;
    public int goldenChappleGoldenAppleChance = 3;
    public int chappleShearedAppleCount = 5;

    // Weaver
    public double weaverMaxHealth = 50.0;
    public double weaverMovementSpeed = 0.3;
    public double weaverAttackDamage = 8.0;

    // Golden Golem
    public double goldenGolemMaxHealth = 20.0;
    public double goldenGolemMovementSpeed = 0.3;
    public double goldenGolemFlyingSpeed = 1.0;
    public double goldenGolemAttackDamage = 2.0;
    public double goldenGolemFollowRange = 48.0;
    public int goldenGolemHealingCharges = 10;
    public int goldenGolemHealingCooldown = 2400;
    public double goldenGolemHealingAmount = 8.0;
    public int goldenGolemAbsorptionDuration = 2400;
    public int goldenGolemAbsorptionAmplifier = 1;

    // Handbell
    public double handbellRange = 48.0;
    public int handbellGlowDuration = 120;

    // Dream Catcher
    public double dreamCatcherRangeX = 8.0;
    public double dreamCatcherRangeY = 5.0;
    public double dreamCatcherRangeZ = 8.0;
    public int dreamCatcherRegenerationDuration = 300;
    public int dreamCatcherRegenerationAmplifier = 2;
    public int dreamCatcherStrengthDuration = 1800;
    public int dreamCatcherStrengthAmplifier = 0;
    public int dreamCatcherDrowsyDuration = 10;

    // Eggple
    public int eggpleFourHatchChance = 160;

    // Blocks
    public int coppertapDripChance = 5;
    public int coppertapFillCauldronChance = 7;
    public int rubberCauldronSolidifyChance = 7;
    public int crumblingStoneInverseChanceStepOn = 25;
    public int cottonGrowthSlowdown = 3;

    public static ModConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                Constants.LOG.error("Failed to load etcetera.json", e);
                INSTANCE = new ModConfig();
                save();
            }
        } else {
            INSTANCE = new ModConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            Constants.LOG.error("Failed to save etcetera.json", e);
        }
    }

    public static Screen createScreen(Screen parent) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.etcetera.title"))
                .save(ModConfig::save);

        ModConfig config = get();

        // Integrations Category
        ConfigCategory.Builder integrations = ConfigCategory.createBuilder()
                .name(Component.translatable("config.etcetera.category.integrations"));
        integrations.option(createBoolOption("enableVillagerTrades", true, () -> config.enableVillagerTrades, v -> config.enableVillagerTrades = v));
        integrations.option(createBoolOption("enableLootTableModifiers", true, () -> config.enableLootTableModifiers, v -> config.enableLootTableModifiers = v));
        integrations.option(createBoolOption("enableFlattenableBlocks", true, () -> config.enableFlattenableBlocks, v -> config.enableFlattenableBlocks = v));
        integrations.option(createBoolOption("enableCompostables", true, () -> config.enableCompostables, v -> config.enableCompostables = v));

        // Mobs Category
        ConfigCategory.Builder mobs = ConfigCategory.createBuilder()
                .name(Component.translatable("config.etcetera.category.mobs"));

        mobs.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.chapple"))
                .option(createIntOption("normalAppleLayTimeMin", 6000, () -> config.normalAppleLayTimeMin, v -> config.normalAppleLayTimeMin = v, 1, 100000))
                .option(createIntOption("normalAppleLayTimeMax", 12000, () -> config.normalAppleLayTimeMax, v -> config.normalAppleLayTimeMax = v, 1, 100000))
                .option(createIntOption("goldenChappleAppleLayTimeMin", 6000, () -> config.goldenChappleAppleLayTimeMin, v -> config.goldenChappleAppleLayTimeMin = v, 1, 100000))
                .option(createIntOption("goldenChappleAppleLayTimeMax", 12000, () -> config.goldenChappleAppleLayTimeMax, v -> config.goldenChappleAppleLayTimeMax = v, 1, 100000))
                .option(createIntOption("goldenChappleGoldenAppleChance", 3, () -> config.goldenChappleGoldenAppleChance, v -> config.goldenChappleGoldenAppleChance = v, 1, 10000))
                .option(createIntOption("chappleShearedAppleCount", 5, () -> config.chappleShearedAppleCount, v -> config.chappleShearedAppleCount = v, 1, 64))
                .build());

        mobs.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.weaver"))
                .option(createDoubleOption("weaverMaxHealth", 50.0, () -> config.weaverMaxHealth, v -> config.weaverMaxHealth = v, 1.0, 1000.0))
                .option(createDoubleOption("weaverMovementSpeed", 0.3, () -> config.weaverMovementSpeed, v -> config.weaverMovementSpeed = v, 0.0, 10.0))
                .option(createDoubleOption("weaverAttackDamage", 8.0, () -> config.weaverAttackDamage, v -> config.weaverAttackDamage = v, 0.0, 1000.0))
                .build());

        mobs.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.golden_golem"))
                .option(createDoubleOption("goldenGolemMaxHealth", 20.0, () -> config.goldenGolemMaxHealth, v -> config.goldenGolemMaxHealth = v, 1.0, 1000.0))
                .option(createDoubleOption("goldenGolemMovementSpeed", 0.3, () -> config.goldenGolemMovementSpeed, v -> config.goldenGolemMovementSpeed = v, 0.0, 10.0))
                .option(createDoubleOption("goldenGolemFlyingSpeed", 1.0, () -> config.goldenGolemFlyingSpeed, v -> config.goldenGolemFlyingSpeed = v, 0.0, 10.0))
                .option(createDoubleOption("goldenGolemAttackDamage", 2.0, () -> config.goldenGolemAttackDamage, v -> config.goldenGolemAttackDamage = v, 0.0, 1000.0))
                .option(createDoubleOption("goldenGolemFollowRange", 48.0, () -> config.goldenGolemFollowRange, v -> config.goldenGolemFollowRange = v, 1.0, 2048.0))
                .option(createIntOption("goldenGolemHealingCharges", 10, () -> config.goldenGolemHealingCharges, v -> config.goldenGolemHealingCharges = v, 1, 1000))
                .option(createIntOption("goldenGolemHealingCooldown", 2400, () -> config.goldenGolemHealingCooldown, v -> config.goldenGolemHealingCooldown = v, 0, 100000))
                .option(createDoubleOption("goldenGolemHealingAmount", 8.0, () -> config.goldenGolemHealingAmount, v -> config.goldenGolemHealingAmount = v, 0.0, 1000.0))
                .option(createIntOption("goldenGolemAbsorptionDuration", 2400, () -> config.goldenGolemAbsorptionDuration, v -> config.goldenGolemAbsorptionDuration = v, 0, 100000))
                .option(createIntOption("goldenGolemAbsorptionAmplifier", 1, () -> config.goldenGolemAbsorptionAmplifier, v -> config.goldenGolemAbsorptionAmplifier = v, 0, 255))
                .build());

        // Items & Blocks Category
        ConfigCategory.Builder itemsBlocks = ConfigCategory.createBuilder()
                .name(Component.translatable("config.etcetera.category.items_blocks"));

        itemsBlocks.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.handbell"))
                .option(createDoubleOption("handbellRange", 48.0, () -> config.handbellRange, v -> config.handbellRange = v, 1.0, 1024.0))
                .option(createIntOption("handbellGlowDuration", 120, () -> config.handbellGlowDuration, v -> config.handbellGlowDuration = v, 0, 100000))
                .build());

        itemsBlocks.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.dream_catcher"))
                .option(createDoubleOption("dreamCatcherRangeX", 8.0, () -> config.dreamCatcherRangeX, v -> config.dreamCatcherRangeX = v, 1.0, 256.0))
                .option(createDoubleOption("dreamCatcherRangeY", 5.0, () -> config.dreamCatcherRangeY, v -> config.dreamCatcherRangeY = v, 1.0, 256.0))
                .option(createDoubleOption("dreamCatcherRangeZ", 8.0, () -> config.dreamCatcherRangeZ, v -> config.dreamCatcherRangeZ = v, 1.0, 256.0))
                .option(createIntOption("dreamCatcherRegenerationDuration", 300, () -> config.dreamCatcherRegenerationDuration, v -> config.dreamCatcherRegenerationDuration = v, 0, 100000))
                .option(createIntOption("dreamCatcherRegenerationAmplifier", 2, () -> config.dreamCatcherRegenerationAmplifier, v -> config.dreamCatcherRegenerationAmplifier = v, 0, 255))
                .option(createIntOption("dreamCatcherStrengthDuration", 1800, () -> config.dreamCatcherStrengthDuration, v -> config.dreamCatcherStrengthDuration = v, 0, 100000))
                .option(createIntOption("dreamCatcherStrengthAmplifier", 0, () -> config.dreamCatcherStrengthAmplifier, v -> config.dreamCatcherStrengthAmplifier = v, 0, 255))
                .option(createIntOption("dreamCatcherDrowsyDuration", 10, () -> config.dreamCatcherDrowsyDuration, v -> config.dreamCatcherDrowsyDuration = v, 0, 100000))
                .build());

        itemsBlocks.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.eggple"))
                .option(createIntOption("eggpleFourHatchChance", 160, () -> config.eggpleFourHatchChance, v -> config.eggpleFourHatchChance = v, 1, 10000))
                .build());

        itemsBlocks.group(dev.isxander.yacl3.api.OptionGroup.createBuilder()
                .name(Component.translatable("config.etcetera.group.blocks"))
                .option(createIntOption("coppertapDripChance", 5, () -> config.coppertapDripChance, v -> config.coppertapDripChance = v, 1, 10000))
                .option(createIntOption("coppertapFillCauldronChance", 7, () -> config.coppertapFillCauldronChance, v -> config.coppertapFillCauldronChance = v, 1, 10000))
                .option(createIntOption("rubberCauldronSolidifyChance", 7, () -> config.rubberCauldronSolidifyChance, v -> config.rubberCauldronSolidifyChance = v, 1, 10000))
                .option(createIntOption("crumblingStoneInverseChanceStepOn", 25, () -> config.crumblingStoneInverseChanceStepOn, v -> config.crumblingStoneInverseChanceStepOn = v, 1, 10000))
                .option(createIntOption("cottonGrowthSlowdown", 3, () -> config.cottonGrowthSlowdown, v -> config.cottonGrowthSlowdown = v, 1, 10000))
                .build());

        return builder.category(integrations.build())
                .category(mobs.build())
                .category(itemsBlocks.build())
                .build().generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("config.etcetera.option." + name))
                .description(OptionDescription.of(Component.translatable("config.etcetera.option." + name + ".desc")))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }

    private static Option<Integer> createIntOption(String name, int defaultValue, Supplier<Integer> getter, Consumer<Integer> setter, int min, int max) {
        return Option.<Integer>createBuilder()
                .name(Component.translatable("config.etcetera.option." + name))
                .description(OptionDescription.of(Component.translatable("config.etcetera.option." + name + ".desc")))
                .binding(defaultValue, getter, setter)
                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(min).max(max))
                .build();
    }

    private static Option<Double> createDoubleOption(String name, double defaultValue, Supplier<Double> getter, Consumer<Double> setter, double min, double max) {
        return Option.<Double>createBuilder()
                .name(Component.translatable("config.etcetera.option." + name))
                .description(OptionDescription.of(Component.translatable("config.etcetera.option." + name + ".desc")))
                .binding(defaultValue, getter, setter)
                .controller(opt -> DoubleFieldControllerBuilder.create(opt).min(min).max(max))
                .build();
    }
}
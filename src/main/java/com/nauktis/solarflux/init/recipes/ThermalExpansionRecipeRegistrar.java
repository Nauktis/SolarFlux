package com.nauktis.solarflux.init.recipes;

import com.google.common.base.Preconditions;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.init.ModBlocks;
import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ThermalExpansionRecipeRegistrar extends VanillaRecipeRegistrar {

    @Override
    protected void registerCell1Recipe() {
        if (!addSmelterRecipe(8000, new ItemStack(ModItems.mMirror), new ItemStack(Items.dye, 1, 4), new ItemStack(ModItems.mSolarCell1))) {
            super.registerCell1Recipe();
        }
    }

    @Override
    protected void registerCell2Recipe() {
        if (!addSmelterRecipe(8000, new ItemStack(ModItems.mSolarCell1), new ItemStack(Items.clay_ball, 16), new ItemStack(ModItems.mSolarCell2))) {
            super.registerCell2Recipe();
        }
    }

    @Override
    protected void registerCell3Recipe() {
        final Fluid glowstone = FluidRegistry.getFluid("glowstone");
        if (glowstone == null || !addTransposerRecipe(8000, new ItemStack(ModItems.mSolarCell2), new ItemStack(ModItems.mSolarCell3), new FluidStack(glowstone, 2000))) {
            super.registerCell3Recipe();
        }
    }

    @Override
    protected void registerCell4Recipe() {
        if (!addSmelterRecipe(8000, new ItemStack(ModItems.mSolarCell3), new ItemStack(Items.blaze_rod, 2), new ItemStack(ModItems.mSolarCell4))) {
            super.registerCell4Recipe();
        }
    }

    @Override
    protected void registerSolarPanel1() {
        try {
            ItemStack powerCoilGold = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "powerCoilGold", 1));
            GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.getSolarPanels().get(1), "sss", "scs", "sss", 's', ModBlocks.getSolarPanels().get(0), 'c', powerCoilGold));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel1();
        }
    }

    @Override
    protected void registerSolarPanel2() {
        try {
            ItemStack frameMachineBasic = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineBasic", 1));
            ItemStack cellBasic = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameCellBasic", 1));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            ModBlocks.getSolarPanels().get(2),
                            "ppp",
                            "scs",
                            "sbs",
                            'p',
                            ModItems.mSolarCell1,
                            's',
                            ModBlocks.getSolarPanels().get(1),
                            'c',
                            frameMachineBasic,
                            'b',
                            cellBasic));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel2();
        }
    }

    @Override
    protected void registerSolarPanel3() {
        try {
            ItemStack frameMachineHardened = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineHardened", 1));
            ItemStack cellHardened = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameCellHardened", 1));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            ModBlocks.getSolarPanels().get(3),
                            "ppp",
                            "scs",
                            "sbs",
                            'p',
                            ModItems.mSolarCell2,
                            's',
                            ModBlocks.getSolarPanels().get(2),
                            'c',
                            frameMachineHardened,
                            'b',
                            cellHardened));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel3();
        }
    }

    @Override
    protected void registerSolarPanel4() {
        try {
            ItemStack frameMachineReinforced = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineReinforced", 1));
            ItemStack cellReinforced = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameCellReinforcedFull", 1));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            ModBlocks.getSolarPanels().get(4),
                            "ppp",
                            "scs",
                            "sbs",
                            'p',
                            ModItems.mSolarCell3,
                            's',
                            ModBlocks.getSolarPanels().get(3),
                            'c',
                            frameMachineReinforced,
                            'b',
                            cellReinforced));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel4();
        }
    }

    @Override
    protected void registerSolarPanel5() {
        try {
            ItemStack frameMachineResonant = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineResonant", 1));
            ItemStack cellResonant = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameCellResonantFull", 1));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            ModBlocks.getSolarPanels().get(5),
                            "ppp",
                            "scs",
                            "sbs",
                            'p',
                            ModItems.mSolarCell4,
                            's',
                            ModBlocks.getSolarPanels().get(4),
                            'c',
                            frameMachineResonant,
                            'b',
                            cellResonant));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel5();
        }
    }

    @Override
    protected void registerSolarPanel(int pTier) {
        try {
            ItemStack powerCoilElectrum = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "powerCoilElectrum", 1));
            ItemStack cellResonant = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameCellResonantFull", 1));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            ModBlocks.getSolarPanels().get(pTier),
                            "ece",
                            "sbs",
                            "ece",
                            'e',
                            ModItems.mSolarCell4,
                            's',
                            ModBlocks.getSolarPanels().get(pTier - 1),
                            'c',
                            powerCoilElectrum,
                            'b',
                            cellResonant));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerSolarPanel(pTier);
        }
    }

    @Override
    protected void registerUpgradeTransferRate() {
        try {
            ItemStack powerCoilGold = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "powerCoilGold", 1));
            GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mUpgradeTransferRate, "www", "cuc", "www", 'w', "plankWood", 'u', ModItems.mUpgradeBlank, 'c', powerCoilGold));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerUpgradeTransferRate();
        }
    }

    @Override
    protected void registerUpgradeCapacity() {
        try {
            ItemStack cellBasic = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "cellBasic", 1));
            GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mUpgradeCapacity, " r ", "rur", "rcr", 'r', "dustRedstone", 'u', ModItems.mUpgradeBlank, 'c', cellBasic));
        } catch (NullPointerException e) {
            SolarFluxMod.log.info("Failed to register %s recipe. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
            e.printStackTrace();
            super.registerUpgradeCapacity();
        }
    }

    private boolean addSmelterRecipe(int pEnergy, ItemStack pPrimaryInput, ItemStack pSecondaryInput, ItemStack pOutput) {
        try {
            Class<?> klass = Class.forName("cofh.api.modhelpers.ThermalExpansionHelper");
            Method method = klass.getMethod("addSmelterRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class);
            method.invoke(null, pEnergy, pPrimaryInput, pSecondaryInput, pOutput);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            SolarFluxMod.log.info("Failed to register %s smelter recipe.", Reference.THERMAL_EXPANSION_MOD_ID);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addTransposerRecipe(int pEnergy, ItemStack pInput, ItemStack pOutput, FluidStack pFluid) {
        try {
            Class<?> klass = Class.forName("cofh.api.modhelpers.ThermalExpansionHelper");
            Method method = klass.getMethod("addTransposerFill", int.class, ItemStack.class, ItemStack.class, FluidStack.class, boolean.class);
            method.invoke(null, pEnergy, pInput, pOutput, pFluid, false);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
package com.nauktis.solarflux.init.recipes;

import com.google.common.collect.Lists;
import com.nauktis.solarflux.init.ModBlocks;
import com.nauktis.solarflux.init.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class VanillaRecipeRegistrar extends AbstractRecipeRegistrar {
    @Override
    public void registerRecipes() {
        registerCellRecipes();
        registerSolarPanelRecipes();
        registerUpgradeRecipes();
    }

    protected void registerCellRecipes() {
        registerMirrorRecipe();
        registerCell1Recipe();
        registerCell2Recipe();
        registerCell3Recipe();
        registerCell4Recipe();
    }

    protected void registerSolarPanelRecipes() {
        if (ModBlocks.getSolarPanels().size() > 0) {
            registerSolarPanel0();
        }
        if (ModBlocks.getSolarPanels().size() > 1) {
            registerSolarPanel1();
        }
        if (ModBlocks.getSolarPanels().size() > 2) {
            registerSolarPanel2();
        }
        if (ModBlocks.getSolarPanels().size() > 3) {
            registerSolarPanel3();
        }
        if (ModBlocks.getSolarPanels().size() > 4) {
            registerSolarPanel4();
        }
        if (ModBlocks.getSolarPanels().size() > 5) {
            registerSolarPanel5();
        }
        for (int tier = 6; tier < ModBlocks.getSolarPanels().size(); ++tier) {
            registerSolarPanel(tier);
        }
    }

    protected void registerUpgradeRecipes() {
        if (ModItems.mUpgradeBlank != null) {
            registerUpgradeBlank();
        }
        if (ModItems.mUpgradeEfficiency != null) {
            registerUpgradeEfficiency();
        }
        if (ModItems.mUpgradeLowLight != null) {
            registerUpgradeLowLight();
        }
        if (ModItems.mUpgradeTraversal != null) {
            registerUpgradeTraversal();
        }
        if (ModItems.mUpgradeTransferRate != null) {
            registerUpgradeTransferRate();
        }
        if (ModItems.mUpgradeCapacity != null) {
            registerUpgradeCapacity();
        }
    }

    protected void registerMirrorRecipe() {
        List<String> metals = Lists.newArrayList("ingotIron");
        if (knowsOre("ingotTin")) {
            metals.add("ingotTin");
        }
        if (knowsOre("ingotAluminum")) {
            metals.add("ingotAluminum");
        }
        if (knowsOre("ingotAluminium")) {
            metals.add("ingotAluminium");
        }
        for (String metal : metals) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mMirror, 2), "ggg", " i ", 'g', "blockGlass", 'i', metal));
        }
    }

    protected void registerCell1Recipe() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell1, "ggg", "lll", "mmm", 'g', "blockGlass", 'l', "gemLapis", 'm', ModItems.mMirror));
    }

    protected void registerCell2Recipe() {
        //@formatter:off
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell2, "clc", "lcl", "msm", 'c', Items.clay_ball, 'l', "gemLapis", 'm', ModItems.mMirror, 's', ModItems.mSolarCell1));
        //@formatter:on
    }

    protected void registerCell3Recipe() {
        //@formatter:off
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell3, "ggg", "lll", "oco", 'g', "blockGlass", 'l', "dustGlowstone", 'o', Blocks.obsidian, 'c', ModItems.mSolarCell2));
        //@formatter:on
    }

    protected void registerCell4Recipe() {
        //@formatter:off
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell4, "bbb", "gdg", "qcq", 'b', Items.blaze_rod, 'g', "dustGlowstone", 'd', "blockDiamond", 'q', "blockQuartz", 'c', ModItems.mSolarCell3));
        //@formatter:on
    }

    protected void registerSolarPanel0() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.getSolarPanels().get(0), "mmm", "prp", "ppp", 'm', ModItems.mMirror, 'p', "plankWood", 'r', "dustRedstone"));
    }

    protected void registerSolarPanel1() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.getSolarPanels().get(1), "sss", "sps", "sss", 's', ModBlocks.getSolarPanels().get(0), 'p', Blocks.piston));
    }

    protected void registerSolarPanel2() {
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
                        Items.repeater,
                        'b',
                        getFirstOreAvailable("blockCopper", "blockIron")));
    }

    protected void registerSolarPanel3() {
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
                        Items.clock,
                        'b',
                        "blockIron"));
    }

    protected void registerSolarPanel4() {
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
                        Blocks.redstone_lamp,
                        'b',
                        "blockGold"));
    }

    protected void registerSolarPanel5() {
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
                        Blocks.redstone_lamp,
                        'b',
                        "blockDiamond"));
    }

    protected void registerSolarPanel(int pTier) {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        ModBlocks.getSolarPanels().get(pTier),
                        "ccc",
                        "sbs",
                        "ccc",
                        'c',
                        ModItems.mSolarCell4,
                        's',
                        ModBlocks.getSolarPanels().get(pTier - 1),
                        'b',
                        "blockDiamond"));
    }

    protected void registerUpgradeBlank() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mUpgradeBlank, " c ", "cmc", " c ", 'c', "cobblestone", 'm', ModItems.mMirror));
    }

    protected void registerUpgradeEfficiency() {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(ModItems.mUpgradeEfficiency, " c ", "cuc", " s ", 'c', ModItems.mSolarCell1, 'u', ModItems.mUpgradeBlank, 's', ModItems.mSolarCell2));
    }

    protected void registerUpgradeLowLight() {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(ModItems.mUpgradeLowLight, "ggg", "lul", "ggg", 'g', "blockGlass", 'u', ModItems.mUpgradeBlank, 'l', "dustGlowstone"));
    }

    protected void registerUpgradeTraversal() {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        ModItems.mUpgradeTraversal,
                        "i i",
                        "rur",
                        "i i",
                        'i',
                        getFirstOreAvailable("ingotAluminum", "ingotAluminium", "ingotTin", "ingotIron"),
                        'u',
                        ModItems.mUpgradeBlank,
                        'r',
                        "dustRedstone"));
    }

    protected void registerUpgradeTransferRate() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mUpgradeTransferRate, "rrr", "gug", "rrr", 'r', "dustRedstone", 'u', ModItems.mUpgradeBlank, 'g', "ingotGold"));
    }

    protected void registerUpgradeCapacity() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mUpgradeCapacity, " r ", "rur", "rcr", 'r', "dustRedstone", 'u', ModItems.mUpgradeBlank, 'c', "blockRedstone"));
    }
}

package com.nauktis.solarflux.blocks;

import cofh.api.energy.IEnergyProvider;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.nauktis.core.inventory.BaseInventory;
import com.nauktis.core.tileentity.BaseModTileEntitySynced;
import com.nauktis.core.utility.Utils;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.modules.EnergySharingModule;
import com.nauktis.solarflux.blocks.modules.ITileEntityModule;
import com.nauktis.solarflux.blocks.modules.SimpleEnergyDispenserModule;
import com.nauktis.solarflux.blocks.modules.TraversalEnergyDispenserModule;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.config.TierConfiguration;
import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.items.UpgradeItem;
import com.nauktis.solarflux.reference.NBTConstants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class SolarPanelTileEntity extends BaseModTileEntitySynced implements IInventory, IEnergyProvider {
    public static final int INVENTORY_SIZE = 5;
    public static final Range<Integer> UPGRADE_SLOTS = Range.closedOpen(0, INVENTORY_SIZE);
    /**
     * Random number to avoid having all panels performing costly operations during the same tick.
     */
    private final short mTickShift;
    private final StatefulEnergyStorage mEnergyStorage;
    /**
     * Index of this tier of SolarPanel.
     */
    private int mTierIndex;
    private ITileEntityModule mEnergySharingModule;
    private ITileEntityModule mEnergyDispenserModule = new SimpleEnergyDispenserModule(this);
    /**
     * The amount of RF currently generated per tick.
     */
    private int mCurrentEnergyGeneration;

    /**
     * The amount of light that the solar panel can see.
     * This is only updated on the server and is sent to the client.
     */
    private float mSunIntensity;

    private BaseInventory mInventory = new BaseInventory("Solar Panel", INVENTORY_SIZE);
    private Map<Item, Integer> mUpgradeCache = Maps.newHashMap();

    public SolarPanelTileEntity() {
        // An empty constructor is always required as it is used when loading a TileEntity from NBT.
        this(0);
    }

    public SolarPanelTileEntity(int pTierIndex) {
        mTierIndex = pTierIndex;
        mTickShift = (short) Utils.RANDOM.nextInt(5 * 20);
        mEnergyStorage = new StatefulEnergyStorage(
                ModConfiguration.getTierConfiguration(mTierIndex).getCapacity(),
                ModConfiguration.getTierConfiguration(mTierIndex).getMaximumEnergyTransfer());

        // Add an energy sharing module if the config says so.
        if (ModConfiguration.doesAutoBalanceEnergy()) {
            mEnergySharingModule = new EnergySharingModule(this);
        }
    }

    public TierConfiguration getTierConfiguration() {
        return ModConfiguration.getTierConfigurations().get(mTierIndex);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        generateEnergy();

        if (isServer()) {
            if (atTickRate(20)) {
                updateCurrentEnergyGeneration();
            }

            mEnergyDispenserModule.tick();

            if (mEnergySharingModule != null) {
                mEnergySharingModule.tick();
            }
        }
    }

    /**
     * Returns the internal RF storage of the SolarPanel.
     */
    public StatefulEnergyStorage getEnergyStorage() {
        return mEnergyStorage;
    }

    public int getCurrentEnergyGeneration() {
        return mCurrentEnergyGeneration;
    }

    /**
     * Updates the amount of RF currently generated per tick.
     * This must only be used on the client to set the value received by the server.
     */
    public void setCurrentEnergyGeneration(int pCurrentEnergyGeneration) {
        mCurrentEnergyGeneration = pCurrentEnergyGeneration;
    }

    public int getMaximumEnergyGeneration() {
        return getTierConfiguration().getMaximumEnergyGeneration();
    }

    /**
     * Updates the current amount of RF generated per tick.
     */
    private void updateCurrentEnergyGeneration() {
        computeSunIntensity();

        double energyGeneration = getMaximumEnergyGeneration() * mSunIntensity;
        if (getUpgradeCount(ModItems.mUpgradeLowLight) > 0) {
            energyGeneration += getMaximumEnergyGeneration() * (-1.1 * mSunIntensity + 0.45 + 0.05 * Math.pow(
                    getUpgradeCount(ModItems.mUpgradeLowLight),
                    0.8));
        }

        energyGeneration *= (1 + ModConfiguration.getEfficiencyUpgradeIncrease() * Math.pow(
                getUpgradeCount(ModItems.mUpgradeEfficiency),
                ModConfiguration.getEfficiencyUpgradeReturnsToScale()));

        mCurrentEnergyGeneration = (int) Math.round(energyGeneration);
    }

    public float getSunIntensity() {
        return mSunIntensity;
    }

    /**
     * This must only be used on the client to set the value received by the server.
     */
    public void setSunIntensity(float pSunIntensity) {
        mSunIntensity = pSunIntensity;
    }

    /**
     * Compute the intensity of the sun.
     */
    private void computeSunIntensity() {
        if (worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            // Intensity based on the position of the sun.
            float multiplicator = 1.5f;
            float displacement = 1.2f;
            // Celestial angle == 0 at zenith.
            float celestialAngleRadians = worldObj.getCelestialAngleRadians(1.0f);
            if (celestialAngleRadians > Math.PI) {
                celestialAngleRadians = (2 * 3.141592f - celestialAngleRadians);
            }

            mSunIntensity = multiplicator * MathHelper.cos(celestialAngleRadians / displacement);
            mSunIntensity = Math.max(0, mSunIntensity);
            mSunIntensity = Math.min(1, mSunIntensity);

            if (mSunIntensity > 0) {
                if (worldObj.isRaining()) {
                    mSunIntensity *= ModConfiguration.getRainGenerationFactor();
                }
                if (worldObj.isThundering()) {
                    mSunIntensity *= ModConfiguration.getThunderGenerationFactor();
                }
            }
        } else {
            mSunIntensity = 0;
        }
    }

    protected void generateEnergy() {
        if (mCurrentEnergyGeneration > 0) {
            getEnergyStorage().receiveEnergy(mCurrentEnergyGeneration, false);
        }
    }

    /**
     * Goes through the inventory and counts the number of each upgrade.
     */
    private void refreshUpgradeCache() {
        mUpgradeCache.clear();
        for (int i = 0; i < getSizeInventory(); ++i) {
            ItemStack itemStack = getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof UpgradeItem) {
                if (mUpgradeCache.containsKey(itemStack.getItem())) {
                    mUpgradeCache.put(itemStack.getItem(), itemStack.stackSize + mUpgradeCache.get(itemStack.getItem()));
                } else {
                    mUpgradeCache.put(itemStack.getItem(), itemStack.stackSize);
                }
            }
        }

        // Do we have any upgrade for traversal?
        if (getUpgradeCount(ModItems.mUpgradeTraversal) > 0 && mEnergyDispenserModule instanceof SimpleEnergyDispenserModule) {
            mEnergyDispenserModule = new TraversalEnergyDispenserModule(this);
        } else if (getUpgradeCount(ModItems.mUpgradeTraversal) == 0 && mEnergyDispenserModule instanceof TraversalEnergyDispenserModule) {
            mEnergyDispenserModule = new SimpleEnergyDispenserModule(this);
        }

        // Apply effect of transfer rate upgrade.
        getEnergyStorage().setMaxTransfer(
                (int) (ModConfiguration.getTierConfiguration(mTierIndex).getMaximumEnergyTransfer() *
                        (1 + ModConfiguration.getTransferRateUpgradeIncrease() * Math.pow(
                                getUpgradeCount(ModItems.mUpgradeTransferRate),
                                ModConfiguration.getTransferRateUpgradeReturnsToScale()))));

        // Apply effect of capacity upgrade
        getEnergyStorage().setMaxEnergyStored(
                (int) (ModConfiguration.getTierConfiguration(mTierIndex).getCapacity() *
                        (1 + ModConfiguration.getCapacityUpgradeIncrease() * Math.pow(
                                getUpgradeCount(ModItems.mUpgradeCapacity),
                                ModConfiguration.getCapacityUpgradeReturnsToScale()))));
    }

    public int getTotalUpgradeInstalled() {
        int count = 0;
        for (int value : mUpgradeCache.values()) {
            count += value;
        }
        return count;
    }

    /**
     * Returns the number of upgrade that can still be added.
     */
    public int additionalUpgradeAllowed(ItemStack pItemStack) {
        if (pItemStack != null) {
            Item item = pItemStack.getItem();
            if (item instanceof UpgradeItem) {
                UpgradeItem upgrade = (UpgradeItem) item;
                return upgrade.getMaximumPerSolarPanel() - getUpgradeCount(upgrade);
            }
        }
        return 0;
    }

    public int getUpgradeCount(Item pItem) {
        // TODO return Math.min count and max allowed
        Integer count = mUpgradeCache.get(pItem);
        return count == null ? 0 : count;
    }

    @Override
    protected void loadDataFromNBT(NBTTagCompound pNBT) {
        super.loadDataFromNBT(pNBT);

        mTierIndex = pNBT.getInteger(NBTConstants.TIER_INDEX);

        // Update Energy Storage with Tier Configuration
        mEnergyStorage.setMaxEnergyStored(ModConfiguration.getTierConfiguration(mTierIndex).getCapacity());
        mEnergyStorage.setMaxTransfer(ModConfiguration.getTierConfiguration(mTierIndex).getMaximumEnergyTransfer());

        // Restore inventory and force an update of the upgrade cache.
        mInventory.readFromNBT(pNBT);
        markDirty();

        mEnergyStorage.readFromNBT(pNBT);
    }

    @Override
    protected void addDataToNBT(NBTTagCompound pNBT) {
        super.addDataToNBT(pNBT);
        pNBT.setInteger(NBTConstants.TIER_INDEX, mTierIndex);
        mInventory.writeToNBT(pNBT);
        mEnergyStorage.writeToNBT(pNBT);
    }

    /**
     * Returns true if this tick is a good one to perform an operation that is desired to run ar the provided rate.
     */
    private boolean atTickRate(int pDesiredTickRate) {
        return (getWorldObj().getTotalWorldTime() + mTickShift) % pDesiredTickRate == 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection pFrom) {
        return pFrom != ForgeDirection.UP;
    }

    @Override
    public int extractEnergy(ForgeDirection pFrom, int pMaxExtract, boolean pSimulate) {
        return getEnergyStorage().extractEnergy(getEnergyStorage().getMaxExtract(), pSimulate);
    }

    public int getEnergyStored() {
        return getEnergyStored(ForgeDirection.DOWN);
    }

    public void setEnergyStored(int pEnergy) {
        getEnergyStorage().setEnergyStored(pEnergy);
    }

    public int getScaledEnergyStoredFraction(int pScale) {
        // Little trick to avoid integer overflow.
        long v = pScale;
        v *= getEnergyStored();
        v /= getMaxEnergyStored();
        checkState(v <= Integer.MAX_VALUE, "Integer overflow");
        return (int) v;
    }

    public int getPercentageEnergyStored() {
        return getScaledEnergyStoredFraction(100);
    }

    @Override
    public int getEnergyStored(ForgeDirection pFrom) {
        return getEnergyStorage().getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return getMaxEnergyStored(ForgeDirection.DOWN);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection pFrom) {
        return getEnergyStorage().getMaxEnergyStored();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("hash", hashCode())
                .add("MaxProduction", getMaximumEnergyGeneration())
                .add("energyStorage", getEnergyStorage())
                .toString();
    }

    public BaseInventory getInventory() {
        return mInventory;
    }

    @Override
    public int getSizeInventory() {
        return mInventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int pSlotIndex) {
        return mInventory.getStackInSlot(pSlotIndex);
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrementAmount) {
        return mInventory.decrStackSize(pSlotIndex, pDecrementAmount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotIndex) {
        return mInventory.getStackInSlotOnClosing(pSlotIndex);
    }

    @Override
    public void setInventorySlotContents(int pSlotIndex, ItemStack pItemStack) {
        mInventory.setInventorySlotContents(pSlotIndex, pItemStack);
    }

    @Override
    public String getInventoryName() {
        return mInventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return mInventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return mInventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer pEntityPlayer) {
        return mInventory.isUseableByPlayer(pEntityPlayer);
    }

    @Override
    public void openInventory() {
        mInventory.openInventory();
    }

    @Override
    public void closeInventory() {
        mInventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int pSlotIndex, ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof UpgradeItem) {
            if (additionalUpgradeAllowed(pItemStack) >= pItemStack.stackSize) {
                return mInventory.isItemValidForSlot(pSlotIndex, pItemStack);
            }
        }
        return false;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        refreshUpgradeCache();
    }
}

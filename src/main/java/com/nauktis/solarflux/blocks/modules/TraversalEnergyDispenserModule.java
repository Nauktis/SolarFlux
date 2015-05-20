package com.nauktis.solarflux.blocks.modules;

import cofh.api.energy.IEnergyReceiver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nauktis.core.utility.BlockPosition;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.init.ModItems;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TraversalEnergyDispenserModule extends IntervalTileEntityModule<SolarPanelTileEntity> {
    private final List<BlockPosition> mTargets = Lists.newArrayList();
    private final Set<BlockPosition> mVisitedBlocks = Sets.newHashSet();
    private final LinkedList<BlockPosition> mBlocksToVisit = Lists.newLinkedList();
    private int mTargetStartingIndex;
    private int mDirectNeighborDiscovered;

    public TraversalEnergyDispenserModule(SolarPanelTileEntity pTileEntity) {
        super(pTileEntity, (short) ModConfiguration.getTraversalUpgradeUpdateRate());
    }

    @Override
    public void tick() {
        sendEnergyToTargets();
        super.tick();
    }

    @Override
    protected void doTick() {
        if (searchFinished()) {
            // We have our new targets.
            mTargets.clear();
            mTargets.addAll(mVisitedBlocks);
            // Reset the search.
            mVisitedBlocks.clear();
            discoverNeighbors(new BlockPosition(getTileEntity().x(), getTileEntity().y(), getTileEntity().z()));
            mDirectNeighborDiscovered = mBlocksToVisit.size();
        }

        // If we reached the max number of target we stop the search.
        if (mVisitedBlocks.size() >= mDirectNeighborDiscovered + getMaximumExtraTargets()) {
            mBlocksToVisit.clear();
        }

        progressSearch();
    }

    private void progressSearch() {
        if (!searchFinished()) {
            // TODO use pop for DFS
            BlockPosition position = mBlocksToVisit.remove();
            mVisitedBlocks.add(position);
            discoverNeighbors(position);
        }
    }

    private boolean searchFinished() {
        return mBlocksToVisit.isEmpty();
    }

    /**
     * Discover blocks that should be traveled around the location provided.
     */
    private void discoverNeighbors(BlockPosition pPosition) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            BlockPosition neighbor = pPosition.move(direction);
            if (!mVisitedBlocks.contains(neighbor) && shouldTraverse(neighbor)) {
                mBlocksToVisit.add(neighbor);
            }
        }
    }

    private boolean shouldTraverse(BlockPosition pPosition) {
        return getTileEntity().getWorldObj().getTileEntity(pPosition.x(), pPosition.y(), pPosition.z()) instanceof IEnergyReceiver;
    }

    private void sendEnergyToTargets() {
        if (mTargets.size() > 0 && getTileEntity().getEnergyStored() > 0) {
            for (int i = 0; i < mTargets.size(); ++i) {
                sendEnergyToTarget(mTargets.get((mTargetStartingIndex + i) % mTargets.size()));
            }
            mTargetStartingIndex = (mTargetStartingIndex + 1) % mTargets.size();
        }
    }

    private void sendEnergyToTarget(BlockPosition pPosition) {
        TileEntity tile = getTileEntity().getWorldObj().getTileEntity(pPosition.x(), pPosition.y(), pPosition.z());
        if (tile instanceof IEnergyReceiver) {
            IEnergyReceiver receiver = (IEnergyReceiver) tile;
            getTileEntity().getEnergyStorage().sendMaxTo(receiver, pPosition.getDirection().getOpposite());
        }
    }

    /**
     * Returns the maximum amount of target that can be found in addition to the 4 neighbors of the block.
     */
    public int getMaximumExtraTargets() {
        return getTileEntity().getUpgradeCount(ModItems.mUpgradeTraversal) * ModConfiguration.getTraversalUpgradeIncrease();
    }
}

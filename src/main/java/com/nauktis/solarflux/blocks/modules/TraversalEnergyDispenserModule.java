package com.nauktis.solarflux.blocks.modules;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nauktis.core.utility.BlockPosition;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.init.ModItems;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.LinkedList;
import java.util.Set;

public class TraversalEnergyDispenserModule extends SimpleEnergyDispenserModule {
    private final Set<BlockPosition> mVisitedBlocks = Sets.newHashSet();
    private final LinkedList<BlockPosition> mBlocksToVisit = Lists.newLinkedList();
    private int mDirectNeighborDiscovered;

    public TraversalEnergyDispenserModule(SolarPanelTileEntity pTileEntity) {
        super(pTileEntity);
    }

    protected void searchTargets() {
        if (searchFinished()) {
            // We have our new targets.
            getTargets().clear();
            getTargets().addAll(mVisitedBlocks);
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

    @Override
    protected int getTargetRefreshRate() {
        return ModConfiguration.getTraversalUpgradeUpdateRate();
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
            if (!mVisitedBlocks.contains(neighbor) && isValidTarget(neighbor)) {
                mBlocksToVisit.add(neighbor);
            }
        }
    }

    /**
     * Returns the maximum amount of target that can be found in addition to the 4 neighbors of the block.
     */
    public int getMaximumExtraTargets() {
        return getTileEntity().getUpgradeCount(ModItems.mUpgradeTraversal) * ModConfiguration.getTraversalUpgradeIncrease();
    }
}

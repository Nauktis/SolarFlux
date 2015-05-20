package com.nauktis.core.utility;

import com.google.common.base.Objects;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A Block position together with an optional direction.
 * The direction is not taken into account when comparing two BlockPositions.
 * This object is immutable.
 */
public final class BlockPosition {
    private final int mX;
    private final int mY;
    private final int mZ;
    private final ForgeDirection mDirection;

    public BlockPosition(int pX, int pY, int pZ) {
        this(pX, pY, pZ, null);
    }

    public BlockPosition(int pX, int pY, int pZ, ForgeDirection pDirection) {
        mX = pX;
        mY = pY;
        mZ = pZ;
        mDirection = pDirection;
    }

    public int x() {
        return mX;
    }

    public int y() {
        return mY;
    }

    public int z() {
        return mZ;
    }

    public ForgeDirection getDirection() {
        return mDirection;
    }

    public BlockPosition forward() {
        return new BlockPosition(mX + mDirection.offsetX, mY + mDirection.offsetY, mZ + mDirection.offsetZ, mDirection);
    }

    public BlockPosition move(ForgeDirection pDirection) {
        return new BlockPosition(mX + pDirection.offsetX, mY + pDirection.offsetY, mZ + pDirection.offsetZ, pDirection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockPosition that = (BlockPosition) o;
        return Objects.equal(mX, that.mX) &&
                Objects.equal(mY, that.mY) &&
                Objects.equal(mZ, that.mZ);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mX, mY, mZ);
    }

    @Override
    public String toString() {
        return String.format("[%s,%s,%s]", mX, mY, mZ);
    }
}

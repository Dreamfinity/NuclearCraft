package nc.tile.turbine;

import nc.capability.radiation.source.IRadiationSource;
import nc.multiblock.cuboidal.*;
import nc.multiblock.turbine.Turbine;

import javax.annotation.Nullable;

public abstract class TileTurbinePart extends TileCuboidalMultiblockPart<Turbine, ITurbinePart> implements ITurbinePart {
	
	public TileTurbinePart(CuboidalPartPositionType positionType) {
		super(Turbine.class, ITurbinePart.class, positionType);
	}
	
	@Override
	public Turbine createNewMultiblock() {
		return new Turbine(world);
	}
	
	public boolean isTransparent() {
		return false;
	}
	
	@Override
	public boolean isGoodForFrame(Turbine multiblock) {
		if (getPartPositionType().isGoodForFrame()) {
			if (isTransparent() && multiblock != null) {
				multiblock.shouldSpecialRenderRotor = true;
			}
			return true;
		}
		setStandardLastError(multiblock);
		return false;
	}
	
	@Override
	public boolean isGoodForSides(Turbine multiblock) {
		if (getPartPositionType().isGoodForWall()) {
			if (isTransparent() && multiblock != null) {
				multiblock.shouldSpecialRenderRotor = true;
			}
			return true;
		}
		setStandardLastError(multiblock);
		return false;
	}
	
	@Override
	public boolean isGoodForTop(Turbine multiblock) {
		if (getPartPositionType().isGoodForWall()) {
			if (isTransparent() && multiblock != null) {
				multiblock.shouldSpecialRenderRotor = true;
			}
			return true;
		}
		setStandardLastError(multiblock);
		return false;
	}
	
	@Override
	public boolean isGoodForBottom(Turbine multiblock) {
		if (getPartPositionType().isGoodForWall()) {
			if (isTransparent() && multiblock != null) {
				multiblock.shouldSpecialRenderRotor = true;
			}
			return true;
		}
		setStandardLastError(multiblock);
		return false;
	}
}

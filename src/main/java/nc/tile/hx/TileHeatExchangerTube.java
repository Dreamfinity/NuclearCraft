package nc.tile.hx;

import it.unimi.dsi.fastutil.objects.*;
import nc.item.ItemMultitool;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.*;
import nc.tile.IRayTraceLogic;
import nc.util.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.*;

public class TileHeatExchangerTube extends TileHeatExchangerPart implements IRayTraceLogic {
	
	public static final Object2DoubleMap<String> DYN_HEAT_TRANSFER_COEFFICIENT_MAP = new Object2DoubleOpenHashMap<>();
	public static final Object2DoubleMap<String> DYN_HEAT_RETENTION_MULT_MAP = new Object2DoubleOpenHashMap<>();
	
	protected String tubeType;
	
	public double heatTransferCoefficient;
	public double heatRetentionMult;
	
	public @Nonnull HeatExchangerTubeSetting[] settings = new HeatExchangerTubeSetting[] {HeatExchangerTubeSetting.CLOSED, HeatExchangerTubeSetting.CLOSED, HeatExchangerTubeSetting.CLOSED, HeatExchangerTubeSetting.CLOSED, HeatExchangerTubeSetting.CLOSED, HeatExchangerTubeSetting.CLOSED};
	
	public @Nullable Vec3d tubeFlow = null;
	public @Nullable Vec3d shellFlow = null;
	
	/**
	 * Don't use this constructor!
	 */
	public TileHeatExchangerTube() {
		super(CuboidalPartPositionType.INTERIOR);
	}
	
	public static abstract class Variant extends TileHeatExchangerTube {
		
		protected Variant(HeatExchangerTubeType type) {
			super(type.getName(), type.getHeatTransferCoefficient(), type.getHeatRetentionMult());
		}
	}
	
	public static class Copper extends Variant {
		
		public Copper() {
			super(HeatExchangerTubeType.COPPER);
		}
	}
	
	public static class HardCarbon extends Variant {
		
		public HardCarbon() {
			super(HeatExchangerTubeType.HARD_CARBON);
		}
	}
	
	public static class Thermoconducting extends Variant {
		
		public Thermoconducting() {
			super(HeatExchangerTubeType.THERMOCONDUCTING);
		}
	}
	
	public TileHeatExchangerTube(String tubeType, double heatTransferCoefficient, double heatRetentionMult) {
		this();
		this.tubeType = tubeType;
		this.heatTransferCoefficient = heatTransferCoefficient;
		this.heatRetentionMult = heatRetentionMult;
	}
	
	@Override
	public void onMachineAssembled(HeatExchanger multiblock) {
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}
	
	public void setTubeSettings(@Nonnull HeatExchangerTubeSetting[] settings) {
		System.arraycopy(settings, 0, this.settings, 0, 6);
	}
	
	public HeatExchangerTubeSetting getTubeSetting(@Nonnull EnumFacing side) {
		return settings[side.getIndex()];
	}
	
	public void setTubeSetting(@Nonnull EnumFacing side, HeatExchangerTubeSetting setting) {
		settings[side.getIndex()] = setting;
	}
	
	public void setTubeSettingOpen(@Nonnull EnumFacing side, boolean open) {
		int index = side.getIndex();
		settings[index] = HeatExchangerTubeSetting.of(open, settings[index].isBaffle());
	}
	
	public void setTubeSettingBaffle(@Nonnull EnumFacing side, boolean baffle) {
		int index = side.getIndex();
		settings[index] = HeatExchangerTubeSetting.of(settings[index].isOpen(), baffle);
	}
	
	public void toggleTubeSetting(@Nonnull EnumFacing side) {
		setTubeSetting(side, getTubeSetting(side).next());
		markDirtyAndNotify(true);
	}
	
	// IMultitoolLogic
	
	@Override
	public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World world, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!isMultiblockAssembled()) {
			boolean opposite = player.isSneaking();
			EnumFacing side = opposite ? facing.getOpposite() : facing;
			toggleTubeSetting(side);
			
			HeatExchangerTubeSetting setting = getTubeSetting(side);
			if (world.getTileEntity(pos.offset(side)) instanceof TileHeatExchangerTube other) {
				other.setTubeSetting(side.getOpposite(), setting);
				other.markDirtyAndNotify(true);
			}
			
			player.sendMessage(new TextComponentString(Lang.localize(opposite ? "nc.block.fluid_toggle_opposite" : "nc.block.fluid_toggle") + " " + setting.getTextColor() + Lang.localize("nc.block.exchanger_tube_fluid_side." + setting)));
			return true;
		}
		
		return super.onUseMultitool(multitool, player, world, facing, hitX, hitY, hitZ);
	}
	
	// IRayTraceLogic
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onPlayerMouseOver(EntityPlayerSP player, EnumFacing side, float partialTicks) {
		if (side == null) {
			return;
		}
		
		if (!ItemMultitool.isMultitool(player.getHeldItemMainhand())) {
			return;
		}
		
		Vec3d playerPos = NCRenderHelper.getPlayerPos(player, partialTicks);
		
		float r = 1F, g = 0F, b = 0F;
		
		GlStateManager.color(r, g, b);
		GlStateManager.glLineWidth(2);
		
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(-playerPos.x, -playerPos.y, -playerPos.z);
		
		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
		float p = 2F * NCRenderHelper.PIXEL, q = 1F - p;
		
		switch (player.isSneaking() ? side.getOpposite() : side) {
			case DOWN -> NCRenderHelper.renderFrame(x + p, y, z + p, q - p, p, q - p, r, g, b, 1F);
			case UP -> NCRenderHelper.renderFrame(x + p, y + q, z + p, q - p, p, q - p, r, g, b, 1F);
			case NORTH -> NCRenderHelper.renderFrame(x + p, y + p, z, q - p, q - p, p, r, g, b, 1F);
			case SOUTH -> NCRenderHelper.renderFrame(x + p, y + p, z + q, q - p, q - p, p, r, g, b, 1F);
			case WEST -> NCRenderHelper.renderFrame(x, y + p, z + p, p, q - p, q - p, r, g, b, 1F);
			case EAST -> NCRenderHelper.renderFrame(x + q, y + p, z + p, p, q - p, q - p, r, g, b, 1F);
		}
		
		Tessellator.getInstance().draw();
		
		GlStateManager.popMatrix();
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
	}
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		
		if (tubeType == null) {
			nbt.setDouble("heatTransferCoefficient", heatTransferCoefficient);
			nbt.setDouble("heatRetentionMult", heatRetentionMult);
		}
		else {
			nbt.setString("tubeType", tubeType);
		}
		
		byte[] byteSettings = new byte[6];
		for (int i = 0; i < 6; ++i) {
			byteSettings[i] = (byte) settings[i].ordinal();
		}
		nbt.setByteArray("settings", byteSettings);
		
		nbt.setBoolean("nullTubeFlow", tubeFlow == null);
		if (tubeFlow != null) {
			nbt.setDouble("tubeFlowX", tubeFlow.x);
			nbt.setDouble("tubeFlowY", tubeFlow.y);
			nbt.setDouble("tubeFlowZ", tubeFlow.z);
		}
		
		nbt.setBoolean("nullShellFlow", shellFlow == null);
		if (shellFlow != null) {
			nbt.setDouble("shellFlowX", shellFlow.x);
			nbt.setDouble("shellFlowY", shellFlow.y);
			nbt.setDouble("shellFlowZ", shellFlow.z);
		}
		
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		
		if (nbt.hasKey("heatTransferCoefficient") && nbt.hasKey("heatRetentionMult")) {
			heatTransferCoefficient = nbt.getDouble("heatTransferCoefficient");
			heatRetentionMult = nbt.getDouble("heatRetentionMult");
		}
		else if (nbt.hasKey("tubeType")) {
			tubeType = nbt.getString("tubeType");
			
			if (DYN_HEAT_TRANSFER_COEFFICIENT_MAP.containsKey(tubeType)) {
				heatTransferCoefficient = DYN_HEAT_TRANSFER_COEFFICIENT_MAP.getDouble(tubeType);
			}
			if (DYN_HEAT_RETENTION_MULT_MAP.containsKey(tubeType)) {
				heatRetentionMult = DYN_HEAT_RETENTION_MULT_MAP.getDouble(tubeType);
			}
		}
		
		if (nbt.hasKey("settings")) {
			settings = new HeatExchangerTubeSetting[6];
			byte[] byteSettings = nbt.getByteArray("settings");
			for (int i = 0; i < 6; ++i) {
				settings[i] = HeatExchangerTubeSetting.values()[byteSettings[i]];
			}
		}
		
		tubeFlow = nbt.getBoolean("nullTubeFlow") ? null : new Vec3d(nbt.getDouble("tubeFlowX"), nbt.getDouble("tubeFlowY"), nbt.getDouble("tubeFlowZ"));
		shellFlow = nbt.getBoolean("nullShellFlow") ? null : new Vec3d(nbt.getDouble("shellFlowX"), nbt.getDouble("shellFlowY"), nbt.getDouble("shellFlowZ"));
	}
}

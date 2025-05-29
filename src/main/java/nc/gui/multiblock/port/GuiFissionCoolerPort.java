package nc.gui.multiblock.port;

import nc.gui.GuiInfoTile;
import nc.gui.element.*;
import nc.network.gui.*;
import nc.network.tile.multiblock.port.FluidPortUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.fission.port.TileFissionCoolerPort;
import nc.tile.internal.fluid.Tank;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiFissionCoolerPort extends GuiInfoTile<TileFissionCoolerPort, FluidPortUpdatePacket, TileContainerInfo<TileFissionCoolerPort>> {
	
	public GuiFissionCoolerPort(Container inventory, EntityPlayer player, TileFissionCoolerPort tile, String textureLocation) {
		super(inventory, player, tile, textureLocation);
	}
	
	@Override
	public void renderTooltips(int mouseX, int mouseY) {
		drawFilteredFluidTooltip(tile.getTanks().get(0), tile.getFilterTanks().get(0), mouseX, mouseY, 40, 31, 24, 24);
		drawFilteredFluidTooltip(tile.getTanks().get(1), tile.getFilterTanks().get(1), mouseX, mouseY, 112, 31, 24, 24);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int fontColor = tile.getMultiblock() != null && tile.getMultiblock().isReactorOn ? -1 : 15641088;
		fontRenderer.drawString(guiName.get(), xSize / 2 - nameWidth.get() / 2, 6, fontColor);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		defaultStateAndBind();
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		Tank filterTank = tile.getFilterTanks().get(0);
		if (!filterTank.isEmpty()) {
			GuiFluidRenderer.renderGuiTank(filterTank.getFluid(), 1000, 1000, guiLeft + 40, guiTop + 31, zLevel, 24, 24, 127);
		}
		
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(0), guiLeft + 40, guiTop + 31, zLevel, 24, 24);
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(1), guiLeft + 112, guiTop + 31, zLevel, 24, 24);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}
	
	public void initButtons() {
		buttonList.add(new NCButton.ClearTank(0, guiLeft + 40, guiTop + 31, 24, 24));
		buttonList.add(new NCButton.ClearTank(1, guiLeft + 112, guiTop + 31, 24, 24));
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if (tile.getWorld().isRemote) {
			for (int i = 0; i < 2; ++i) {
				if (guiButton.id == i && NCUtil.isModifierKeyDown()) {
					(tile.getTanks().get(i).isEmpty() ? new ClearFilterTankPacket(tile, i) : new ClearTankPacket(tile, i)).sendToServer();
					return;
				}
			}
		}
	}
}

package nc.tile;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.*;

public interface IRayTraceLogic extends ITile {
	
	@SideOnly(Side.CLIENT)
	void onPlayerMouseOver(EntityPlayerSP player, EnumFacing side, float partialTicks);
}

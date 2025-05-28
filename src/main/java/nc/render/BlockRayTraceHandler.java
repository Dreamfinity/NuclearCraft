package nc.render;

import nc.tile.IRayTraceLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class BlockRayTraceHandler {
	
	@SubscribeEvent
	public void onClientTick(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.player;
		if (player != null) {
			RayTraceResult ray = mc.objectMouseOver;
			if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
				if (mc.world.getTileEntity(ray.getBlockPos()) instanceof IRayTraceLogic rayTraceTile) {
					rayTraceTile.onPlayerMouseOver(player, ray.sideHit, event.getPartialTicks());
				}
			}
		}
	}
}

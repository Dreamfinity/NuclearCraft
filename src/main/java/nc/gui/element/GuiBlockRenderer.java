/**
 * Massive thanks to CrazyPants, maker of EnderIO and related mods, for letting me use this code!
 */

package nc.gui.element;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

import javax.annotation.*;
import java.util.List;

public class GuiBlockRenderer {
	
	private static TextureAtlasSprite getTexture(IBlockState state, EnumFacing facing) {
		IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
		List<BakedQuad> quadList = ibakedmodel.getQuads(state, facing, 0L);
		TextureAtlasSprite sprite = quadList.isEmpty() ? ibakedmodel.getParticleTexture() : quadList.get(0).getSprite();
		return sprite == null ? getMissingSprite() : sprite;
	}
	
	private static @Nonnull TextureAtlasSprite getMissingSprite() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
	}
	
	public static void renderGuiBlock(@Nullable IBlockState state, EnumFacing facing, int x, int y, double zLevel, int width, int height) {
		if (state == null || facing == null) {
			return;
		}
		TextureAtlasSprite icon = getTexture(state, facing);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		GlStateManager.enableBlend();
		for (int i = 0; i < width; i += 16) {
			for (int j = 0; j < height; j += 16) {
				int drawWidth = Math.min(width - i, 16);
				int drawHeight = Math.min(height - j, 16);
				
				int drawX = x + i;
				int drawY = y + j;
				
				double minU = icon.getMinU();
				double maxU = icon.getMaxU();
				double minV = icon.getMinV();
				double maxV = icon.getMaxV();
				
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder buffer = tessellator.getBuffer();
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				double u = minU + (maxU - minU) * drawWidth / 16F, v = minV + (maxV - minV) * drawHeight / 16F;
				buffer.pos(drawX, drawY + drawHeight, 0).tex(minU, v).endVertex();
				buffer.pos(drawX + drawWidth, drawY + drawHeight, 0).tex(u, v).endVertex();
				buffer.pos(drawX + drawWidth, drawY, 0).tex(u, minV).endVertex();
				buffer.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
				tessellator.draw();
			}
		}
		GlStateManager.disableBlend();
	}
}

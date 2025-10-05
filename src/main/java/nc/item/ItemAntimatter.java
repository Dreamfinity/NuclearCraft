package nc.item;

import nc.NuclearCraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentTranslation;

public class ItemAntimatter extends ItemNC {

    public ItemAntimatter(String name, String... lines) {
        super("", name, lines);
    }

    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem == null) {
            return false;
        }

        if (!entityItem.onGround) {
            return false;
        }

        if (!NuclearCraft.enableAntiMatterExplosion) {
            // TODO (Veritaris): make firework effect here
            EntityPlayer playerWhoDropped = entityItem.worldObj.getPlayerEntityByName(entityItem.getThrower());
            if (playerWhoDropped != null) {
                playerWhoDropped.addChatMessage(new ChatComponentTranslation("message.nuclearcraft.antimatterExplosionDisabled"));
            }
            entityItem.setDead();
            return true;
        }

        int x = (int) Math.floor(entityItem.posX);
        int y = (int) Math.floor(entityItem.posY);
        int z = (int) Math.floor(entityItem.posZ);

        for (int i = -((int) (0.2D * NuclearCraft.explosionRadius)); i <= ((int) (0.2D * NuclearCraft.explosionRadius)); i++) {
            for (int j = -((int) (0.2D * NuclearCraft.explosionRadius)); j <= ((int) (0.2D * NuclearCraft.explosionRadius)); j++) {
                for (int k = -((int) (0.2D * NuclearCraft.explosionRadius)); k <= ((int) (0.2D * NuclearCraft.explosionRadius)); k++) {
                    if (i * i + j * j + k * k <= ((int) (0.2D * NuclearCraft.explosionRadius)) * ((int) (0.2D * NuclearCraft.explosionRadius)) && entityItem.worldObj.getBlock(x + i, y + j, z + k) != Blocks.bedrock) {
                        entityItem.worldObj.setBlockToAir(x + i, y + j, z + k);
                    }
                }
            }
        }
        entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "random.explode", 4.0F, 1.0F);
        entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "nc:shield2", 12.0F, 1.0F);
        entityItem.setDead();
        return true;
    }
}

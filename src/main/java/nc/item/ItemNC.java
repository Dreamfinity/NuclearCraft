package nc.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nc.NuclearCraft;
import nc.util.InfoNC;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class ItemNC extends Item {

    String[] info;
    String folders;
    String name;
    boolean tab;

    public ItemNC(String folder, String nam, String... lines) {
        super();
        String[] strings = new String[lines.length];
        System.arraycopy(lines, 0, strings, 0, lines.length);
        info = strings;
        folders = folder;
        name = nam;
        setUnlocalizedName(nam);
        tab = true;
    }

    public ItemNC(String folder, String nam, boolean showInCreativeTab, String... lines) {
        super();
        String[] strings = new String[lines.length];
        System.arraycopy(lines, 0, strings, 0, lines.length);
        info = strings;
        folders = folder;
        name = nam;
        setUnlocalizedName(nam);
        tab = showInCreativeTab;
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return tab ? NuclearCraft.tabNC : null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("nc:" + folders + (Objects.equals(folders, "") ? "" : "/") + name);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean whatIsThis) {
        super.addInformation(itemStack, player, list, whatIsThis);
        if (info.length > 0) InfoNC.infoFull(list, info);
    }
}

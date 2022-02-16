package com.overlast.item;

import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import java.util.ArrayList;


public class ItemEvoDevice extends Item {

    /*
     * A nice handheld fan that instantly cools the player down.
     */

    private final String name = "evo_device";

    public ItemEvoDevice() {

        // Set registry and unlocalized name.
        this.setUnlocalizedName(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
        // Basic properties.
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        // Server side.
        if (!world.isRemote) {
        }
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

        // Server side.
        if (!world.isRemote && entityLiving instanceof EntityPlayerMP) {

            // Basic variables.
            EntityPlayerMP player = (EntityPlayerMP) entityLiving;

            SRPWorldData data = SRPWorldData.get(world);

            ArrayList<Integer> nodesX = data.getNodes("x");
            ArrayList<Integer> nodesY = data.getNodes("y");
            ArrayList<Integer> nodesZ = data.getNodes("z");
            ArrayList<Integer> nodesA = data.getNodes("a");

            if(nodesX.size()== 0) {
                player.sendMessage(new TextComponentString("当前世界无可探测的节点,请继续提高警惕"));
            }else if((int)(Math.random()*10)>=5&&SRPWorldData.get(player.getEntityWorld()).getEvolutionPhase()==7||(int)(Math.random()*10)>=2&&SRPWorldData.get(player.getEntityWorld()).getEvolutionPhase()==8) {
                player.sendMessage(new TextComponentString("节点探测器的频道似乎受到了干扰，机器已经过流损坏。"));
            }else {
                String out = "警告，世界出现节点，请务必尽快清除。所有节点坐标为 (x, y, z, age): ";
                for (int i = 0; i < nodesX.size(); i++) {
                    out = out + "[" + nodesX.get(i) + ", " + nodesY.get(i) + ", " + nodesZ.get(i) + ", " + nodesA.get(i) + "] ";
                }
                player.sendMessage(new TextComponentString(out));
            }

            stack.setCount(stack.getCount() - 1);
        }

        return stack;
    }

    // How long it takes to use it (how long to show the animation).
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 20;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BOW;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return slotChanged;
    }
}
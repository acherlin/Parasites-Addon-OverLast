package com.overlast.block;

import com.dhanantry.scapeandrunparasites.SRPMain;
import com.dhanantry.scapeandrunparasites.block.*;
import com.dhanantry.scapeandrunparasites.network.SRPPacketParticle;
import com.dhanantry.scapeandrunparasites.util.ParasiteEventWorld;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Random;

public class BlockBlockPurifier
        extends Block {

    private final String name = "blockpurifier";
    public BlockBlockPurifier() {
        super(Material.SPONGE);
        this.setUnlocalizedName(OverLast.MOD_ID + "."+name);
        this.setRegistryName(name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setHardness(5.0F);
        setDefaultState(this.blockState.getBaseState());
    }


    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        if (worldIn.isRemote) return flag;

        ItemStack head = new ItemStack(playerIn.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem());
        if (head.getItem() != Items.AIR) return flag;

            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            ParasiteEventWorld.killBiome(worldIn, pos, 16);
            int distanceY = pos.getY();
            for (int y = 0; y <= 255; y++) {
                for (int x = -16; x <= 16; x++) {
                    for (int z = -16; z <= 16; z++) {
                        Block targetBlock = worldIn.getBlockState(pos.add(x, y - distanceY, z)).getBlock();
                        if (targetBlock instanceof BlockAir) {
                            continue;
                        }
                        if (targetBlock instanceof BlockInfestedStain || targetBlock instanceof BlockParasiteStain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.DIRT.getDefaultState());
                            continue;
                        }
                        if (targetBlock instanceof BlockInfestedRemain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }
                        if (targetBlock instanceof BlockInfestedTrunk || targetBlock instanceof BlockParasiteTrunk) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.LOG.getDefaultState());
                            continue;
                        }
                        if (targetBlock instanceof BlockInfestedRubble || targetBlock instanceof BlockParasiteRubble || targetBlock instanceof BlockParasiteRubbleDense) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.STONE.getDefaultState());
                            continue;
                        }
                        if (targetBlock instanceof BlockInfestedBush || targetBlock instanceof BlockParasiteBush || targetBlock instanceof BlockParasiteMouth || targetBlock instanceof BlockStairBase || targetBlock instanceof BlockSlabHalf || targetBlock instanceof BlockSlabDouble
                                || targetBlock instanceof BlockParasiteCanister || targetBlock instanceof BlockParasiteThin) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }
                    }
                }
            }
            //worldIn.setBlockState(pos, Blocks.STONE.getDefaultState());
            for (int i = 0; i <= 3; i++) {
                SRPMain.network.sendToAll((IMessage) new SRPPacketParticle(pos.up(0), 0.5F, 0.5F, (byte) 2));
            }
        return flag;
    }


    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote)
            return;
        ParasiteEventWorld.killBiome(worldIn, pos, 16);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

        for (int i = 0; i <= 3; i++)
            SRPMain.network.sendToAll((IMessage) new SRPPacketParticle(pos.up(0), 0.5F, 0.5F, (byte) 2));
    }
}



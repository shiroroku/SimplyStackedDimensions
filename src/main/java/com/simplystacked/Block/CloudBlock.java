package com.simplystacked.Block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class CloudBlock extends BreakableBlock {

	private static final int decayTime = 3;
	public static final IntegerProperty DECAYTIMER = IntegerProperty.create("decaytimer", 0, decayTime);

	public CloudBlock() {
		super(AbstractBlock.Properties.of(Material.WOOL).strength(0.3F).randomTicks().noOcclusion().sound(SoundType.SNOW).harvestTool(ToolType.SHOVEL));
		this.registerDefaultState(this.stateDefinition.any().setValue(DECAYTIMER, decayTime));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (state.getValue(DECAYTIMER) <= 0) {
			world.removeBlock(pos, false);
		} else {
			world.setBlockAndUpdate(pos, state.setValue(DECAYTIMER, state.getValue(DECAYTIMER) - 1));
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(DECAYTIMER, decayTime);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DECAYTIMER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

}

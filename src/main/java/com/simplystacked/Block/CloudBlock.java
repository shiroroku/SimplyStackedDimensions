package com.simplystacked.Block;


import com.simplystacked.SimplyStackedDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class CloudBlock extends Block {

    private static final int decayTime = 3;
    public static final IntegerProperty DECAYTIMER = IntegerProperty.create("decaytimer", 0, decayTime);

    public CloudBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.3F).randomTicks().noOcclusion().sound(SoundType.SNOW));
        this.registerDefaultState(this.stateDefinition.any().setValue(DECAYTIMER, decayTime));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        SimplyStackedDimensions.LOGGER.debug(pState.getValue(DECAYTIMER));
        if (pState.getValue(DECAYTIMER) <= 0) {
            pLevel.removeBlock(pPos, false);
        } else {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(DECAYTIMER, pState.getValue(DECAYTIMER) - 1));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(DECAYTIMER, decayTime);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DECAYTIMER);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}

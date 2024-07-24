package com.simplystacked.Data;

import com.simplystacked.SimplyStackedDimensions;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DataGenBlocks extends BlockStateProvider {

    public DataGenBlocks(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, SimplyStackedDimensions.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        SimplyStackedDimensions.BLOCKS.getEntries().stream().map(Supplier::get).forEach((block) -> simpleBlockWithItem(block, models().cubeAll(ForgeRegistries.BLOCKS.getKey(block).getPath(), blockTexture(block)).renderType("translucent")));
    }
}

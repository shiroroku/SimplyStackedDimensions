package com.simplystacked.Block;

import com.simplystacked.SimplyStackedDimensions;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockAndItemRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyStackedDimensions.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyStackedDimensions.MODID);

	public static final RegistryObject<Block> CLOUD = registerBlockAndItem("cloud", CloudBlock::new);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	/**
	 * Creates and returns a Block while also creating an Item for that block.
	 *
	 * @param id Block id
	 * @param supplier Block factory
	 * @return Registry object of supplied Block
	 */
	private static <I extends Block> RegistryObject<I> registerBlockAndItem(final String id, final Supplier<? extends I> supplier) {
		RegistryObject<I> createdBlock = BLOCKS.register(id, supplier);
		ITEMS.register(id, () -> new BlockItem(createdBlock.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
		return createdBlock;
	}
}

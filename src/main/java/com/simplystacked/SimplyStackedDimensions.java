package com.simplystacked;

import com.simplystacked.Block.CloudBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod(SimplyStackedDimensions.MODID)
public class SimplyStackedDimensions {

    public static final String MODID = "simplystacked";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyStackedDimensions.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyStackedDimensions.MODID);

    public static final RegistryObject<Block> CLOUD = registerBlockAndItem("cloud", CloudBlock::new);

    public static void init() {
    }

    /**
     * Creates and returns a Block while also creating an Item for that block.
     *
     * @param id       Block id
     * @param supplier Block factory
     * @return Registry object of supplied Block
     */
    private static <I extends Block> RegistryObject<I> registerBlockAndItem(final String id, final Supplier<? extends I> supplier) {
        RegistryObject<I> createdBlock = BLOCKS.register(id, supplier);
        ITEMS.register(id, () -> new BlockItem(createdBlock.get(), new Item.Properties()));
        return createdBlock;
    }

    public SimplyStackedDimensions() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMONCONFIG);
    }
}

package com.example.examplemod.worldgen.feature;

import com.example.examplemod.ExampleBlocks;
import com.example.examplemod.ExampleMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ShelfFeature extends Feature<NoneFeatureConfiguration> {

    ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room"));

    public ShelfFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {
        ExampleMod.LOGGER.info("place {}", featurePlaceContext.origin());

        RandomSource random = featurePlaceContext.random();

        if (featurePlaceContext.level().getBlockState(featurePlaceContext.origin().north()).getBlock() == Blocks.AIR
            && featurePlaceContext.level().getBlockState(featurePlaceContext.origin().south()).getBlock() == Blocks.AIR
            && featurePlaceContext.level().getBlockState(featurePlaceContext.origin().east()).getBlock() == Blocks.AIR
            && featurePlaceContext.level().getBlockState(featurePlaceContext.origin().west()).getBlock() == Blocks.AIR) {
            ExampleMod.LOGGER.info("this should be suppressed with placementmodifier");
            return false;
        }

        int shelfLength = random.nextIntBetweenInclusive(3, 4);
        int shelfHeight = random.nextIntBetweenInclusive(6, 9) - shelfLength;
        BlockPos origin = featurePlaceContext.origin();
        BlockPos copy_origin = new BlockPos(origin);

        for (int i = 0; i < shelfHeight + 1; i++) {
            BlockState shelfState = Blocks.WAXED_COPPER_TRAPDOOR.defaultBlockState()
                    .setValue(TrapDoorBlock.HALF, Half.TOP)
                    .setValue(TrapDoorBlock.OPEN, true)
                    .setValue(TrapDoorBlock.FACING, Direction.WEST);
            featurePlaceContext.level().setBlock(copy_origin, shelfState, 0);
            copy_origin = copy_origin.above();
        }
        origin = origin.east();
        copy_origin = new BlockPos(origin);


        int shelfType = random.nextIntBetweenInclusive(0, 1);
        for (int i = 0; i < shelfLength; i++) {
            BlockState baseState = ExampleBlocks.PLAIN_WALLPAPER.get().defaultBlockState();
            featurePlaceContext.level().setBlock(copy_origin, baseState, 0);
            copy_origin = copy_origin.above();

            for (int j = shelfType; j < shelfHeight; j++) {
                if (j % 2 == 0) {
                    if (random.nextIntBetweenInclusive(0, 9) == 0) {
                        BlockState shelfState = Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.NORTH);
                        featurePlaceContext.level().setBlock(copy_origin, shelfState, 0);
                    }
                } else {
                    BlockState shelfState = Blocks.IRON_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.TOP);
                    featurePlaceContext.level().setBlock(copy_origin, shelfState, 0);
                }

                copy_origin = copy_origin.above();
            }
            origin = origin.east();
            copy_origin = new BlockPos(origin);
        }

        for (int i = 0; i < shelfHeight + 1; i++) {
            BlockState shelfState = Blocks.WAXED_COPPER_TRAPDOOR.defaultBlockState()
                    .setValue(TrapDoorBlock.HALF, Half.TOP)
                    .setValue(TrapDoorBlock.OPEN, true)
                    .setValue(TrapDoorBlock.FACING, Direction.EAST);
            featurePlaceContext.level().setBlock(copy_origin, shelfState, 0);
            copy_origin = copy_origin.above();
        }

//        BlockState baseState = Blocks.RED_WOOL.defaultBlockState();
//        featurePlaceContext.level().setBlock(featurePlaceContext.origin(), baseState, 0);
//        featurePlaceContext.level().setBlock(featurePlaceContext.origin().east(), baseState, 0);
//
//        BlockState shelfState = Blocks.IRON_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.TOP);
//        featurePlaceContext.level().setBlock(featurePlaceContext.origin().above(), shelfState, 0);
//        featurePlaceContext.level().setBlock(featurePlaceContext.origin().above().east(), shelfState, 0);



        //        featurePlaceContext.level().registryAccess().lookupOrThrow(Registries.STRUCTURE).getOrThrow(key);


//        RandomSource randomsource = random;
//        WorldGenLevel worldgenlevel = featurePlaceContext.level();
//        BlockPos blockpos = featurePlaceContext.origin();
//        Rotation rotation = Rotation.getRandom(randomsource);
//
//        StructureTemplateManager structuretemplatemanager = worldgenlevel.getLevel().getServer().getStructureManager();
//        Optional<StructureTemplate> option = structuretemplatemanager.get(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room"));

//        if (!option.isPresent()) {
//            ExampleMod.LOGGER.info("cannot find structure tempalte");
//            return false;
//        }
//        else {
//            StructureTemplate structuretemplate = option.get();
//
////            Vec3i vec3i = structuretemplate.getSize(rotation);
////            BlockPos blockpos1 = blockpos.offset(-vec3i.getX() / 2, 0, -vec3i.getZ() / 2);
////
////            int j = blockpos.getY();
////            int k;
////            for (k = 0; k < vec3i.getX(); ++k) {
////                for (int l = 0; l < vec3i.getZ(); ++l) {
////                    j = Math.min(j, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockpos1.getX() + k, blockpos1.getZ() + l));
////                }
////            }
////
////            k = Math.max(j - 15 - randomsource.nextInt(10), worldgenlevel.getMinY() + 10);
////
////            ExampleMod.LOGGER.info("k: {}", k);
//
//            BlockPos blockpos2 = structuretemplate.getZeroPositionWithTransform(blockpos.atY(4), Mirror.NONE, rotation);
//
//            boolean result = structuretemplate.placeInWorld(worldgenlevel, blockpos2, blockpos2, new StructurePlaceSettings(), randomsource, 4);
//            ExampleMod.LOGGER.info("placeInWorld: {}", result);
            return true;
//        }
    }
}

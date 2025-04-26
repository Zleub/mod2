package com.example.examplemod.worldgen;

import com.example.examplemod.ExampleMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class ExampleFeature extends Feature<NoneFeatureConfiguration> {

    ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room"));

    public ExampleFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {
        ExampleMod.LOGGER.info("place {}", featurePlaceContext.origin());
//        featurePlaceContext.level().setBlock(featurePlaceContext.origin(), Blocks.RED_WOOL.defaultBlockState(), 0);
//        featurePlaceContext.level().registryAccess().lookupOrThrow(Registries.STRUCTURE).getOrThrow(key);


        RandomSource randomsource = featurePlaceContext.random();
        WorldGenLevel worldgenlevel = featurePlaceContext.level();
        BlockPos blockpos = featurePlaceContext.origin();
        Rotation rotation = Rotation.getRandom(randomsource);

        StructureTemplateManager structuretemplatemanager = worldgenlevel.getLevel().getServer().getStructureManager();
        Optional<StructureTemplate> option = structuretemplatemanager.get(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "test_room"));

        if (!option.isPresent()) {
            ExampleMod.LOGGER.info("cannot find structure tempalte");
            return false;
        }
        else {
            StructureTemplate structuretemplate = option.get();

//            Vec3i vec3i = structuretemplate.getSize(rotation);
//            BlockPos blockpos1 = blockpos.offset(-vec3i.getX() / 2, 0, -vec3i.getZ() / 2);
//
//            int j = blockpos.getY();
//            int k;
//            for (k = 0; k < vec3i.getX(); ++k) {
//                for (int l = 0; l < vec3i.getZ(); ++l) {
//                    j = Math.min(j, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockpos1.getX() + k, blockpos1.getZ() + l));
//                }
//            }
//
//            k = Math.max(j - 15 - randomsource.nextInt(10), worldgenlevel.getMinY() + 10);
//
//            ExampleMod.LOGGER.info("k: {}", k);

            BlockPos blockpos2 = structuretemplate.getZeroPositionWithTransform(blockpos.atY(4), Mirror.NONE, rotation);

            boolean result = structuretemplate.placeInWorld(worldgenlevel, blockpos2, blockpos2, new StructurePlaceSettings(), randomsource, 4);
            ExampleMod.LOGGER.info("placeInWorld: {}", result);
            return true;
        }
    }
}

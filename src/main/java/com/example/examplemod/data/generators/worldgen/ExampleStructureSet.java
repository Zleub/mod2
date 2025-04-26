package com.example.examplemod.data.generators.worldgen;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public class ExampleStructureSet {
    public static final ResourceKey<StructureSet> TEST_ROOM = createSetKey("test_room");
//    public static final ResourceKey<StructureSet> BRONZE_DUNGEON = createKey("bronze_dungeon");
//    public static final ResourceKey<StructureSet> SILVER_AND_GOLD_DUNGEONS = createKey("silver_and_gold_dungeons");

    private static ResourceKey<StructureSet> createSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name));
    }

    private static ResourceKey<Structure> createStructKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, name));
    }

    /**
     * Warning for "deprecation" is suppressed because using {@link StructurePlacement.ExclusionZone} is necessary.
     */
    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(TEST_ROOM, new StructureSet(structures.getOrThrow(createStructKey("test_room")), new RandomSpreadStructurePlacement(6, 3, RandomSpreadType.LINEAR, 15536586)));

//        Holder<StructureSet> airborneSetHolder = context.register(SILVER_AND_GOLD_DUNGEONS, new StructureSet(List.of(
//                StructureSet.entry(structures.getOrThrow(AetherStructures.SILVER_DUNGEON), 3),
//                StructureSet.entry(structures.getOrThrow(AetherStructures.GOLD_DUNGEON), 1)),
//                new RandomSpreadStructurePlacement(36, 24, RandomSpreadType.LINEAR, 4325806)));
//
//        context.register(BRONZE_DUNGEON, new StructureSet(structures.getOrThrow(AetherStructures.BRONZE_DUNGEON), new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, 32146754, Optional.of(new StructurePlacement.ExclusionZone(airborneSetHolder, 4)), 6, 5, RandomSpreadType.TRIANGULAR)));
    }



}

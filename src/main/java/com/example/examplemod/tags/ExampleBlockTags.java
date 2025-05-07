package com.example.examplemod.tags;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ExampleBlockTags {
    static TagKey<Block> createKey(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, path));
    }

    public static final TagKey<Block> YELLOW_WALLPAPER = createKey("yellow_wallpaper");
}

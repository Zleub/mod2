package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleFluids;
import com.example.examplemod.ExampleItems;
import com.example.examplemod.ExampleMod;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;

import java.util.Optional;
import java.util.function.BiConsumer;

public class ExampleItemModels extends ItemModelGenerators {
    public ExampleItemModels(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {

//        this.generateFlatItem(ExampleItems.ALMOMD_MILK_BUCKET.get(), ModelTemplates.FLAT_ITEM);
//        this.generateLayeredItem(ExampleItems.ALMOMD_MILK_BUCKET.get(), ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket"), "")
        this.itemModelOutput.accept(ExampleItems.ALMOMD_MILK_BUCKET.get(), new DynamicFluidContainerModel.Unbaked(
                new DynamicFluidContainerModel.Textures(
                        Optional.of(ResourceLocation.fromNamespaceAndPath("", "")),
                        Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "bucket")),
                        Optional.of(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "fluid/almond_milk")),
                        Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "bucket_fluid_cover"))
                ),
                ExampleFluids.ALMOND_MILK.get(),
                false,
                true,
                true
        ));
    }


}

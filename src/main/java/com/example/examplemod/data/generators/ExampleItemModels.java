package com.example.examplemod.data.generators;

import com.example.examplemod.ExampleItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class ExampleItemModels extends ItemModelGenerators {
    public ExampleItemModels(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        this.generateFlatItem(ExampleItems.ALMOMD_MILK_BUCKET.get(), ModelTemplates.FLAT_ITEM);
    }


}

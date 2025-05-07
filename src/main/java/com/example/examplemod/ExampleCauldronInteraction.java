package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static net.minecraft.core.cauldron.CauldronInteraction.fillBucket;

public class ExampleCauldronInteraction {
    public static CauldronInteraction.InteractionMap ALMOND_MILK = CauldronInteraction.newInteractionMap("almond_milk");

    public static void register() {
        ALMOND_MILK.map().put(Items.BUCKET, ALMOND_BUCKET_INTERACTION);
        ALMOND_MILK.map().put(Items.GLASS_BOTTLE, ALMOND_BOTTLE_INTERACTION);
        CauldronInteraction.EMPTY.map().put(ExampleItems.ALMOMD_MILK_BUCKET.get(), ExampleCauldronInteraction::fillWithAlmondInteraction);
    }

    public static CauldronInteraction ALMOND_BUCKET_INTERACTION = (blockState, level, blockPos, player, hand, itemStack) ->
            fillBucket(blockState, level, blockPos, player, hand, itemStack, new ItemStack(ExampleItems.ALMOMD_MILK_BUCKET.get()), (p_175660_) -> (Integer)p_175660_.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);
    public static CauldronInteraction ALMOND_BOTTLE_INTERACTION = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
        if (!level.isClientSide) {
            Item item = itemStack.getItem();
//            player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, PotionContents.createItemStack(Items.POTION, ExamplePotions.ALMOND_MILK)));
            player.setItemInHand(interactionHand, new ItemStack(ExampleItems.ALMOND_MILK_BOTTLE.get()));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
            level.playSound((Entity)null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
        }

        return InteractionResult.SUCCESS;
    };

    public static InteractionResult fillWithAlmondInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, ItemStack itemStack) {
        return CauldronInteraction.emptyBucket(level, blockPos, player, hand, itemStack, ExampleBlocks.ALMOND_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);
    }
}

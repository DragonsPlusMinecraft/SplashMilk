package love.marblegate.splashmilk.item;

import love.marblegate.splashmilk.entity.MilkBottleEntity;
import love.marblegate.splashmilk.registry.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MilkBottle extends Item {
    public MilkBottle() {
        super(new Properties().tab(ItemGroup.TAB_BREWING));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 10;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        return DrinkHelper.useDrink(world, playerEntity, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity livingEntity) {
        if(!world.isClientSide())
            livingEntity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        if(livingEntity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) livingEntity;
            if (!player.abilities.instabuild) {
                itemStack.shrink(1);
            }
            if(itemStack.isEmpty()){
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                if (!player.abilities.instabuild) {
                    player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
                }
                return itemStack;
            }
        } else {
            itemStack.shrink(1);
            return itemStack;
        }
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.DRINK;
    }


}

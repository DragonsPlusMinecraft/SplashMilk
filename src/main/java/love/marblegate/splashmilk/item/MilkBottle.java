package love.marblegate.splashmilk.item;

import love.marblegate.splashmilk.entity.MilkBottleEntity;
import love.marblegate.splashmilk.registry.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MilkBottle extends Item {
    public MilkBottle() {
        super(new Properties().tab(ItemGroup.TAB_BREWING));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean isLingering = itemstack.getItem().equals(ItemRegistry.LINGERING_MILK_BOTTLE.get());
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), isLingering ? SoundEvents.LINGERING_POTION_THROW : SoundEvents.SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!world.isClientSide) {
            MilkBottleEntity milkBottleEntity = new MilkBottleEntity(world, player);
            milkBottleEntity.setItem(itemstack);
            milkBottleEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(milkBottleEntity);
        }

        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }
}

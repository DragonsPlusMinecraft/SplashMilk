package plus.dragons.splashmilk.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class MilkBottle extends Item {
    public MilkBottle() {
        super(new Properties());
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 10;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide())
            livingEntity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        if(livingEntity instanceof Player player){
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            if(itemStack.isEmpty()){
               return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                if (!player.getAbilities().instabuild) {
                    player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }
                return itemStack;
            }
        } else {
            itemStack.shrink(1);
            return itemStack;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
}

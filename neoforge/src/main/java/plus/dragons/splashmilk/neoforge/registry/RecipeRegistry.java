package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import plus.dragons.splashmilk.neoforge.SplashMilk;

@EventBusSubscriber(modid = SplashMilk.MOD_ID)
public class RecipeRegistry {
    @SubscribeEvent
    public static void recipe(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(Ingredient.ofItems(ItemRegistry.MILK_BOTTLE.get()), Ingredient.ofItems(Items.GUNPOWDER), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofItems(ItemRegistry.SPLASH_MILK_BOTTLE.get()), Ingredient.ofItems(Items.DRAGON_BREATH), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(new CustomBrewingRecipe());
    }

    static class CustomBrewingRecipe implements IBrewingRecipe{

        @Override
        public boolean isInput(ItemStack arg) {
            if(arg.isOf(Items.POTION)||arg.isOf(Items.SPLASH_POTION)||arg.isOf(Items.LINGERING_POTION)){
                PotionContentsComponent p = arg.get(DataComponentTypes.POTION_CONTENTS);
                return p.matches(Potions.WATER) || p.matches(Potions.THICK) || p.matches(Potions.MUNDANE) || p.matches(Potions.AWKWARD);
            }
            return false;
        }

        @Override
        public boolean isIngredient(ItemStack arg) {
            return arg.isOf(Items.MILK_BUCKET);
        }

        @Override
        public ItemStack getOutput(ItemStack arg, ItemStack arg2) {
            if(arg.isOf(Items.POTION)){
                return ItemRegistry.MILK_BOTTLE.get().getDefaultStack();
            }
            if(arg.isOf(Items.SPLASH_POTION)){
                return ItemRegistry.SPLASH_MILK_BOTTLE.get().getDefaultStack();
            }
            return ItemRegistry.LINGERING_MILK_BOTTLE.get().getDefaultStack();
        }
    }

}

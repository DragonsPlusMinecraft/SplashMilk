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

    static class CustomBrewingRecipe implements IBrewingRecipe {

        @Override
        public boolean isInput(ItemStack input) {
            if (!input.isOf(Items.POTION) && !input.isOf(Items.SPLASH_POTION) && !input.isOf(Items.LINGERING_POTION)) {
                return false;
            }
            PotionContentsComponent contents = input.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            return contents.matches(Potions.WATER) || contents.matches(Potions.THICK) || contents.matches(Potions.MUNDANE) || contents.matches(Potions.AWKWARD);
        }

        @Override
        public boolean isIngredient(ItemStack ingredient) {
            return ingredient.isOf(Items.MILK_BUCKET);
        }

        @Override
        public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
            if (!isIngredient(ingredient) || !isInput(input)) {
                return ItemStack.EMPTY;
            }
            if (input.isOf(Items.POTION)) {
                return ItemRegistry.MILK_BOTTLE.get().getDefaultStack();
            }
            if (input.isOf(Items.SPLASH_POTION)) {
                return ItemRegistry.SPLASH_MILK_BOTTLE.get().getDefaultStack();
            }
            return ItemRegistry.LINGERING_MILK_BOTTLE.get().getDefaultStack();
        }
    }

}

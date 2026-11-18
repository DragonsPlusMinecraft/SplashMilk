package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import plus.dragons.splashmilk.neoforge.SplashMilk;

@EventBusSubscriber(modid = SplashMilk.MOD_ID)
public class RecipeRegistry {
    @SubscribeEvent
    public static void recipe(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(Ingredient.of(ItemRegistry.MILK_BOTTLE.get()), Ingredient.of(Items.GUNPOWDER), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.of(ItemRegistry.SPLASH_MILK_BOTTLE.get()), Ingredient.of(Items.DRAGON_BREATH), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(new CustomBrewingRecipe());
    }

    static class CustomBrewingRecipe implements IBrewingRecipe {

        @Override
        public boolean isInput(ItemStack input) {
            if (!input.is(Items.POTION) && !input.is(Items.SPLASH_POTION) && !input.is(Items.LINGERING_POTION)) {
                return false;
            }
            PotionContents contents = input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            return contents.is(Potions.WATER) || contents.is(Potions.THICK) || contents.is(Potions.MUNDANE) || contents.is(Potions.AWKWARD);
        }

        @Override
        public boolean isIngredient(ItemStack ingredient) {
            return ingredient.is(Items.MILK_BUCKET);
        }

        @Override
        public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
            if (!isIngredient(ingredient) || !isInput(input)) {
                return ItemStack.EMPTY;
            }
            if (input.is(Items.POTION)) {
                return ItemRegistry.MILK_BOTTLE.get().getDefaultInstance();
            }
            if (input.is(Items.SPLASH_POTION)) {
                return ItemRegistry.SPLASH_MILK_BOTTLE.get().getDefaultInstance();
            }
            return ItemRegistry.LINGERING_MILK_BOTTLE.get().getDefaultInstance();
        }
    }

}

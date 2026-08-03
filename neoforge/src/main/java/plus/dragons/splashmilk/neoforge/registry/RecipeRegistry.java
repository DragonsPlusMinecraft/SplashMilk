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

    static class CustomBrewingRecipe implements IBrewingRecipe{

        @Override
        public boolean isInput(ItemStack arg) {
            if(arg.is(Items.POTION)||arg.is(Items.SPLASH_POTION)||arg.is(Items.LINGERING_POTION)){
                PotionContents p = arg.get(DataComponents.POTION_CONTENTS);
                return p.is(Potions.WATER) || p.is(Potions.THICK) || p.is(Potions.MUNDANE) || p.is(Potions.AWKWARD);
            }
            return false;
        }

        @Override
        public boolean isIngredient(ItemStack arg) {
            return arg.is(Items.MILK_BUCKET);
        }

        @Override
        public ItemStack getOutput(ItemStack arg, ItemStack arg2) {
            if(arg.is(Items.POTION)){
                return ItemRegistry.MILK_BOTTLE.get().getDefaultInstance();
            }
            if(arg.is(Items.SPLASH_POTION)){
                return ItemRegistry.SPLASH_MILK_BOTTLE.get().getDefaultInstance();
            }
            return ItemRegistry.LINGERING_MILK_BOTTLE.get().getDefaultInstance();
        }
    }

}

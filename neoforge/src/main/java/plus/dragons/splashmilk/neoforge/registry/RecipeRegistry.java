package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistry {
    @SubscribeEvent
    public static void recipeGen(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(Ingredient.ofItems(ItemRegistry.MILK_BOTTLE.get()), Ingredient.ofItems(Items.GUNPOWDER), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofItems(ItemRegistry.SPLASH_MILK_BOTTLE.get()), Ingredient.ofItems(Items.DRAGON_BREATH), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.WATER)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.MUNDANE)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.MUNDANE)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.THICK)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.THICK)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        });
    }
}

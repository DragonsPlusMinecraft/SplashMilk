package love.marblegate.splashmilk.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistry {
    @SubscribeEvent
    public static void recipeGen(FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.WATER)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.MUNDANE)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.MUNDANE)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.THICK)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.THICK)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)),Ingredient.of(Items.MILK_BUCKET),new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        });
    }
}

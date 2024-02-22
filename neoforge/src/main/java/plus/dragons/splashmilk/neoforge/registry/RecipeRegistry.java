package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.Optional;

@EventBusSubscriber(modid = SplashMilk.MOD_ID)
public class RecipeRegistry {
    @SubscribeEvent
    public static void recipeGen(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(Ingredient.ofItems(ItemRegistry.MILK_BOTTLE.get()), Ingredient.ofItems(Items.GUNPOWDER), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofItems(ItemRegistry.SPLASH_MILK_BOTTLE.get()), Ingredient.ofItems(Items.DRAGON_BREATH), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.LINGERING_POTION), Potions.WATER)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.SPLASH_POTION), Potions.MUNDANE)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.LINGERING_POTION), Potions.MUNDANE)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.SPLASH_POTION), Potions.THICK)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.LINGERING_POTION), Potions.THICK)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.SPLASH_MILK_BOTTLE.get()));
        event.getBuilder().addRecipe(Ingredient.ofStacks(ofPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.ofItems(Items.MILK_BUCKET), new ItemStack(ItemRegistry.LINGERING_MILK_BOTTLE.get()));
    }

    private static ItemStack ofPotion(ItemStack basePotionItem,RegistryEntry<Potion> potion){
        basePotionItem.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potion));
        return basePotionItem;
    }

}

package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Im loading data!!!");
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //getting UOMs
        Optional<UnitOfMeasure> TeaspoonUOM = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!TeaspoonUOM.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> TablespoonUOM = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!TablespoonUOM.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> CupUOM = unitOfMeasureRepository.findByDescription("Cup");

        if(!CupUOM.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> OunceUOM = unitOfMeasureRepository.findByDescription("Ounce");

        if(!OunceUOM.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        UnitOfMeasure Teaspoon = TeaspoonUOM.get();
        UnitOfMeasure Tablespoon = TablespoonUOM.get();
        UnitOfMeasure Cup = CupUOM.get();
        UnitOfMeasure Ounce = OunceUOM.get();

        //get Categories

        Optional<Category> americanCategory = categoryRepository.findByDescription("American");

        if(!americanCategory.isPresent()){
            throw new RuntimeException("Category not found");
        }

        Optional<Category> mexicanCategory = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategory.isPresent()){
            throw new RuntimeException("Category not found");
        }

        Category Mexican = mexicanCategory.get();
        Category American = americanCategory.get();

        //Guacamole
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "How to make guacamole - scoring avocado\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "How to make guacamole - smashing avocado with fork\n" +
                "Add the remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");


        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");


        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("Tipe avocados", new BigDecimal(2),Cup));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(5),Teaspoon));
        guacRecipe.addIngredient(new Ingredient("Fresh lemon juice", new BigDecimal(3),Tablespoon));
        guacRecipe.addIngredient(new Ingredient("Ripe Tomatos", new BigDecimal(2),Ounce));

        guacRecipe.getCategories().add(American);
        guacRecipe.getCategories().add(Mexican);

        recipes.add(guacRecipe);
        return recipes;
    }

}

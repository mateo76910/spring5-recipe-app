package guru.springframework.domain;

import lombok.Data;
import org.hibernate.dialect.identity.Ingres9IdentityColumnSupport;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    @ManyToMany
    @JoinTable(name = "recipe_category",
    joinColumns = @JoinColumn(name ="recipe_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Enumerated(value=EnumType.STRING) // odlucuje hoce li enumove vrijednosti gledati kao brojeve ili stringove
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)  //ukoliko se obrise recept brise se i notes
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    //@JoinColumn(name="recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Lob
    private Byte[] image;


    public void setNotes(Notes notes) {

        this.notes = notes;
        notes.setRecipe(this);
    }



    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

}

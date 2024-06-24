package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.recipe.Recipe;
import net.minestom.server.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minestom.server.network.NetworkBuffer.STRING;

public record DeclareRecipesPacket(@NotNull List<Recipe> recipes) implements ServerPacket.Play {
    public static final int MAX_RECIPES = Short.MAX_VALUE;
    public static final int MAX_INGREDIENTS = 128;

    public DeclareRecipesPacket {
        recipes = List.copyOf(recipes);
    }

    public DeclareRecipesPacket(@NotNull NetworkBuffer reader) {
        this(reader.readCollection(r -> {
            final String recipeId = r.read(STRING);
            final RecipeType type = r.read(RecipeType.NETWORK_TYPE);
            return switch (type) {
                case RecipeType.SHAPED -> new Recipe.Shaped(recipeId, reader);
                case RecipeType.SHAPELESS -> new Recipe.Shapeless(recipeId, reader);
                case RecipeType.SMELTING -> new Recipe.Smelting(recipeId, reader);
                case RecipeType.BLASTING -> new Recipe.Blasting(recipeId, reader);
                case RecipeType.SMOKING -> new Recipe.Smoking(recipeId, reader);
                case RecipeType.CAMPFIRE_COOKING -> new Recipe.CampfireCooking(recipeId, reader);
                case RecipeType.STONECUTTING -> new Recipe.Stonecutting(recipeId, reader);
                case RecipeType.SMITHING_TRANSFORM -> new Recipe.SmithingTransform(recipeId, reader);
                case RecipeType.SMITHING_TRIM -> new Recipe.SmithingTrim(recipeId, reader);
                default -> throw new UnsupportedOperationException("Unrecognized type: " + type);
            };
        }, MAX_RECIPES));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.writeCollection(recipes, (bWriter, recipe) -> {
            bWriter.write(STRING, recipe.id());
            bWriter.write(RecipeType.NETWORK_TYPE, recipe.type());
            bWriter.write(recipe);
        });
    }

    @Override
    public int playId() {
        return ServerPacketIdentifier.DECLARE_RECIPES;
    }
}

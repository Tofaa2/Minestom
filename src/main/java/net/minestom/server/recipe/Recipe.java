package net.minestom.server.recipe;

import net.minestom.server.item.ItemStack;
import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.minestom.server.network.NetworkBuffer.*;

public sealed interface Recipe extends NetworkBuffer.Writer {
    int MAX_INGREDIENTS = 128;

    @NotNull String id();

    @NotNull RecipeType type();

    record Shaped(String id, String group, RecipeCategory.Crafting category,
                  int width, int height, List<Ingredient> ingredients,
                  ItemStack result, boolean showNotification) implements Recipe {
        public Shaped {
            if (ingredients.size() != width * height)
                throw new IllegalArgumentException("Invalid shaped recipe, ingredients size must be equal to width * height");
            ingredients = List.copyOf(ingredients);
        }

        private Shaped(Shaped packet) {
            this(packet.id, packet.group, packet.category, packet.width, packet.height, packet.ingredients, packet.result, packet.showNotification);
        }

        public Shaped(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(read(id, reader));
        }

        private static Shaped read(@NotNull String id, @NotNull NetworkBuffer reader) {
            String group = reader.read(STRING);
            RecipeCategory.Crafting category = reader.readEnum(RecipeCategory.Crafting.class);
            int width = reader.read(VAR_INT);
            int height = reader.read(VAR_INT);
            List<Ingredient> ingredients = new ArrayList<>();
            for (int slot = 0; slot < width * height; slot++) {
                ingredients.add(new Ingredient(reader));
            }
            ItemStack result = reader.read(ItemStack.STRICT_NETWORK_TYPE);
            boolean showNotification = reader.read(BOOLEAN);
            return new Shaped(id, group, category, width, height, ingredients, result, showNotification);
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Crafting.class, category);
            writer.write(VAR_INT, width);
            writer.write(VAR_INT, height);
            for (Ingredient ingredient : ingredients) {
                ingredient.write(writer);
            }
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
            writer.write(BOOLEAN, showNotification);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SHAPED;
        }
    }

    record Shapeless(String id, String group, RecipeCategory.Crafting category,
                     List<Ingredient> ingredients, ItemStack result) implements Recipe {
        public Shapeless(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    reader.readEnum(RecipeCategory.Crafting.class),
                    reader.readCollection(Ingredient::new, MAX_INGREDIENTS), reader.read(ItemStack.STRICT_NETWORK_TYPE));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Crafting.class, category);
            writer.writeCollection(ingredients);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SHAPELESS;
        }
    }

    record Smelting(String id, String group, RecipeCategory.Cooking category,
                    Ingredient ingredient, ItemStack result,
                    float experience, int cookingTime) implements Recipe {
        public Smelting(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    reader.readEnum(RecipeCategory.Cooking.class),
                    new Ingredient(reader),
                    reader.read(ItemStack.STRICT_NETWORK_TYPE),
                    reader.read(FLOAT), reader.read(VAR_INT));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Cooking.class, category);
            writer.write(ingredient);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
            writer.write(FLOAT, experience);
            writer.write(VAR_INT, cookingTime);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SMELTING;
        }
    }

    record Blasting(String id, String group, RecipeCategory.Cooking category,
                    Ingredient ingredient, ItemStack result,
                    float experience, int cookingTime) implements Recipe {
        public Blasting(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    reader.readEnum(RecipeCategory.Cooking.class),
                    new Ingredient(reader),
                    reader.read(ItemStack.STRICT_NETWORK_TYPE),
                    reader.read(FLOAT), reader.read(VAR_INT));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Cooking.class, category);
            writer.write(ingredient);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
            writer.write(FLOAT, experience);
            writer.write(VAR_INT, cookingTime);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.BLASTING;
        }
    }

    record Smoking(String id, String group, RecipeCategory.Cooking category,
                   Ingredient ingredient, ItemStack result,
                   float experience, int cookingTime) implements Recipe {
        public Smoking(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    reader.readEnum(RecipeCategory.Cooking.class),
                    new Ingredient(reader),
                    reader.read(ItemStack.STRICT_NETWORK_TYPE),
                    reader.read(FLOAT), reader.read(VAR_INT));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Cooking.class, category);
            writer.write(ingredient);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
            writer.write(FLOAT, experience);
            writer.write(VAR_INT, cookingTime);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SMOKING;
        }
    }

    record CampfireCooking(String id, String group, RecipeCategory.Cooking category,
                           Ingredient ingredient, ItemStack result,
                           float experience, int cookingTime) implements Recipe {
        public CampfireCooking(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    reader.readEnum(RecipeCategory.Cooking.class),
                    new Ingredient(reader),
                    reader.read(ItemStack.STRICT_NETWORK_TYPE),
                    reader.read(FLOAT), reader.read(VAR_INT));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.writeEnum(RecipeCategory.Cooking.class, category);
            writer.write(ingredient);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
            writer.write(FLOAT, experience);
            writer.write(VAR_INT, cookingTime);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.CAMPFIRE_COOKING;
        }
    }

    record Stonecutting(String id, String group, Ingredient ingredient,
                        ItemStack result) implements Recipe {
        public Stonecutting(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, reader.read(STRING),
                    new Ingredient(reader),
                    reader.read(ItemStack.STRICT_NETWORK_TYPE));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(STRING, group);
            writer.write(ingredient);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.STONECUTTING;
        }
    }

    record SmithingTransform(String id, Ingredient template, Ingredient base,
                             Ingredient addition, ItemStack result) implements Recipe {
        public SmithingTransform(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, new Ingredient(reader), new Ingredient(reader),
                    new Ingredient(reader), reader.read(ItemStack.STRICT_NETWORK_TYPE));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(template);
            writer.write(base);
            writer.write(addition);
            writer.write(ItemStack.STRICT_NETWORK_TYPE, result);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SMITHING_TRANSFORM;
        }
    }

    record SmithingTrim(String id, Ingredient template,
                        Ingredient base, Ingredient addition) implements Recipe {
        public SmithingTrim(@NotNull String id, @NotNull NetworkBuffer reader) {
            this(id, new Ingredient(reader), new Ingredient(reader), new Ingredient(reader));
        }

        @Override
        public void write(@NotNull NetworkBuffer writer) {
            writer.write(template);
            writer.write(base);
            writer.write(addition);
        }

        @Override
        public @NotNull RecipeType type() {
            return RecipeType.SMITHING_TRIM;
        }
    }

    record Ingredient(@NotNull List<@NotNull ItemStack> items) implements NetworkBuffer.Writer {
        public Ingredient {
            items = List.copyOf(items);
        }

        public Ingredient(@NotNull ItemStack @NotNull ... items) {
            this(List.of(items));
        }

        public Ingredient() {
            this(List.of());
        }

        public Ingredient(@NotNull NetworkBuffer reader) {
            this(reader.readCollection(ItemStack.STRICT_NETWORK_TYPE, MAX_INGREDIENTS));
        }

        public void write(@NotNull NetworkBuffer writer) {
            writer.writeCollection(ItemStack.STRICT_NETWORK_TYPE, items);
        }
    }
}

import java.util.Scanner;

public class Main {
    /**
     * Check if char is number
     * @param ch character
     * @return boolean
     */
    private static boolean IsNumber(char ch) {
        // Is number
        try {
            Integer.parseInt(String.valueOf(ch));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Resolve amount of ingredient
     * @param ingredient input
     * @param amount input
     * @return String[int][]
     */
    private static String[][] ResolveIngredient(String[] ingredient, String[] amount) {
        String[][] resolved = new String[ingredient.length][2];
        for (int i = 0; i<ingredient.length; i++) {
            // Sanity check
            if (i > amount.length -1) {
                // TODO: Error
                continue;
            }

            // Resolve
            resolved[i][0] = ingredient[i];
            resolved[i][1] = amount[i];
        }
        return resolved;
    }

    /**
     * Resolve recipes in this format: 2Flour4Water
     * @param raw raw recipes stored as array
     * @param ingredients
     * @return
     */
    private static String[][][] ResolveRecipes(String[] raw, String[] ingredients) {
        // String
        String buffer = "";
        int position = -1;
        int searchPosition = 0;
        int endPosition = 0;
        int count = 0;
        boolean valid = false;

        // Max can be ingredients.length
        String[][][] resolved = new String[raw.length][ingredients.length][2];

        // Loop trough recipes
        for (int i = 0; i < raw.length; i++) {
            // Read into buffer
            buffer = raw[i];
            count = 0;

            // Calculate need
            for (int i2 = 0; i2 < ingredients.length; i2++) {
                searchPosition = 0; // Reset for each ingredient
                valid = false;      // Reset
                // Filter
                while (!valid) {
                    position = buffer.indexOf(ingredients[i2], searchPosition);
                    endPosition = position + ingredients[i2].length();

                    // Position
                    if (position == -1) {
                        break;  // We can go to the next
                    }

                    // If we position is the last element then we are gucci
                    //System.out.printf("   %d:%d before: %s stringatpos: %s endpos: %d isnumber %b\n",
                    //        position + ingredients[i2].length(),
                    //        buffer.length(),
                    //        buffer.charAt(position - 1),
                    //        buffer.charAt(position),
                    //        endPosition,
                    //        IsNumber(buffer.charAt(position - 1))
                    //);

                    // number|ingredient|end
                    if (endPosition == buffer.length() && IsNumber(buffer.charAt(position - 1))) {
                        valid = true;
                        break;
                    }

                    // number|ingredient|number
                    if (IsNumber(buffer.charAt(position - 1)) && IsNumber(buffer.charAt(endPosition))) {
                        valid = true;
                        break;
                    }

                    // Skip already filtered
                    searchPosition = position + ingredients[i2].length();

                    // EOS
                    if (searchPosition >= buffer.length())
                        break;
                }

                // Valid
                if (valid) {
                    // Reset
                    valid = false;
                    searchPosition = position - 1;

                    // Get positions until number
                    for (int i3 = searchPosition -1; i3 >= 0; i3--) {
                        if (!IsNumber(buffer.charAt(i3))) {
                            break;
                        }
                        searchPosition--;
                    }

                    // Raw number
                    String rawNumber = buffer.substring(searchPosition, position);

                    // Sanity check
                    if (count > ingredients.length) {
                        // Not enough space;
                        break;
                    }

                    // Allocate space
                    resolved[i][count][0] = ingredients[i2];
                    resolved[i][count][1] = rawNumber;
                    count++;
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves recipes in this format: 2B2C2A
     * @param raw
     * @param ingredients
     * @return
     */
    private static String[][][] ResolveRecipesSimple(String[] raw, String[] ingredients) {
        // Max can be ingredients.length
        String[][][] resolved = new String[raw.length][ingredients.length][2];
        String buffer = "";
        String temp = "";
        int count = 0;
        // Loop trough recipes
        for (int i = 0; i < raw.length; i++) {
            count = 0;
            // Read into buffer
            buffer = raw[i];
            for (int i2 = 0; i2 < buffer.length(); i2++) {
                temp = String.valueOf(buffer.charAt(i2));
                if (i2 == 0 || (i2 > 1 && i2 % 2 == 0)) {
                    // Amount value
                    resolved[i][count][1] = temp;
                } else {
                    // Ingredient
                    resolved[i][count][0] = temp;
                    // We can go higher
                    count++;
                }
            }
        }

        return resolved;
    }

    /**
     * Entry point
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in); // Scanner
        String buffer = "";                     // Input buffer

        System.out.println("Geben Sie die Anzahl Zutaten und Rezepte an:");
        int iIngredientsSize = scan.nextInt();   // Zutaten
        int iRecipesSize = scan.nextInt();       // Rezepte

        // Flush
        scan.nextLine();

        // Ungültige anzahl rezepte
        if (iIngredientsSize == 0 || iRecipesSize == 0) {
           System.out.println("Die Eingabe ist ungültig.");
        }

        // Get next line
        System.out.printf("Geben Sie die %d Zutaten an: \n", iIngredientsSize);
        buffer = scan.nextLine();

        // Ingredents
        String[] aRawIngredients = buffer.split(" ");
        if (aRawIngredients.length != iIngredientsSize) {
           System.out.printf("Die Anzahl Zutaten entspricht nicht der eingabe: %d != %d. \n", aRawIngredients.length, iIngredientsSize);
           return;
        }

        // Get next line
        System.out.printf("Geben Sie die %d Rezepte an: \n", iRecipesSize);
        buffer = scan.nextLine();

        // Recipes
        String[] aRawRecipes = buffer.split(" ");
        if (aRawRecipes.length != iRecipesSize) {
           System.out.printf("Die Anzahl Rezepte entspricht nicht der eingabe: %d != %d. \n", aRawRecipes.length, iRecipesSize);
           return;
        }


        // Get next line
        System.out.printf("Geben Sie die %d Mengen an: \n", iIngredientsSize);
        buffer = scan.nextLine();

        //// Amounts
        String[] aRawAmounts = buffer.split(" ");
        if (aRawAmounts.length != iIngredientsSize) {
           System.out.printf("Die Anzahl Mengen entspricht nicht den Zutaten: %d != %d. \n", aRawAmounts.length, iIngredientsSize);
           return;
        }

        int[] aCooked = new int[aRawRecipes.length];
        int restAmount = 0;

        // Ingredients
        String[][] aIngredients = ResolveIngredient(aRawIngredients, aRawAmounts);

        // Resolve
        String[][][] aRecipes = ResolveRecipes(aRawRecipes, aRawIngredients);

        // This method is resolving the stuff simple:
        //String[][][] aRecipes = ResolveRecipesSimple(aRawRecipes, aRawIngredients);

        boolean enough = true;
        boolean cooked = true;
        int value = 0;
        while(cooked) {
            cooked = false;
            // Loop trough recipes
            for (int i = 0; i < aRecipes.length; i++) {
                enough = true;

                // Loop trough ingredients in recipe
                for (int i2 = 0; i2 < aRecipes[i].length; i2++) {
                    // Check if we have enough
                    for (int i3 = 0; i3 < aIngredients.length; i3++) {
                        if (aIngredients[i3][0].equals(aRecipes[i][i2][0])) {
                            if (Integer.parseInt(aIngredients[i3][1]) < Integer.parseInt(aRecipes[i][i2][1])) {
                                enough = false;
                                break;
                            }
                        }
                    }
                }

                // If we have enough....
                if (enough) {
                    cooked = true;

                    // Loop trough ingredients in recipe
                    for (int i2 = 0; i2 < aRecipes[i].length; i2++) {
                        // Check if we have enough
                        for (int i3 = 0; i3 < aIngredients.length; i3++) {
                            if (aIngredients[i3][0].equals(aRecipes[i][i2][0])) {
                                value = Integer.parseInt(aRecipes[i][i2][1]);
                                aIngredients[i3][1] = Integer.toString(Integer.parseInt(aIngredients[i3][1]) - value);
                            }
                        }
                    }

                    aCooked[i]++;
                }
            }
        }

        // Separate from input
        System.out.println();

        // Loop Make output
        for(int i = 0; i < aRawRecipes.length; i++) {
            System.out.printf("%s:%d\n", aRawRecipes[i], aCooked[i]);
        }

        // Calculate rest amount
        for(int i = 0; i < aIngredients.length; i++) {
            System.out.printf("%s: %s ",aIngredients[i][0], aIngredients[i][1]);
            restAmount += Integer.parseInt(aIngredients[i][1]);
        }

        // Separate from rest amount calculation
        System.out.println();

        // Show rest
        System.out.printf("Rest:%d \n", restAmount);
    }
}

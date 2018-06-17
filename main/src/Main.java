import java.util.Scanner;

public class Main {
    public static void write(String[] ingredient, String[] recipies, String[] amount) {

    }

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
        String[] aIngredients = buffer.split(" ");
        if (aIngredients.length != iIngredientsSize) {
            System.out.printf("Die Anzahl Zutaten entspricht nicht der eingabe: %d != %d. \n",aIngredients.length, iIngredientsSize);
            return;
        }

        // Get next line
        System.out.printf("Geben Sie die %d Rezepte an: \n", iRecipesSize);
        buffer = scan.nextLine();

        // Recipes
        String[] aRecipes = buffer.split(" ");
        if (aRecipes.length != iRecipesSize) {
            System.out.printf("Die Anzahl Rezepte entspricht nicht der eingabe: %d != %d. \n", aRecipes.length, iRecipesSize);
            return;
        }


        // Get next line
        System.out.printf("Geben Sie die %d Mengen an: \n", iIngredientsSize);
        buffer = scan.nextLine();

        // Amounts
        String[] aAmounts = buffer.split(" ");
        if (aAmounts.length != iIngredientsSize) {
            System.out.printf("Die Anzahl Mengen entspricht nicht den Zutaten: %d != %d. \n", aAmounts.length, iIngredientsSize);
            return;
        }

        // DEBUG:
        System.out.printf("Mengen: %d  Rezepte: %d  Zutaten :%d \n", aAmounts.length, aRecipes.length, aIngredients.length);
        write(aIngredients, aRecipes, aAmounts);
    }
}

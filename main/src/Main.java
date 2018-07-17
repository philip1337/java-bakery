import java.util.Scanner;

public class Main {
    // Member variablen deklarieren
    private static String[] aZutaten;
    private static int[] aMengen;
    private static int[] aZubereitet;
    private static String[] aRezepte;

    // Main entry point
    public static void main(String [] args){
        Scanner scan = new Scanner (System.in); // Scanner für Eingaben vorbereiten
        // Inistialisieren der Variablen
        int anzZutaten;
        int anzRezepte;
        int mengen;

        //String rezept;
        String zutaten;
        String rezepte;

        // Einlesen der Zutaten und Rezepte
        System.out.println ("Anzahl Zutaten und Rezepte eingeben");
        anzZutaten = scan.nextInt(); // 4
        anzRezepte = scan.nextInt(); // 3

        scan.nextLine(); // Das es nicht überspringt

        // 2te Zeile einlesen (Zutaten eingeben) // Array Zutanten befüllen
        System.out.println ("Zutaten eingeben"); // A B F Q
        zutaten = scan.nextLine();
        aZutaten = zutaten.split (" ");

        // 3te Zeile einlesen (Rezepte eingeben) // Array Zutanten befüllen
        System.out.println ("Rezepte eingeben"); // 3A5B5F 1B1F1Q 4B6F5Q
        rezepte = scan.nextLine();
        aRezepte = rezepte.split (" ");

        // aZubereitet initialisieren
        aZubereitet = new int[aRezepte.length];

        // 4te Zeile einlesen (Mengen der Zutaten)
        System.out.println ("Mengen eingeben"); // 32 13 24 7
        aMengen = new int [anzZutaten];
        for (int i = 0; i < anzZutaten; i++){
            mengen = scan.nextInt();
            aMengen[i] = mengen;
        }

        // Rezepte berechnen
        Zubereiten(anzZutaten, anzRezepte, aRezepte);

        // Rest mengen ausgeben
        Ausgabe();
    }

    public static void Ausgabe() {
        // Rest mennge
        int rest = 0;

        // Divider
        System.out.println();

        // Rezepte anzeigen
        for(int i = 0; i < aRezepte.length; i++) {
            System.out.printf("%s:%d\n", aRezepte[i], aZubereitet[i]);
        }

        // Rest berechnen
        for(int i = 0; i < aZutaten.length; i++) {
            System.out.printf("%s: %d ",aZutaten[i], aMengen[i]);
            rest += aMengen[i];
        }

        // Separate from rest amount calculation
        System.out.println();

        // Show rest
        System.out.printf("Rest:%d \n", rest);
    }

    // Zubereiten
    public static void Zubereiten(int anzZutaten, int anzRezepte, String [] aRezepte) {
        boolean zubereiten = true;
        int strikes = 0;

        if (aRezepte.length != anzRezepte) {
            // Hier ist etwas falsch... die anzahl rezepte stimmt nicht mit der eingabe überein
            // theoretisch sollte man hier abbrechen
        }

        // Main loop solange wir zubereiten
        while (zubereiten) {
            // Reset strikes
            strikes = 0;

            // Loop
            for(int index = 0; index < anzRezepte; index++) {
                if (!BerechnenUndAbziehen(aRezepte[index], index))
                    strikes++;
            }

            // Falls keines der Rezepte mehr zubereitet werden konnte brechen wir ab...
            if (strikes >= aRezepte.length)
                zubereiten = false;
        }
    }

    // Berechnen und abziehen...
    public static boolean BerechnenUndAbziehen(String rezept, int index) {
        // Keine 2er pärchen...
        if (rezept.length() % 2 != 0)
            return false;

        // 2er pärchen sind nur erlaubt
        int aRezeptMengen[] = new int [rezept.length() / 2];
        char aRezeptZutaten[] = new char [rezept.length() / 2];
        char buffer;

        // Berechnen
        for(int i = 0; i < (rezept.length() / 2); i++){
            buffer = rezept.charAt(i*2);
            aRezeptMengen[i] = Integer.parseInt(String.valueOf(buffer));

            buffer = rezept.charAt(i*2+1);
            aRezeptZutaten[i] = buffer;
        }

        // Valid flag
        boolean valid;

        // Berechnen ob genug zutaten da sind... (wir müssen schauen ob es genug hat von jeder zutat....)
        for (int i = 0; i < aRezeptZutaten.length; i++) {
            valid = false;

            for (int i2 = 0; i2 < aZutaten.length; i2++) {
                // hier wird verglichen ob die zutat des rezeptes bei unseren zutaten existiert...
                if (aZutaten[i2].equals(String.valueOf(aRezeptZutaten[i]))) {
                    valid = true;   // Zutat wurde gefunden
                    // i ist der index für die menge die wir abziehen müssen
                    // i2 ist der index für rezept menge

                    // Falls wir nicht genug haben
                    if (aMengen[i2] < aRezeptMengen[i]) {
                        return false;
                    }
                }
            }

            // Wir können hier aufhören weil die zutat nicht existiert
            if (!valid)
                return false;
        }

        // Falls wir genug haben von allem können wir abziehen
        for (int i = 0; i < aRezeptZutaten.length; i++) {
            for (int i2 = 0; i2 < aZutaten.length; i2++) {
                // hier wird verglichen ob die zutat des rezeptes bei unseren zutaten existiert...
                if (aZutaten[i2].equals(String.valueOf(aRezeptZutaten[i]))) {
                    // i ist der index für die menge die wir abziehen müssen
                    // i2 ist der index für rezept menge

                    // Abziehen (das if ist hier eigentlich nicht nötig weil wir oben schon berechnet haben)
                    if (aMengen[i2] >= aRezeptMengen[i]) {
                        aMengen[i2] -= aRezeptMengen[i];
                    }
                }
            }
        }

        // Wir müssen wissen wieviel wir zubereitet haben hier wird erhöht
        aZubereitet[index]++;
        return true;
    }
}

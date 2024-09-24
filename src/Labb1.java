import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Mian Wang
 * @version 4.1
 * @Versionförändringar 1.Koden är logiskt korrekt och fungerar som förväntat.
 * 2.Färg och format har lagts till.
 * @Nästa versionsförbättringar:
 * Lägga till funktioner, såsom automatisk beräkning av vinst.
 * Lägga till try-catch-block.
 * Använda samlingar för att göra hanteringen mer bekväm.
 * Använda objektorienterad programmering.
 * Förbättra metodanvändningen för att öka programmets effektivitet.
 * @since 2024-09-21
 */

class Labb1Company {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final Scanner sc = new Scanner(System.in);
    private static final String[] menuChoices = {ANSI_PURPLE + "1. Ägare (visa alla, lägg till, ändra, ta bort)",
            "2. Anställd (visa alla, lägg till, ändra, ta bort)",
            "3. Skriv ut ammanställning", "0. Avsluta programmet" + ANSI_RESET};
    private static int[] employees = new int[]{};

    public static void main(String[] args) {

        // Ange restaurangens namn
        String restaurangens_name;
        while (true) {
            System.out.println(ANSI_YELLOW + "Ange restaurangens namn (minst 10 tecken)" + ANSI_RESET);
            restaurangens_name = sc.nextLine();
            if (restaurangens_name.length() <= 10) {
                System.out.println(ANSI_RED + "För kort namn.Prova igen" + ANSI_RESET);
                continue;
            }
            break;
        }
        int numberOfOwner;
        // avgör hur många ägare som ska finnas
        int[] owner = null;

        flag:
        while (true) {
            while (true) {
                System.out.println("Antal ägare?");
                numberOfOwner = Integer.parseInt(sc.nextLine());
                if (!(numberOfOwner > 0)) {
                    System.out.println(ANSI_RED + "Det måste finnas minst en ägare" + ANSI_RESET);
                    continue;
                }
                break;
            }
            // skapar ägare array
            owner = new int[numberOfOwner];
            // skapar index
            int index = 0;
            //skapar totalOwnerPercentage
            int totalOwnerPercentage = 0;
            // skriva in ownership
            if (owner.length == 1) {
                owner[0] = 100; //
                System.out.println(ANSI_RED + "ägare 1 ägarandel 100" + ANSI_RESET);
            } else {
                while (index < owner.length - 1) { // för räknar den sista ägare ownership automatiskt,owner.length måste minska 1
                    System.out.println("ange ägare" + (index + 1) + " ägarandel");
                    int ownerPercentage = Integer.parseInt(sc.nextLine());
                    if (!(ownerPercentage > 0)) {
                        System.out.println("en ägare måste äga något...");
                        continue;
                    }
                    owner[index] = ownerPercentage; // tilldeal ownership
                    totalOwnerPercentage += ownerPercentage; // räknar total ownership
                    if (totalOwnerPercentage > 100) {
                        System.out.println("ownership är över 100, du måste göra igen");
                        continue flag;
                    }
                    index++;
                }
                owner[index] = 100 - totalOwnerPercentage; // räknar den sista ownership
            }
            break flag;
        }
        System.out.println("=======");
        printAll(owner, "ägare");
        System.out.println("=======");
// employees
        System.out.println(ANSI_RED + "Välkommen till " + restaurangens_name + "!" + ANSI_RESET);

        while (true) {
            System.out.println(ANSI_GREEN + "Välj ett av dessa ment-alternativ:" + ANSI_RESET);
            for (String menuChoice : menuChoices) {
                System.out.println(menuChoice);
            }
            System.out.println("================================================================");
            System.out.println("Ange siffran för menyval");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: // hantera ägare
                    owner = subChoice(owner, "ägare");
                    break;
                case 2: // hantera anställd
                    employees = subChoice(employees, "anställd");
                    break;
                case 3: // skriv ut sammanställning
                    System.out.println("===============");
                    printSummary(owner, employees);
                    System.out.println("===============");
                    break;
                case 0: // avsluta programmet
                    egg();
                    System.exit(0);
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    //förändring ägare
    public static int[] correctOwnership(int[] array, int wantedOwnership, Boolean giveOwnership) {

        String giveOrTake = giveOwnership ? "fördelas ut" : "tas fram";
        String infoGiverOrTake = giveOwnership ? "ge till" : "ta ifrån";
        System.out.println("Det är " + wantedOwnership + " procentenheter som behöver " + giveOrTake + ".");
        System.out.println(giveOwnership ? "vilken ägare vill du ge ägarandelar till ?" : "vilken ägare vill du ta ägarandelar av ? ");
        for (int i = 0; i < array.length; i++) {//show every owner ownership
            System.out.println("ägare" + (i + 1) + ":" + array[i] + "%");
        }
        // tilldela ownership till ägare
        while (true) {
            System.out.println("Ange siffran för vilken ägare du vill " + infoGiverOrTake);
            int index = Integer.parseInt(sc.nextLine());
            if (!(index > 0 && index <= array.length)) { // index giltighetverifiering
                System.out.println("Felaktigt val. Prova igen");
                continue;
            }
            if ((array[index - 1] == 1) && (!giveOwnership)) {
                System.out.println(ANSI_RED + "Ägare" + index + " har bara 1% kvar. Du kan inte ta bort det sista ägarandelar " + ANSI_RESET);
                continue;
            }
            System.out.println("Hur många preocetenheter vill du " + giveOrTake + " ägare " + index + "?");
            int correctOwnership = Integer.parseInt(sc.nextLine());
            //bedömning de ändrade aktierna är mindra wantedOwnership och större än 1
            if (correctOwnership > wantedOwnership || correctOwnership < 1) {
                System.out.println(ANSI_RED + "du kan inte ta mer än " + wantedOwnership + " och preocetenheter måste störe än noll" + ANSI_RESET);
                continue;
            }
            // bestäm om den faktiska parametern är ägare eller anställd, om ägare,
            // man behöver see till de aktier som vill flyttas är mindra de aktier som redan finns
            if (!giveOwnership && correctOwnership >= array[index - 1]) {
                System.out.println("du kan endast ta " + (correctOwnership - array[index - 1] - 1) + " procentenheter från ägare");
                continue;
            }

            index--;

            if (giveOwnership) {
                array[index] += correctOwnership;
            } else {
                array[index] -= correctOwnership;
            }
            wantedOwnership -= correctOwnership;
            if (wantedOwnership != 0) {
                System.out.println("Det är " + wantedOwnership + " procentenheter som behöver " + giveOrTake + ".");
                continue;
            }
            return array;
        }
    }

    //lägger till ägare eller anställad
    public static int[] addNew(int[] array, String elementParam) {
        if (elementParam.equalsIgnoreCase("anställd")) {
            int salary;
            while (true) {
                System.out.println("Ange den anställdes timlön > ");
                salary = Integer.parseInt(sc.nextLine());
                if (salary < 0) {
                    System.out.println("Timlönen måste vara minst 0 Kr per timme");
                    continue;
                }
                break;
            }
            if (array.length == 0) {
                return new int[]{salary};
            }
            int[] newArray = Arrays.copyOf(array, array.length + 1);
            newArray[array.length] = salary;
            return newArray;
        }

        int ownership;
        while (true) {
            System.out.println("Ange ägarens ägarandel");
            ownership = Integer.parseInt(sc.nextLine());
            if (ownership < 0 || ownership > 100) {
                System.out.println("Felaktig ägarandel. Det måste vara mer än 0% och imndre än 100%");
                continue;
            }
            correctOwnership(array, ownership, false);
            int[] newArray = Arrays.copyOf(array, array.length + 1);
            newArray[array.length] = ownership;
            return newArray;
        }
    }

    // skriv ut sammanställning
    public static void printSummary(int[] ownerArray, int[] employeesArray) {
        int totalOwnership = 0;
        for (int i = 0; i < ownerArray.length; i++) {
            totalOwnership += ownerArray[i];
            System.out.println("Ägare" + (i + 1) + ": " + ownerArray[i] + "%");
        }
        System.out.println("Totalt ägarandel: " + totalOwnership + "%");
        int totalHourSalaries = 0;
        for (int i = 0; i < employeesArray.length; i++) {
            totalHourSalaries += employeesArray[i];
            System.out.println("Anställd" + (i + 1) + ": " + employeesArray[i] + " Kr/timme");
        }
        System.out.println("Totala timkostnad anställda: " + totalHourSalaries + " Kr/timme");
    }

    //skriv ut info av ägare eller anställd
    public static void printAll(int[] array, String element) {
        if (array.length == 0) {
            System.out.println("Det finns inga " + element + " inlagda");
            return;
        }
        String printPrefix;
        if (element.equalsIgnoreCase("ägare")) {
            printPrefix = "%";
        } else {
            printPrefix = "kr/h";
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(ANSI_GREEN + " " + (i + 1) + ": " + array[i] + printPrefix + ANSI_RESET);
        }
    }

    // ta bort ägare eller anställd
    public static int[] remove(int[] array, String elementParam) {
        if (elementParam.equalsIgnoreCase("ägare") && array.length == 1) {
            System.out.println("Du kan inte ta bort den enda ägaren i företaget");
            return array;
        }
        System.out.println("Vilken " + elementParam + " vill du ta bort?");
        int inputNumber;
        do {
            printAll(array, elementParam);
            System.out.println("Ange siffran på den " + elementParam + " du vill ta bort");
            inputNumber = Integer.parseInt(sc.nextLine()) - 1;
            if (inputNumber < 0 || inputNumber >= array.length) {
                System.out.println("Ange en giltig siffra");
                continue;
            }
            break;
        } while (true);
        int[] newArray = new int[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i == inputNumber) {
                continue;
            }
            newArray[j++] = array[i];
        }
        if (!elementParam.equalsIgnoreCase("ägare")) {
            return newArray;
        }
        return correctOwnership(newArray, array[inputNumber], true);
    }

    // ändra ägare
    public static int[] change(int[] array, String elementParam) {
        int inputNumber;
        boolean giveAway;
        if (array.length <= 1) {
            System.out.println("endas en ägare, du måste lägg till mer ägare");
            return array;
        }
        while (true) {
            System.out.println("Vilken" + elementParam + "vill du ändra på");
            printAll(array, elementParam);

            System.out.println("Ange siffran på den du vill ändra på >");
            inputNumber = Integer.parseInt(sc.nextLine()) - 1;
            if (inputNumber < 0 || inputNumber >= array.length) {
                System.out.println("Ange en giltig siffra");
                continue;
            }
            break;
        }

        if (!elementParam.equalsIgnoreCase("ägare")) {
            int salary;
            while (true) {
                System.out.println("Ange den anställdes nya timlön");
                salary = Integer.parseInt(sc.nextLine());
                if (salary < 0) {
                    System.out.println("Timlönen måste vara minst 0 Kr per timme");
                    continue;
                }
                array[inputNumber] = salary;
                return array;
            }

        }
        int ownership;
        while (true) {
            System.out.println("Ange ägarens nys ägarandel > ");
            ownership = Integer.parseInt(sc.nextLine());
            if (ownership < 0 || ownership > 100) {
                System.out.println("Felaktig ägarandel. Det måste vara mer än 0% och imndre än 100%");
                continue;
            }
            break;
        }
        if (ownership > array[inputNumber]) {
            ownership -= array[inputNumber];
            array[inputNumber] += ownership;
            giveAway = false;
        } else {
            int temp = array[inputNumber];
            array[inputNumber] = ownership;
            ownership = temp - ownership;
            giveAway = true;
        }
        int[] tempArr = new int[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i == inputNumber) {
                continue;
            }
            tempArr[j++] = array[i];
        }
        correctOwnership(tempArr, ownership, giveAway);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i == inputNumber) {
                continue;
            }
            array[i] = tempArr[j];
            j++;
        }
        return array;
    }

    //subMeny
    public static int[] subChoice(int[] arrayParam, String elementParam) {
        while (true) {
            System.out.println(ANSI_GREEN + "Vad vill du göra med " + elementParam + "?");
            System.out.println("================================================================");
            System.out.println("1. Visa alla " + elementParam + ".");
            System.out.println("2. Lägg till en ny " + elementParam + ".");
            System.out.println("3. Ändra en " + elementParam + ".");
            System.out.println("4. Ta bort en " + elementParam + ".");
            System.out.println("0. Gå tillbaka till huvudmenyn.");
            System.out.print("Ange siffran för menyval > " + ANSI_RESET);
            int menuSubChoice = Integer.parseInt(sc.nextLine());
            switch (menuSubChoice) {
                case 1: // visa ägare eller anställd
                    System.out.println("=========");
                    printAll(arrayParam, elementParam);
                    System.out.println("=========");
                    break;
                case 2: // lägger till ägare eller anställd
                    arrayParam = addNew(arrayParam, elementParam);
                    break;
                case 3: // ändra ägare eller anställd
                    arrayParam = change(arrayParam, elementParam);
                    break;
                case 4: // ta bort
                    arrayParam = remove(arrayParam, elementParam);
                    break;
                case 0: // återgå till huvudmenyn
                    return arrayParam;
                default:
                    System.out.println("Ogiltigt val. Försök igen...");
            }
        }
    }

    public static void egg() {
        System.out.println("Tack för besöket! ");
        System.out.println("  *****  ");
        System.out.println(" *     * ");
        System.out.println("*  O O  *");
        System.out.println("*   ^   *");
        System.out.println(" * '-' * ");
        System.out.println("  *****  ");
    }
}




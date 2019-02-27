package com.wictorsundstrom.Slutuppgift;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SlutUppgift {
    private static ArrayLists lists = new ArrayLists();

    public static void main(String[] args) {
        while (!menu()) {}
    }

    private static boolean menu() {
        showMenu();
        switch (scannerCheck(scanner().nextLine())) {
            case 1:
                // add
                System.out.println("What do you want to add?");
                add(scanner().nextLine());
                break;
            case 2:
                // search
                System.out.println("What do you want to search for?");
                search(scanner().nextLine());
                break;
            case 3:
                // edit
                System.out.println("What do you want to change?");
                edit(scanner().nextLine());
                break;
            case 4:
                // remove
                System.out.println("What do you want to remove?");
                remove(scanner().nextLine());
                break;
            case 5:
                // sort
                sort();
                break;
            case 6:
                // count words
                wordFunction(0);
                System.out.println("Total words in list: " + lists.getSum());
                break;
            case 7:
                // Reverse
                System.out.println(reverseRecursion(lists.getList()));
                break;
            case 8:
                //Crypting
                System.out.println("What do you want to encrpyt?");
                System.out.println(cryptoFunction(scanner().nextLine()));
                break;
            case 0:
                return true;
            default:
                System.out.println("Incorrect input");
                break;
        }
        return false;
    }

    private static void showMenu(){
        System.out.println("Menu\n" +
                "1 - Add text to list\n" +
                "2 - Search through list\n" +
                "3 - Edit list\n" +
                "4 - Remove text from list\n" +
                "5 - Sort the list\n" +
                "6 - Show number of words in the list\n" +
                "7 - Reverse the list\n" +
                "8 - Encrypt a word\n" +
                "0 - Exit");
    }

    // Då setList förväntar sig en ArrayList i sin setter
    // så måste jag först skapa en nu som innehåller värdet av den nuvarande listan och sedan + det som man vill lägga till.
    private static void add(String input) {
        ArrayList<String> setNewList = new ArrayList<>(lists.getList());

        setNewList.add(input + " " + dateMethod());
        lists.setList(setNewList);
        System.out.println(lists.getList().get(lists.getList().size()-1)  + " has been added");

        numberToFibonacci(lists.getFibonacci().size());
    }

    // Kollar om input finns eller inte.
    // Hittar den en input så skriver den ut vad du sökte efter och vilken plats den ligger på
    private static void search(String input) {
        if (indexSearched(input) == -1) {
            System.out.println("Input doesn't exist");
        } else {
            System.out.println(input + " was found at index " + indexSearched(input));
        }
    }

    // Loopar listan och kollar om det man söker på finns i någon av strängarna i listan (- datumet i varje sträng)
    // och om den hittar en så returnerar den index på det man sökte efter.
    private static int indexSearched (String input) {
        for(int i = 0; i < lists.getList().size(); i++) {
            if (lists.getList().get(i).substring(0, lists.getList().get(i).lastIndexOf(" ")).contains(input)) {
                return i;
            }
        }
        return -1;
    }

    // Kollar om det man vill ändra finns i listan.
    // Finns det så bygger den upp en ny arraylista där den ersätter det förra elementet med ett nytt
    // och sedan sätter den nya listan som list (via setter setList).
    private static void edit(String input) {
        ArrayList<String> setNewList = new ArrayList<>(lists.getList());

        if (indexSearched(input) == -1) {
            System.out.println("Input doesn't exist");
        } else {
            System.out.println("New input: ");

            setNewList.set(indexSearched(input), input = scanner().nextLine() +  " " + dateMethod());
            lists.setList(setNewList);

            System.out.println(input + " has been added");

            numberToFibonacci(lists.getFibonacci().size()); }
    }

    // Kollar om det man vill ta bort finns
    // Finns det så bygger den upp en ny arraylista där den tar bort elementet och sedan
    // sätter den nya listan som list (via setter setList)
    private static void remove(String input) {
        ArrayList<String> setNewList = new ArrayList<>(lists.getList());

       if (indexSearched(input) == -1) {
           System.out.println("Input doesn't exist");
       }else {
           setNewList.remove(indexSearched(input));
           lists.setList(setNewList);

           System.out.println(input +" has been removed");
       }
   }

   // Val om man vill sortera via A-Z eller Datum
    private static void sort() {
        System.out.println("1. Sort text A-Z\n2. Sort text by date");
        switch (scannerCheck(scanner().nextLine())) {
            case 1:
                collSort();
                break;
            case 2:
                dateSort();
                break;
        }
    }

    // Sorterar elementen med 0-9 - A-Z kriterier.
    private static void collSort(){
        //Before sort
        System.out.println("Before Sort: ");
        System.out.println(lists.getList());
        //Sort the list
        Collections.sort(lists.getList());
        //After Sort
        System.out.println("After Sort: ");
        System.out.println(lists.getList());
    }

    // Sorterar elementen med datum som kriterie.
    // Den jämför sträng 1 och 2 där den tar bort allt utom det som ligger efter det sista mellanslaget
    // (i vårat fall, ALLTID datumet)
    // Och sedan fortsätter den till den har gått igenom allt.
    private static void dateSort(){
        //Before sort
        System.out.println("Before Sort: ");
        System.out.println(lists.getList());
        //Sort the list
        Collections.sort(lists.getList(), Comparator.comparing(list -> list.substring(list.lastIndexOf(" "))));
        //After Sort
        System.out.println("After Sort: ");
        System.out.println(lists.getList());
    }

    // Summerar alla returns som kommer från countWords och sätter värdet av sum till det totala värdet av sum.
    // (sum sätts via setter - setSum)
    private static void wordFunction(int sum){
        for(String words : lists.getList()){
            sum += countWords(words);
        }
        lists.setSum(sum);
    }

    // Kollar om det finns mellanslag, finns det så skär den bort ordet + mellanslaget och returnerar 1 och
    // sen så kallar den på sig själv igen med det nya order och kollar igen om det fortfarande finns mellanslag.
    private static int countWords (String sentence) {
        if (sentence.isEmpty()) {
            return 0;
        }
        int space = sentence.indexOf(" ");

        if (space != -1) {
            return 1 + countWords(sentence.substring(space + 1));
        }

        return 0;
    }

    // Scanner. Returnerar en scanner varje gång den kallas.
    private static Scanner scanner() {
        Scanner scan = new Scanner(System.in);
        return scan;
    }

    // Kollar om input är mellan 0-9 (används för meny valen).
    private static int scannerCheck(String input) {
        if (input.matches("[0-9]+")) {
            return Integer.parseInt(input);
        } else {
            return 99;
        }
    }

    // Skapar en sträng med timme, minut och sekund.
    private static String dateMethod(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    // Kollar hur många värden som finns i fibonacci listan, är det 0 eller 1 så ska den lägga in 1
    // Annars ska den plusa de 2 sista elementen i listan och lägga in summan.
    // När den ska uppdatera fibonacci så skapar den en ny lista för att lägga in det nya värdet och sedan
    // sätter den fibonacci till den nya listan (via setter setFibonacci).
    private static void numberToFibonacci(int n) {
        int sum;
        ArrayList<Integer> setNewFibonacci = new ArrayList<>(lists.getFibonacci());

        if (lists.getFibonacci().size() == 0 || lists.getFibonacci().size() == 1) {
            sum = 1;
        } else{
            sum = lists.getFibonacci().get(n-1) + lists.getFibonacci().get(n-2);
        }

        setNewFibonacci.add(sum);
        lists.setFibonacci(setNewFibonacci);
        System.out.println(lists.getFibonacci());

        checkIfEven(sum);
    }

    // kollar om det nya värdet som lagts till i fibonacci är jämt eller udda
    private static void checkIfEven(int numToCheck){
        if(numToCheck % 2 == 0){
            System.out.println(numToCheck + " is even");
        } else {
            System.out.println(numToCheck + " is odd");
        }
    }

    // Krypterar texten med hjälp av MessageDigest med kriteriet MD5
    // den tar inputen och gör om till bytes som MessageDigest sen gör om till en ny byte array med ett nytt hash värde
    private static String cryptoFunction(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return ByteArrayToString(digest).toUpperCase();

        }catch (NoSuchAlgorithmException ex){
            System.out.println("Error, Try again");
            return "";
        }
    }

    // Gör om bytesen som kommer ut från cryptoFunction och gör om de till en hexadecimal och sedan därifrån till en sträng.
    private static String ByteArrayToString(byte[] cryptedBytes) {
        StringBuilder hex = new StringBuilder(cryptedBytes.length);
        for (byte character : cryptedBytes) {
            hex.append(String.format("%02x", character));
        }
        return hex.toString();
    }

    //Returnerar listan reversedList efter att den har tagit in list via getList när metoden anroppas.
    private static List<String> reverseRecursion(List<String> list){
        if(list.size()<=1){
            return list;
        }
        List<String> reversedList = new ArrayList<>();
        reversedList.add(list.get(list.size() -1));
        reversedList.addAll(reverseRecursion(list.subList(0, list.size() -1)));
        return reversedList;
    }
}

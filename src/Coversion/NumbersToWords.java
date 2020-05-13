package Coversion;

import java.util.ArrayList;
import java.util.List;

public class NumbersToWords {

    public static String getAmountInWords (double sum){

        int amount = (int) Math.floor(sum);
        double remainder = sum - amount;
        remainder = Math.round(remainder * 100.00) / 100.00;
        remainder*= 100;
        int remainingInt = (int) remainder;
        String [] thousands = {"", "tūkstantis", "tūkstančiai", "tūkstančių"};
        String [] millions = {"", "milijonas", "milijonai", "milijonų"};
        String [] ending = {"", "EUR", "EUR", "EUR"};

        StringBuilder amountInWords = new StringBuilder();

        List<String> tripletsInWords = new ArrayList<>();
        int numberOfTriplets = 0;
        while (amount != 0){
            if (numberOfTriplets == 0){
                tripletsInWords.add(getTripletInWords(amount % 1000, ending));
            }
            if (numberOfTriplets == 1){
                tripletsInWords.add(getTripletInWords(amount % 1000, thousands));
            }
            if (numberOfTriplets == 2){
                tripletsInWords.add(getTripletInWords(amount % 1000, millions));
            }
            amount /= 1000;
            numberOfTriplets++;
        }
        for (int i = tripletsInWords.size()-1; i >= 0; i--){
            amountInWords.append(tripletsInWords.get(i) + " ");
        }
        amountInWords.append(remainingInt + " ct.");
        String words = amountInWords.toString();
        words = words.substring(0, 1).toUpperCase() + words.substring(1);
        return words;
    }

    private static String getTripletInWords (int triplet, String[] name){
        String [] vienetai = {"","vienas", "du", "trys", "keturi", "penki", "šeši", "setyni", "aštuoni", "devyni"};
        String [] kiti = {"šimtas", "šimtai"};
        int numberOfdigits = 0;
        List<Integer> list = new ArrayList<>();
        while (triplet != 0){
            list.add(triplet % 10);
            triplet /= 10;
            numberOfdigits++;
        }
        StringBuilder amountInWords = new StringBuilder();

        if (numberOfdigits == 3){
            int firstNumber = list.get(numberOfdigits -1);
            int secondNumber = list.get(numberOfdigits - 2);
            int thirdNumber = list.get(numberOfdigits - 3);
            if (firstNumber == 1){
                amountInWords.append(kiti[0]);
            } else {
                amountInWords.append(vienetai[list.get(numberOfdigits - 1)]);
                amountInWords.append(" " + kiti[1]);
            }
            amountInWords.append(" " + twoDecimalToString(secondNumber, thirdNumber, name));
        } else if (numberOfdigits == 2){
            amountInWords.append(twoDecimalToString(list.get(numberOfdigits-1), list.get(numberOfdigits-2), name));;
        } else {
            amountInWords.append(vienetai[list.get(0)]);
            if (list.get(0) == 1){
                amountInWords.append(" " + name[1]);
            } else {
                amountInWords.append(" " + name[2]);
            }
        }
        return amountInWords.toString();
    }

    private static String twoDecimalToString (int first, int second, String[] name){
        String [] vienetai = {"","vienas", "du", "trys", "keturi", "penki", "šeši", "setyni", "aštuoni", "devyni"};
        String [] niolika = {"", "vienuolika", "dvylika", "trylika", "keturiolika", "penkiolika", "šešiolika", "septyniolika", "aūtuoniolika", "devyniolika"};
        String [] desimtys = {"", "dešimt", "dvidešimt", "trisdešimt", "keturiasdešimt", "penkiasdešimt", "šešiasdešimt", "septyniasdešimt", "aštuoniasdešimt", "devyniasdešimt"};
        String [] kiti = {"šimtas", "šimtai"};
        StringBuilder inWords = new StringBuilder();

        if (first == 0){
            if (second == 1){
                inWords.append(vienetai[second] + " " + name[1]);
            } else {
                inWords.append(vienetai[second] + " " + name[2]);
            }
        } else if (first == 1 && second == 0){
            inWords.append(desimtys[first] + " " + name[2]);
        } else if (second == 0){
            inWords.append(desimtys[first] + " " + name[3]);
        } else if (first == 1){
            inWords.append(niolika[second] + " " + name[3]);
        } else {
            inWords.append(desimtys[first] + " " + vienetai[second]);
            if (second == 1){
                inWords.append(" " + name[1]);
            } else {
                inWords.append(" " + name[2]);
            }
        }
        return inWords.toString();
    }
}

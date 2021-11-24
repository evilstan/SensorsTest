package com.evilstan.sensorstest;

import java.util.Locale;

public class MorseConvertor {

    public MorseConvertor(){

    }

    public long[] textToInterval(String text){
        long[] result = new long[0];
        return result;
    }

    public String[] textToMorse(String text) {
        return convertMorse(text);

    }

    public static String[] convertTextTomMorse(String text) {
        return convertMorse(text);
    }

    private static String[] convertMorse(String text) {
        String[] textArray = text.split("");
        String[] result = new String[textArray.length];

        for (int i = 0; i < textArray.length; i++) {
            String letter = textArray[i];
            result[i] = getMorseCodeOfLetter(letter);
        }

        return result;
    }

    private static String getMorseCodeOfLetter(String s) {
        s = s.toLowerCase(Locale.ROOT);
        switch (s) {
            case "1":
                return ".----";
            case "2":
                return "..---";
            case "3":
                return "...--";
            case "4":
                return "....-";
            case "5":
                return ".....";
            case "6":
                return "-....";
            case "7":
                return "--...";
            case "8":
                return "---..";
            case "9":
                return "----.";
            case "0":
                return "-----";
            case "a":
                return ".-";
            case "b":
                return "-...";
            case "c":
                return "-.-.";
            case "d":
                return "-..";
            case "e":
                return ".";
            case "f":
                return "..-.";
            case "g":
                return "--.";
            case "h":
                return "....";
            case "i":
                return "..";
            case "k":
                return ".---";
            case "l":
                return ".-..";
            case "m":
                return "--";
            case "n":
                return "-.";
            case "o":
                return "---";
            case "p":
                return ".--.";
            case "q":
                return "--.-";
            case "r":
                return ".-.";
            case "s":
                return "...";
            case "t":
                return "-";
            case "u":
                return "..-";
            case "v":
                return "...-";
            case "w":
                return ".--";
            case "x":
                return "-..-";
            case "y":
                return "-.--";
            case "z":
                return "--..";
            default:
                return " ";
        }
    }
}

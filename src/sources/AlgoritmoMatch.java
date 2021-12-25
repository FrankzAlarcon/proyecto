/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Frankz
 */
public class AlgoritmoMatch {

    public static String bruteForceManyMatches(String texto, String patron, int linea){
        int longTexto = texto.length();
        int longPatron = patron.length();
        String result = "";
        if(longPatron > longTexto){
            return result;
        }
        int indexPatron;
        for (int i = 0; i <= longTexto-longPatron; i++) {
            indexPatron = 0;
            while(indexPatron < longPatron && texto.charAt(i + indexPatron) == patron.charAt(indexPatron)){
                indexPatron++;
            }
            if(indexPatron == longPatron){
                result += " en la linea " + linea + ", en la posicion: " + i + " hasta " + (i + longPatron - 1) + "\n";
            }            
        }
        return result;
    }

    private static int[] funcionFallo(String patron) {
        int longPatron = patron.length();
        int[] fail = new int[longPatron];
        int mainIndex = 1;
        int countRepetitionIndex = 0;
        while (mainIndex < longPatron) {
            if (patron.charAt(mainIndex) == patron.charAt(countRepetitionIndex)) {
                fail[mainIndex] = countRepetitionIndex + 1;
                countRepetitionIndex++;
                mainIndex++;
            } else if (countRepetitionIndex > 0) {
                countRepetitionIndex = fail[countRepetitionIndex - 1];
            } else {
                mainIndex++;
            }
        }
        return fail;
    }

    public static String KMPmatcher(String texto, String patron, int linea){
        int longTexto = texto.length();
        int longPatron = patron.length();
        String result = "";
        if(longPatron == 0 || longPatron > longTexto){
            return result;
        }
        int[] fail = funcionFallo(patron);
        int textIndex = 0;
        int patternIndex = 0;
        while(textIndex < longTexto){
            if(texto.charAt(textIndex) == patron.charAt(patternIndex)){
                if(patternIndex == (longPatron - 1)){
                    result += " en la linea " + linea + ", en la posicion: " + (textIndex - longPatron + 1) + " hasta " + textIndex + "\n";
                    patternIndex = -1;
                }
                textIndex++;
                patternIndex++;
            } else if(patternIndex > 0){
                patternIndex = fail[patternIndex - 1];
            } else {
                textIndex++;
            }
        }
        return result;
    }

    public static String BoyerMooreManyMatches(String texto, String patron, int linea) {
        String result = "";
        //Longitudes del patron y del texto
        int longTexto = texto.length();
        int longPatron = patron.length();
        if (longPatron == 0 || longPatron > longTexto) {
            return result;//-1;
        }
        //Tabla D1
        Map<Character, Integer> vectorCaracteres = new HashMap<>();
        for (int i = 0; i < longTexto; i++) {
            vectorCaracteres.put(texto.charAt(i), -1);
        }
        for (int i = 0; i < longPatron; i++) {
            vectorCaracteres.put(patron.charAt(i), i);
        }

        int indexTexto = longPatron - 1;
        int indexPatron = longPatron - 1;
        //int indexTexto = 0;
        boolean coincidencia = false;
        while (indexTexto < longTexto) {
            if (!coincidencia && texto.charAt(indexTexto) == patron.charAt(indexPatron)) {
                if (indexPatron == 0) {
                    result += " en la linea " + linea + ", en la posicion: " + indexTexto + " hasta " + (indexTexto + longPatron - 1) + "\n";
                    indexPatron = longPatron;
                    coincidencia = true;
                }
                indexTexto--;
                indexPatron--;
            } else {
                if (coincidencia) {
                    indexTexto += longPatron + 1;
                    coincidencia = false;
                } else {
                    indexTexto += longPatron - Math.min(indexPatron, 1 + vectorCaracteres.get(texto.charAt(indexTexto)));
                }
                indexPatron = longPatron - 1;
            }
        }
        return result;
    }
}

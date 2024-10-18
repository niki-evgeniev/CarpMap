package com.example.carpmap.Utility;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CryptoBgnToEng {

    public String convertCyrillicToLatin(String name){
        Map<Character, String> cyrToLatMap = new HashMap<>();
        cyrToLatMap.put('А', "A");
        cyrToLatMap.put('Б', "B");
        cyrToLatMap.put('В', "V");
        cyrToLatMap.put('Г', "G");
        cyrToLatMap.put('Д', "D");
        cyrToLatMap.put('Е', "E");
        cyrToLatMap.put('Ж', "Zh");
        cyrToLatMap.put('З', "Z");
        cyrToLatMap.put('И', "I");
        cyrToLatMap.put('Й', "Y");
        cyrToLatMap.put('К', "K");
        cyrToLatMap.put('Л', "L");
        cyrToLatMap.put('М', "M");
        cyrToLatMap.put('Н', "N");
        cyrToLatMap.put('О', "O");
        cyrToLatMap.put('П', "P");
        cyrToLatMap.put('Р', "R");
        cyrToLatMap.put('С', "S");
        cyrToLatMap.put('Т', "T");
        cyrToLatMap.put('У', "U");
        cyrToLatMap.put('Ф', "F");
        cyrToLatMap.put('Х', "H");
        cyrToLatMap.put('Ц', "Ts");
        cyrToLatMap.put('Ч', "Ch");
        cyrToLatMap.put('Ш', "Sh");
        cyrToLatMap.put('Щ', "Sht");
        cyrToLatMap.put('Ъ', "A");
        cyrToLatMap.put('Ь', "");
        cyrToLatMap.put('Ю', "Yu");
        cyrToLatMap.put('Я', "Ya");
        cyrToLatMap.put('а', "a");
        cyrToLatMap.put('б', "b");
        cyrToLatMap.put('в', "v");
        cyrToLatMap.put('г', "g");
        cyrToLatMap.put('д', "d");
        cyrToLatMap.put('е', "e");
        cyrToLatMap.put('ж', "zh");
        cyrToLatMap.put('з', "z");
        cyrToLatMap.put('и', "i");
        cyrToLatMap.put('й', "y");
        cyrToLatMap.put('к', "k");
        cyrToLatMap.put('л', "l");
        cyrToLatMap.put('м', "m");
        cyrToLatMap.put('н', "n");
        cyrToLatMap.put('о', "o");
        cyrToLatMap.put('п', "p");
        cyrToLatMap.put('р', "r");
        cyrToLatMap.put('с', "s");
        cyrToLatMap.put('т', "t");
        cyrToLatMap.put('у', "u");
        cyrToLatMap.put('ф', "f");
        cyrToLatMap.put('х', "h");
        cyrToLatMap.put('ц', "ts");
        cyrToLatMap.put('ч', "ch");
        cyrToLatMap.put('ш', "sh");
        cyrToLatMap.put('щ', "sht");
        cyrToLatMap.put('ъ', "a");
        cyrToLatMap.put('ь', "");
        cyrToLatMap.put('ю', "yu");
        cyrToLatMap.put('я', "ya");
        cyrToLatMap.put(' ', "-");

        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            result.append(cyrToLatMap.getOrDefault(c, String.valueOf(c)));
        }

        return result.toString();
    }
}

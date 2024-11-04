package com.example.carpmap.Utility;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConvertorBgToEn {

    public String convertCyrillicToLatin(String name) {
        Map<Character, String> convertor = new HashMap<>();
        convertor.put('А', "A");
        convertor.put('Б', "B");
        convertor.put('В', "V");
        convertor.put('Г', "G");
        convertor.put('Д', "D");
        convertor.put('Е', "E");
        convertor.put('Ж', "Zh");
        convertor.put('З', "Z");
        convertor.put('И', "I");
        convertor.put('Й', "Y");
        convertor.put('К', "K");
        convertor.put('Л', "L");
        convertor.put('М', "M");
        convertor.put('Н', "N");
        convertor.put('О', "O");
        convertor.put('П', "P");
        convertor.put('Р', "R");
        convertor.put('С', "S");
        convertor.put('Т', "T");
        convertor.put('У', "U");
        convertor.put('Ф', "F");
        convertor.put('Х', "H");
        convertor.put('Ц', "Ts");
        convertor.put('Ч', "Ch");
        convertor.put('Ш', "Sh");
        convertor.put('Щ', "Sht");
        convertor.put('Ъ', "A");
        convertor.put('Ь', "");
        convertor.put('Ю', "Yu");
        convertor.put('Я', "Ya");
        convertor.put('а', "a");
        convertor.put('б', "b");
        convertor.put('в', "v");
        convertor.put('г', "g");
        convertor.put('д', "d");
        convertor.put('е', "e");
        convertor.put('ж', "zh");
        convertor.put('з', "z");
        convertor.put('и', "i");
        convertor.put('й', "y");
        convertor.put('к', "k");
        convertor.put('л', "l");
        convertor.put('м', "m");
        convertor.put('н', "n");
        convertor.put('о', "o");
        convertor.put('п', "p");
        convertor.put('р', "r");
        convertor.put('с', "s");
        convertor.put('т', "t");
        convertor.put('у', "u");
        convertor.put('ф', "f");
        convertor.put('х', "h");
        convertor.put('ц', "ts");
        convertor.put('ч', "ch");
        convertor.put('ш', "sh");
        convertor.put('щ', "sht");
        convertor.put('ъ', "a");
        convertor.put('ь', "");
        convertor.put('ю', "yu");
        convertor.put('я', "ya");
        convertor.put(' ', "-");

        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            result.append(convertor.getOrDefault(c, String.valueOf(c)));
        }

        return result.toString();
    }
}

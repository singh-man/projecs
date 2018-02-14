package org.utils;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by msingh on 22/11/2016.
 */
public class StringUtils {

    /**
     * Use camelCaseToTitleCase(..)
     * @param a
     * @return
     */
    @Deprecated
    public static String _camelCaseToTitleCase(String a) {
        StringBuilder sb = new StringBuilder();
        char[] ch = a.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if(i == 0) {
                sb.append(Character.toUpperCase(ch[i]));
            } else if (Character.isUpperCase(ch[i])) {
                if(ch[i-1] == ' ') {
                    sb.append(ch[i]);
                } else {
                    sb.append(' ').append(ch[i]);
                }
            } else {
                sb.append(ch[i]);
            }
        }
        return sb.toString().trim();
    }

    public static String camelCaseToTitleCase(String a) {
        if (!a.matches("^[a-zA-Z0-9]+")) return a;
        String[] args = a.split("(?=\\p{Lu})");
        StringBuilder sb = new StringBuilder(Character.toUpperCase(args[0].charAt(0)) + args[0].substring(1));
        IntStream.range(1, args.length).forEach(i -> sb.append(" ").append(args[i]));
        return sb.toString().trim();
    }

    @Test
    public void testCamelCaseToTitleCase() {
        assertEquals("abc;def", camelCaseToTitleCase("abc;def"));
        assertEquals("Abc Def", camelCaseToTitleCase("abcDef"));
        assertEquals("Abc D E F", camelCaseToTitleCase("abcDEF"));
        assertEquals("abc def", camelCaseToTitleCase("abc def"));
        assertEquals("Abc9def", camelCaseToTitleCase("abc9def"));
        assertEquals("Abc9 Def", camelCaseToTitleCase("abc9Def"));
        assertEquals("Abc Def", camelCaseToTitleCase("Abc Def"));
        assertEquals("9Abc Def", camelCaseToTitleCase("9Abc Def"));
        assertEquals("9abc Def", camelCaseToTitleCase("9abcDef"));
        assertEquals("Aabc/Def", camelCaseToTitleCase("Aabc/Def"));
        assertEquals("Aabc Def G H Ijkl M Nop", camelCaseToTitleCase("AabcDefGHIjklMNop"));
    }

    public static String convertToProperCase(String a) {
        StringBuilder sb = new StringBuilder();
        String[] names = a.toLowerCase().split(" ");
        for(String name : names){
            if(name.equals("")) continue;
            Character f = name.charAt(0);
            sb.append(Character.toUpperCase(f));
            if(name.length()>1){
                sb.append(name.substring(1, name.length()));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    @Test
    public void testConvertToProperCase() {
        assertEquals("Abc;def", convertToProperCase("abc;def"));
        assertEquals("Abcdef", convertToProperCase("abcDef"));
        assertEquals("Abcdef", convertToProperCase("abcDEF"));
        assertEquals("Abc Def", convertToProperCase("abc def"));
        assertEquals("Abc9def", convertToProperCase("abc9def"));
        assertEquals("Abc9def", convertToProperCase("abc9Def"));
        assertEquals("Abc9 Def", convertToProperCase("abc9 def"));
    }
}

package org;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

public class HashSetVsTreeSet {

    private String generateRandomStrings() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 70;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @Test
    public void testGenerateRandomStrings() {
        String generatedString = generateRandomStrings();
        System.out.println(generatedString);
    }

    private void prepareSetData(Set<String> s) {
        IntStream.range(0, 500).forEach(i -> s.add(i + "." + generateRandomStrings()));
        s.add("This is the text to be searched and find for me.");
    }

    private Set<String> prepareHashSet() {
        Set<String> s = new HashSet<>();
        prepareSetData(s);
        return s;
    }

    private Set<String> prepareTreeSet() {
        Set<String> s = new TreeSet<>();
        prepareSetData(s);
        return s;
    }

    private void checkSetPerformance(Set<String> s) {
        long t1 = System.nanoTime();
        s.contains("This is the text to be searched and find for me.");
        System.err.println("Time taken. " + (System.nanoTime() - t1));
    }

    public static void main(String[] args) {
        HashSetVsTreeSet o = new HashSetVsTreeSet();
        Set<String> set = o.prepareHashSet();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter e only if u wanna exit.");
        String s = scan.next();
        while (!s.equals("e")) {
            o.checkSetPerformance(set);
            System.out.println("Enter e only if u wanna exit.");
            s = scan.next();
        }
    }

}

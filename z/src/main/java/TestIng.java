
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestIng {

    private static Instrumentation ins;

    private static void lambda_2() {
        class B {

            String b;

            B(String b) {
                this.b = b;
            }
        }
        class A {

            String a;
            B b;

            A(String a) {
                this.a = a;
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 97 * hash + Objects.hashCode(this.a);
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                final A other = (A) obj;
                if (!Objects.equals(this.a, other.a)) {
                    return false;
                }
                return true;
            }

            @Override
            public String toString() {
                return a;
            }
        }
        List<A> aa = Arrays.asList(new A("c"), new A("b"), new A("c"), new A("i"));
        aa.sort((e1, e2) -> {
            return 0;
        });
        System.out.println(aa);

        List<String> a1 = Arrays.asList("a", "t", "y", "r", "s", "r", "", "r", "q", "", "d", "", "vg", "", "s");
        a1.sort((e1, e2) -> {
            if (e1.length() == 0 || e2.length() == 0) {
                return 0;
            }
//                              if(e2.length() == 0) return 1; 
            return e1.compareTo(e2);
        });
        System.out.println(a1);
        System.out.println("".compareTo("vg"));

        List<String> sO = new ArrayList<>();
//        List<String> collect = lambda_1(a1, sO);
//        a1.stream().filter(aaa -> aaa.equals("s")).collect(Collectors.toList())
//                .forEach(ss -> sO.add(ss));
        a1.stream().filter(aaa -> aaa.equals("s")).forEach(ss -> sO.add(ss));
        System.out.println("s only : " + sO);
//        System.out.println("co : " + collect);

        HashMap<Integer, Callable<String>> opcode_only
                = new HashMap<Integer, Callable<String>>() {
                    {
                        put(0, () -> {
                            return "Lambda zero called";
                        });
                        put(1, () -> {
                            return "Lambda one called....";
                        });
                    }
                };
        try {
            System.out.println(opcode_only.get(1).call());
        } catch (Exception ex) {
            Logger.getLogger(TestIng.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FunctionalInterface
    interface A {

        void a();
    }

    private static String rPro(String act, String remove) {
        System.out.println("aaa");
        if (act.contains(remove)) {
            act = rPro(act.replace(remove, ""), remove);
        }
        return act;
    }

    private static String pro(String act, String remove) {

        String[] array = act.split(" ");
        StringBuilder sbs = new StringBuilder();

        for (String a : array) {
            sbs.append(rPro(a, remove)).append(" ");
        }
        return sbs.toString();
    }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        String s = "man singh";
        System.out.println(new StringBuilder("-meta:artist:").append("\"").append(s).append("\""));
        System.err.println("fsdafasdf");
        System.out.println("manish");
        System.out.println("isnotis".split("not" , -1).length-1);
        
//        System.out.println(pro("Hilllloo Therlloe", "llo"));
//        System.out.println(pro("Hllllllllllllllllllllooooooooooi", "llo"));
//        lambda_2();
        
        Set<String> a = new HashSet<String>(Arrays.asList("a", "b", "c"));
        Set<String> b = new HashSet<String>(Arrays.asList("b"));
        System.out.println(a.containsAll(b));
    }

    private static List<String> lambda_1(List<String> a1, List<String> sO) {
        List<String> collect = a1.stream().filter(aaa -> {
            if (aaa.equals("s")) {
                sO.add(aaa);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return collect;
    }

    public void testRobot() {
        try {

            Robot robot = new Robot();
            // Creates the delay of 5 sec so that you can open notepad before
            // Robot start writting
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_H);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_Y);
            robot.keyRelease(KeyEvent.VK_Y);
            robot.mouseMove(160, 120);
            robot.mousePress(0);
            robot.mouseRelease(0);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void testPrintWriter() throws IOException,
            UnsupportedEncodingException {
        PrintWriter pw = new PrintWriter(new FileWriter("c:/zzzz/a.err"));
        pw.println("WIthout any Char set"
                + new String(new byte[]{'a', 'b'}, "8859_1") + " : ");
        // pw.flush();
        pw.println("WIth UTF 16 set"
                + new String(new byte[]{'d', 'f'}, "UTF_16"));
        // pw.flush();
        pw.println("WIth 8859_1 set" + new String(new byte[]{}, "8859_1"));
        // pw.flush();
        pw.flush();
        pw.close();
    }

    public static void premain(String args, Instrumentation inst) {
        ins = inst;
    }

}

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestIng {

    private static Instrumentation ins;

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

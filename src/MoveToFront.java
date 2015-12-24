import com.sun.org.apache.xpath.internal.SourceTree;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> seq = new LinkedList<>();
        for (int i = 0; i < 255; i++) {
            seq.add((char)(i));
        }
        for (; !BinaryStdIn.isEmpty();) {
            char c = BinaryStdIn.readChar();
            int index = seq.indexOf(c);
            StdOut.printf("%c", (char)(index));
            seq.remove(index);
            seq.addFirst(c);
        }
    }

    public static String encodeTest() {
        LinkedList<Character> seq = new LinkedList<>();
        String result = "";
        for (int i = 0; i < 255; i++) {
            seq.add((char)(i));
        }
        String s = "ABRACADABRA!";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = seq.indexOf(c);
            result += (char)(index);
            seq.remove(index);
            seq.addFirst(c);
        }

        return result;
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> seq = new LinkedList<>();
        for (int i = 0; i < 255; i++) {
            seq.add((char) (i));
        }
        for (; !BinaryStdIn.isEmpty();) {
            char index = BinaryStdIn.readChar();
            char c = seq.get(index);
            StdOut.printf("%c", c);
            seq.remove(index);
            seq.addFirst(c);
        }
    }

    public static String decodeTest(String s) {
        LinkedList<Character> seq = new LinkedList<>();
        String result = "";
        for (int i = 0; i < 255; i++) {
            seq.add((char) (i));
        }
        for (int i = 0; i < s.length(); i++) {
            char index = s.charAt(i);
            char c = seq.get(index);
            result += c;
            seq.remove(index);
            seq.addFirst(c);
        }

        return result;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            //System.out.println("Encode");
            encode();
        }
        if (args[0].equals("+")) {
            //System.out.println("Decode");
            decode();
        }
        /*String s = encodeTest();
        System.out.println(decodeTest(s));*/
    }
}
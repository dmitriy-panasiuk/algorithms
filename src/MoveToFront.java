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
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            int index = seq.indexOf(c);
            StdOut.printf("%c", (char)(index));
            seq.remove(index);
            seq.addFirst(c);
        }
    }

    public static void encodeTest() {
        LinkedList<Character> seq = new LinkedList<>();
        for (int i = 0; i < 255; i++) {
            seq.add((char)(i));
        }
        String s = "CAAABCCCACCF";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = seq.indexOf(c);
            System.out.println(index);
            seq.remove(index);
            seq.addFirst(c);
        }
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        encodeTest();
    }
}
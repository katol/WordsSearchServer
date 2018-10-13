import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class HandlerTest {

    private static volatile Handler handler;

    @Test
    public void run() {
        LinkedList<String> consts = new LinkedList<String>();
        consts.add("ab");
        consts.add("cd");

        handler = new Handler(consts);
        handler.start();
        handler.add("a1");
        handler.add("b2");
        handler.add("abc");
        handler.add("abcd");
        try {
            handler.join(1000);
        }
        catch (InterruptedException e) {

        }
        handler.interrupt();

        LinkedList<String> expected = new LinkedList<String>();
        expected.add("abc");
        expected.add("abcd");

        LinkedList<String> actual = handler.getOutput();

        assertEquals(expected, actual);
    }
}
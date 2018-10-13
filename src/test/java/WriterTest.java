import org.junit.Test;

import java.util.*;
import java.io.*;
import static org.junit.Assert.*;

/**
 * Класс, тестирующий класс, записывающий сообщения в файл вместе с датой получения
 *       со свойствами <b>writer</b>, <b>fileName</b> и <b>dateLength</b>.
 * @autor Анатолий Берелехис
 */
public class WriterTest {

    private static volatile Writer writer;
    private static final String fileName = "outputTest.txt";
    private static final int dateLength = 9;

    /**
     * Записывает элементы в файл
     * @param elementsToWrite - элементы
     */
    private void writeToFile(LinkedList<String> elementsToWrite) {
        writer = new Writer(fileName);
        writer.start();

        Iterator<String> it = elementsToWrite.iterator();
        while (it.hasNext())
            writer.add(it.next());

        try {
            writer.join(5000);
        }
        catch (InterruptedException e) {

        }

        writer.interrupt();

        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException e) {

        }
    }

    /**
     * Считывает элементы из файла
     * @return возвращает список этих элементов
     */
    private LinkedList<String> readFromFile() {
        LinkedList<String> elementsFromFile = new LinkedList<String>();
        BufferedReader reader;
        String elementFromFile;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while((elementFromFile = reader.readLine())!= null)
                elementsFromFile.add(elementFromFile);
            reader.close();
        }
        catch (IOException e) {
            System.out.println("IOException!");
        }

        return elementsFromFile;
    }

    /**
     * Следующие три функции необходимы для тестирования,
     *      так как нет возможнсоти сравнить даты получения сообщений
     *          (в тестовый класс они всегда придут хоть на секунду, но позже,
     *              чем в основной)
     */

    /**
     * Формирует список длин сообщений
     * @param values - строковые значения самих сообщений
     * @return возвращает список длин этих сообщений
     */
    private LinkedList<Integer> getLengths(LinkedList<String> values) {
        LinkedList<Integer> lengths = new LinkedList<Integer>();
        Iterator<String> it = values.iterator();
        while(it.hasNext())
            lengths.add(it.next().length());
        return lengths;
    }

    /**
     * Формирует список длин сообщений, прибавляя к ним размер длины даты получения
     * @param values - строковые значения самих сообщений
     * @return возвращает список длин этих сообщений
     */
    private LinkedList<Integer> getLengthsWithDate(LinkedList<String> values) {
        LinkedList<Integer> lengths = new LinkedList<Integer>();
        Iterator<String> it = values.iterator();
        while(it.hasNext())
            lengths.add(it.next().length() + dateLength);
        return lengths;
    }

    /**
     * Формирует булевый список, где каждое значение сигнализирует о том,
     *      содержит ли строка из strings соответсвубщий элемент из elements
     * @param strings - строки, проверяющиеся на содержания заданных элементов
     * @param elements - эти элементы
     */
    private LinkedList<Boolean> getContainFlags(
            LinkedList<String> strings,
            LinkedList<String> elements
    ) {
        LinkedList<Boolean> containsFlags = new LinkedList<Boolean>();
        if (strings.size() != elements.size())
            return containsFlags;

        Iterator<String> stringsIterator = strings.iterator();
        Iterator<String> elementsIterator = strings.iterator();
        while (stringsIterator.hasNext()) {
            String string = stringsIterator.next();
            String element = elementsIterator.next();
            if (string.contains(element))
                containsFlags.add(true);
            else
                containsFlags.add(false);
        }
        return containsFlags;
    }

    @Test
    public void run() {
        LinkedList<String> expectedValues =
                new LinkedList<String>(Arrays.asList("a1", "b2", "qwerty", "5"));
        LinkedList<Integer> expectedLengths = getLengthsWithDate(expectedValues);
        LinkedList<Boolean> expectedContainsFlags =
                new LinkedList<Boolean>(Arrays.asList(true, true, true, true));

        writeToFile(expectedValues);
        LinkedList<String> actualValues = readFromFile();
        LinkedList<Integer> actualLengths = getLengths(actualValues);
        LinkedList<Boolean> actualContainsFlags =
                getContainFlags(actualValues, expectedValues);

        assertEquals(expectedLengths, actualLengths);
        assertEquals(expectedContainsFlags, actualContainsFlags);

    }
}
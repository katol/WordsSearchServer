import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Класс, записывающий сообщения в файл вместе с датой получения
 *      со свойствами <b>fileName</b>, <b>input</b> и <b>writer</b>.
 * @autor Анатолий Берелехис
 */
public class Writer extends Thread{

    private String fileName;
    private volatile LinkedList<String> input;
    private PrintWriter writer;

    /**
     * Конструктор
     * @param fileName - имя файла, куда будут записываться сообщения
     */
    public Writer(String fileName) {
        this.fileName = fileName;
        input = new LinkedList<String>();
    }

    /**
     * Добавляет элемент в очередь на запись
     * @param element - элемент сообщения (еще без даты получения)
     */
    public void add(String element) {
        input.add(element);
    }

    /**
     * Зписывает сообщения с датой получения в файл
     * Исполняется при старте нити.
     */
    @Override
    public void run() {
        try {
            writer = new PrintWriter(fileName);
        }
        catch (IOException e) {
            System.out.println("Writer can not init!");
        }

        do {
            if (!this.isInterrupted()) {
                try {
                    Date dateNow = new Date();
                    SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
                    writer.println(input.getFirst() + " " + formatForDateNow.format(dateNow));
                    input.removeFirst();
                }
                catch (NoSuchElementException e) {

                }
            }
            else {
                break;
            }
        }
        while(true);

        writer.close();
    }
}

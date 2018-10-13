import java.util.*;

/**
 * Класс, проверяющий сообщения на наличие заданных констант
 *       со свойствами <b>consts</b>, <b>input</b> и <b>output</b>.
 * @autor Анатолий Берелехис
 */
public class Handler extends Thread{

    private LinkedList<String> consts;
    private volatile LinkedList<String> input;
    private volatile LinkedList<String> output;

    /**
     * Конструктор
     * @param consts - константы,
     *      на на наличие которых будет выполняться проверка сообщений
     */
    public Handler(LinkedList<String> consts) {
        this.consts = consts;
        input = new LinkedList<String>();
        output = new LinkedList<String>();
    }

    /**
     * Добавляет элемент в очередь на обработку
     * @param element - элемент сообщения (еще без даты получения)
     */
    public void add(String element) {
        input.add(element);
    }

    /**
     * Геттер для итогового вывода сообщений,
     *      содержащих заданные константы
     * @return возвращает эти сообщения
     */
    public LinkedList<String> getOutput() {
        return output;
    }

    /**
     * Проверяет сообщения на наличие заданных констант
     * Исполняется при старте нити.
     */
    @Override
    public void run() {
        do {
            if (!this.isInterrupted()) {
                try {
                    String inputElement = input.getFirst();
                    Iterator<String> it = consts.iterator();
                    boolean isContains = false;
                    while (it.hasNext()) {
                        String constsElement = it.next();
                        if (inputElement.contains(constsElement))
                            isContains = true;
                    }
                    if (isContains) {
                        output.add(inputElement);
                        System.out.println(inputElement);
                    }
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
    }
}

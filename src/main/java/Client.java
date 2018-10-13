import java.io.*;
import java.net.*;

/**
 * Основной класс приложения-клиента
 * @autor Анатолий Берелехис
 */
public class Client {

    /**
     * Основная функция приложения-клиента
     * @param args - аргументы командной строки
     *      (1 - адрес сервера. если запускается на одном компьютере, то "localhost")
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Client side");

        Socket fromServer = createSocket(args);
        PrintWriter out = new PrintWriter(fromServer.getOutputStream(),true);
        BufferedReader in = new  BufferedReader(new InputStreamReader(System.in));

        String message;
        while ((message = in.readLine())!=null) {
            out.println(message);
            if (message.equalsIgnoreCase("exit")) break;
        }

        out.close();
        in.close();
        fromServer.close();
    }

    /**
     * Создает сокет
     * @param args - аргументы командной строки
     *      (1 - адрес сервера. если запускается на одном компьютере, то "localhost")
     * @return возвращает сокет
     */
    private static Socket createSocket(String[] args) throws IOException{
        Socket fromServer = null;
        if (args.length==0) {
            System.out.println("use: client hostname");
            System.exit(-1);
        }
        System.out.println("Connecting to... "+args[0]);
        fromServer = new Socket(args[0],4444);
        return fromServer;
    }
} 
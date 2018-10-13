import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Основной класс приложения-сервера со свойствами <b>Writer</b> и <b>Handler</b>.
 * @autor Анатолий Берелехис
 */
public class Server {

    static Writer writer;
    static Handler handler;

    /**
     * Основная функция приложения-сервера
     * @param args - аргументы командной строки
     *      (константы, на на наличие которых будет выполняться проверка сообщений)
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");

        ServerSocket servers = createServerSocket();
        Socket fromClient = accept(servers);
        BufferedReader in  = new BufferedReader(new
                InputStreamReader(fromClient.getInputStream()));

        writer = new Writer("output.txt");
        writer.start();
        LinkedList<String> consts = new LinkedList<String>(Arrays.asList(args));
        handler = new Handler(consts);
        handler.start();

        System.out.println("Wait for messages");
        String input;
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("exit"))
                break;
            writer.add(input);
            handler.add(input);
        }

        writer.interrupt();
        handler.interrupt();
        in.close();
        fromClient.close();
        servers.close();
    }

    /**
     * Создает сокет сервера
     * @return возвращает сокет сервера
     */
    private static ServerSocket createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(4444);
            return serverSocket;
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
            return null;
        }
    }

    /**
     * Соединяет с клиентом
     * @param servers - сокет сервера
     */
    private static Socket accept(ServerSocket servers) {
        try {
            System.out.print("Waiting for a client...");
            Socket fromClient= servers.accept();
            System.out.println("Client connected");
            return fromClient;
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
            return null;
        }
    }
}
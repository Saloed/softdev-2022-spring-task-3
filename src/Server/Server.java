package Server;

import Message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

// Создание потока прослушивания клиентских запросов на подключение к серверу
class ServerThread extends Thread {
    public void run() {
        // Прослушивание подключения клиентов до закрытия сервера
        while (!Server.serverSocket.isClosed()) {
            try {
                // Ожидание клиента
                Server.Display("Client waiting...");
                Socket clientSocket = Server.serverSocket.accept();
                Server.Display("Client came...");
                // Создание объектной модели клиента
                SClient nclient = new SClient(clientSocket, Server.IdClient);
                Server.IdClient++;
                Server.Clients.add(nclient);
                nclient.listenThread.start();

            } catch (IOException e) {
                // Обработка ошибки подключения клиента
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}

public class Server {

    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    public static int port = 0;
    public static ServerThread runThread;// Запуск потока прослушивания команд клиентов
    public static ArrayList<SClient> Clients = new ArrayList<>();//Создание списков клиентов
    public static Semaphore pairTwo = new Semaphore(1, true);

    public static void Start(int openport) {
        try {
            //Установка порта для сервера
            Server.port = openport;
            //Запуск сервера с указанным портом
            Server.serverSocket = new ServerSocket(Server.port);

            //Создание потока для прослушивания клиентов
            Server.runThread = new ServerThread();
            // Запуск потока прослушивания
            Server.runThread.start();

        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Функция для вывода сообщения в терминал сервера
    public static void Display(String msg) {
        System.out.println(msg);
    }

    // Отправка сообщений клиентам и серверу
    public static void Send(SClient cl, Message msg) {
        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

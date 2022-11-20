package Client;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Client.Client.sInput;
import backgammon.Backgammon;
import backgammon.Bar;
import backgammon.Triangle;
import java.util.LinkedList;

// Прослушивание полученных сообщений от сервера
class Listen extends Thread {

    //Функция для запуска прослушивания сообщений
    public void run() {
        //Данный блок выполняется до тех пор, пока клиент подключен к серверу
        while (Client.socket.isConnected()) {
            try {
                // Получение сообщения от сервера
                Message received = (Message) (sInput.readObject());

                //Проверка сообщения на тип
                switch (received.type) {
                    case RivalConnected -> {
                        //Сообщение о подключении соперника
                        String rivalMessage = received.content.toString();
                        System.out.println(rivalMessage);
                    }
                    case Dice -> {
                        // Обработка значений игральных костей и их перерисовка на доске
                        int[] recv = (int[]) received.content;
                        System.out.println("dice1: " + recv[0] + ", dice2: " + recv[1]);
                        Backgammon.setDices(recv[0], recv[1]);
                        Backgammon.repaint();
                    }
                    case Triangles -> {
                        // Установка всех треугольников и отображение их на доске
                        Backgammon.setTriangles((LinkedList<Triangle>) received.content);
                        Backgammon.repaint();
                        System.out.println("get triangles");
                    }
                    case Bar -> {
                        // Установка полей для отображения фишек на треугольнике
                        Bar b = (Bar) received.content;
                        Backgammon.setBar(b);
                        Backgammon.repaint();
                        System.out.println("get bar");
                    }
                    case Color -> {
                        // Установка цвета клиента
                        Backgammon.setColor((int) received.content);
                        System.out.println("client color: " + ((int) received.content));
                    }
                    case ChangePlayer ->
                            //Изменение текущего игрока
                            Backgammon.changeCurrentPlayer();
                    case GiveUp -> {
                        //Обработка "сдаться"
                        Backgammon.giveUp();
                        Client.Stop();
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                //Обработка ошибки потоков (ответа от сервера)
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                break;
            }

        }

    }
}


//Создание объекта клиента
public class Client {

    public static Socket socket; // Библиотека для создания подключения к серверу

    public static ObjectInputStream sInput; // переменная для получения данных от сервера
    public static ObjectOutputStream sOutput; // переменная для отправки данных на сервер

    public static Listen listenMe; //Переменная для прослушивания клиента

    //Функция для старта и создания клиента
    public static void Start(String ip, int port) {
        try {
            // Установка узла подключения для клиента
            Client.socket = new Socket(ip, port);
            Client.Display("Connected to server");
            // Установка входных и выходных данных
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());

            //Начало прослушивания ответов сервера
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //Отправка сообщения на сервер, если клиент только подключился
            Message msg = new Message(Message.Message_Type.Connected);
            msg.content = "Connected";
            Client.Send(msg);
        } catch (IOException e) {
            //Обработка ошибки работы потока
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Отключение клиента от сервера
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.interrupt();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    //Функция для отображения сообщений
    public static void Display(String msg) {
        System.out.println(msg);
    }

    //Функция для отправки данных на сервер
    public static void Send(Message msg) {
        try {
            //Записываем сообщение
            Client.sOutput.writeObject(msg);
            //Обновляем объект с сообщениями, чтобы данное сообщение принялось сервером
            sOutput.reset();
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}

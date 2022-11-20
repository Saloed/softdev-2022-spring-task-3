package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {
    int id; //id пользователя
    Socket soket; // переменная для канала подключения
    ObjectOutputStream sOutput; // вывод пользовательских данных
    ObjectInputStream sInput; // ввод пользовательских данных
    Listen listenThread; // поток сервера для получения пользовательских команд
    PairingThread pairThread; // поток для создания пары двух клиентов
    SClient rival;  // соперник клиента

    public boolean paired = false; //переменная для проверки пары

    //Создание нового объекта клиента при его подключении к серверу
    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            //Настройка отправки и получений пользователю сообщений
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Прослушивание команд через поток от конкретного клиента
        this.listenThread = new Listen(this);
        //Установка потока для пары клиентов
        this.pairThread = new PairingThread(this);

    }
    // Прослушивание команд от клиентов через поток
    static class Listen extends Thread {
        SClient TheClient;
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }
        public void run() {
            // Прослушивание команд до отключения клиента
            while (TheClient.soket.isConnected()) {
                try {
                    Message received = (Message) (TheClient.sInput.readObject());
                    switch (received.type) {
                        //Сообщение: подключен ли
                        case Connected:
                            TheClient.pairThread.start();
                            break;
                        //Сообщение: подключен ли соперник
                        case RivalConnected:
                            break;
                        //Сообщение: Игральная кость
                        case Dice:
                            Server.Send(TheClient.rival, received);
                            break;
                        //Сообщение: Треугольник
                        case Triangles:
                            Server.Send(TheClient.rival, received);
                            break;
                        //Сообщение:Панель на треугольнике
                        case Bar:
                            Server.Send(TheClient.rival, received);
                            break;
                        //Сообщение: получить цвет игрока
                        case Color:
                            Server.Send(TheClient.rival, received);
                            break;
                        //Сообщение: изменить игрока
                        case ChangePlayer:
                            Server.Send(TheClient.rival, received);
                            break;
                        //Сообщение: сдаться
                        case GiveUp:
                            Server.Send(TheClient.rival, received);
                            break;
                        
                    }

                } catch (IOException | ClassNotFoundException e) {
                    // Если клиент отключился, то удаляем его из списка игроков
                    Server.Clients.remove(TheClient);

                }
            }

        }
    }

    // У каждого клиента есть поток для пары игроков(противники)
    static class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //Прослушивания сразу двух клиентов (в паре)
            while (TheClient.soket.isConnected() && !TheClient.paired) {
                try {
                    Server.pairTwo.acquire(1);

                    if (!TheClient.paired) {
                        SClient crival = null;
                        while (crival == null && TheClient.soket.isConnected()) {
                            for (SClient clnt : Server.Clients) {
                                if (TheClient != clnt && clnt.rival == null) {
                                    crival = clnt;
                                    crival.paired = true;
                                    crival.rival = TheClient;
                                    TheClient.rival = crival;
                                    TheClient.paired = true;
                                    break;
                                }
                            }

                            sleep(1000); // Остановка сервера на 1с, чтобы успеть сделать изменения на стороне клиента.
                        }
                        //Если соединение установлено, то отправляем соответствующие сообщения двум клиентам
                        Message msg1 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = "Rival Connected";
                        Server.Send(TheClient.rival, msg1);

                        Message msg2 = new Message(Message.Message_Type.RivalConnected);
                        msg2.content = "Rival Connected";
                        Server.Send(TheClient, msg2);

                        // Выбираем цвет клиентов
                        Message msg3 = new Message(Message.Message_Type.Color);
                        msg3.content = 1;
                        Server.Send(TheClient.rival, msg3);

                        Message msg4 = new Message(Message.Message_Type.Color);
                        msg4.content = 0;
                        Server.Send(TheClient, msg4);
                    }
                    // Запускаем соединение двух клиентов
                    Server.pairTwo.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}

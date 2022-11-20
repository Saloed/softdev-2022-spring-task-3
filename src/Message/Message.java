package Message;

public class Message implements java.io.Serializable {

    //В данной структуре перечисляются все виды сообщений от клиента
    public enum Message_Type {
        Connected, RivalConnected, Dice, Triangles, Bar, Color, ChangePlayer, GiveUp
    }

    //Переменная для записи типа сообщений
    public Message_Type type;

    //Текст самого сообщения
    public Object content;

    //Функция для создания сообщений
    public Message(Message_Type t) {
        this.type = t;
    }

}

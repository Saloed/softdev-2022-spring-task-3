package backgammon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Client.Client;
import Message.Message;
import javax.swing.JLabel;

public class Backgammon {

    public static Backgammon ThisGame;

    static Color CurrentPlayer; // Создание переменной для игрока
    static Color playerColor;   // Переменная для хранения цвета игрока

    //Установка размеров экрана
    static int actualWidth = 1000;
    static int actualHeight = 450;
    static int extraWidth = 18;
    static int extraHeight = 43;

    Piece selectedPiece = null; //Переменная для выделенной фишки на поле
    Triangle target_triangle = null; //Переменная для выделенного треугольника на поле

    int X; // Координата курсора
    int Y;

    static int pieceR = 40; // Радиус фишки
    static int triangleW = 60; // Ширина треугольника
    static int triangleH = 5 * pieceR; // Высота треугольника
    static int middleBar = 50 * (triangleW / 60); //Установка центральной панели

    static int dice1 = 1; //Игральная кость
    static int dice2 = 1;
    int play = 0;    // Количество брошенных костей
    boolean playDice = false;   // Переменная на проверку броска кости
    int[] steps = {0, 0, 0, 0}; //Массив для хранения шагов игрока
    static JFrame jFrame;   // Переменная для создания окна игрока
    static LinkedList<Triangle> triangles; // Список для хранения фишек на треугольнике
    static Bar bar; // Переменная для фишек

    static JLabel playerLabel;//Переменная для игрока
    static JLabel currentLabel; //Переменная для игрока, что делает ход
    public static JLabel giveUpLabel; //"give up"
    static boolean givedUp = false; //Проверка на "сдался"

    static JButton connectServer; //Переменная для кнопки подключения к серверу
    static JButton diceButton; //Переменная для броска костей

    public Backgammon() {
        ThisGame = this;
        jFrame = new JFrame();
        jFrame.setSize(actualWidth + extraWidth, actualHeight + extraHeight);
        jFrame.setTitle("Backgammon");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        triangles = new LinkedList<>();
        addElements(triangles);

        bar = new Bar();
        bar.setBounds(6 * triangleW, 0, middleBar, actualHeight);

        JPanel jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                // Отрисовка всех фишек на треугольнике
                for (Triangle t : triangles) {
                    g.setColor(t.color);
                    g.fillPolygon(t.x, t.y, 3);

                    for (Piece p : t.pieces) {
                        g.setColor(p.color);
                        g.fillOval(p.x, p.y, p.r, p.r);
                    }
                }

                // Отрисовка центральной полосы на треугольнике
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(bar.x, bar.y, bar.width, bar.height);

                // Отображаем текст, если фишек в треугольнике более 5
                for (Triangle t : triangles) {
                    if (t.size() > 5) {
                        g.setColor(Color.GREEN);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                        if (t.id < 12) {
                            g.drawString("" + t.size(), t.x[0] + selectedPiece.r / 2, t.y[1] - selectedPiece.r / 3);
                        } else {
                            g.drawString("" + t.size(), t.x[0] + selectedPiece.r / 2, t.y[1] + selectedPiece.r / 2);
                        }
                    }
                }

                // Рисуем игральные кости
                BufferedImage image = null;
                BufferedImage image2 = null;
                try {
                    image = ImageIO.read(new File("src\\image\\" + dice1 + ".png"));
                    image2 = ImageIO.read(new File("src\\image\\" + dice2 + ".png"));

                } catch (IOException ignored) {

                }
                g.drawImage(image, 6 * triangleW, actualHeight / 2 - middleBar, this);
                g.drawImage(image2, 6 * triangleW, actualHeight / 2, this);

                // Рисуем съеденные фишки (рисуются под игральными костями)
                for (Piece p : bar.piecesYellow) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }
                for (Piece p : bar.piecesBlue) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }
                // Отображения количества съеденных костей
                if (bar.sizeYellow() > 0) {
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                    g.drawString("" + bar.sizeYellow(), bar.x + pieceR / 2, bar.y + pieceR / 2);
                }
                if (bar.sizeBlue() > 0) {
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                    g.drawString("" + bar.sizeBlue(), bar.x + pieceR / 2, bar.height - pieceR / 2);
                }

            }

        };

        // Обработка нажатия игрока
        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                //Если игрока нажал сдаться, то игра заканчивается
                if (givedUp) {
                    Client.Stop();
                    return;
                }
                try {
                    if (!playerColor.equals(CurrentPlayer)) {
                        return;
                    }
                } catch (java.lang.NullPointerException e) {
                    return;
                }
                if (!playDice) {
                    return;
                }

                // Проверка куда нажимает игрок и есть ли у него доступные фишки
                if (bar.hasPiece(CurrentPlayer)) {
                    System.out.println("bar, current player: " + CurrentPlayer);
                    if (bar.x <= X && bar.x + bar.width >= X) {
                        boolean played = false;
                        play++;
                        if (play == 1) {

                            if (CurrentPlayer.equals(Color.BLUE)) {
                                target_triangle = triangles.get(24 - dice1);
                                if (triangles.get(24 - dice1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(24 - dice1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }

                            } else {
                                target_triangle = triangles.get(dice1 - 1);
                                if (triangles.get(dice1 - 1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(dice1 - 1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }
                            }

                        } else if (play <= 4) {

                            if (CurrentPlayer.equals(Color.BLUE)) {
                                target_triangle = triangles.get(24 - dice2);
                                if (triangles.get(24 - dice2).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(24 - dice2).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }

                            } else {
                                target_triangle = triangles.get(dice2 - 1);
                                if (triangles.get(dice2 - 1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(dice2 - 1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }
                            }

                            played = true;
                        }

                        // Изменение игрока после броска, если все условия броска соблюдены
                        if ((dice1 == dice2 && play >= 4) || (dice1 != dice2 && play >= 2) || (!played)) {
                            changeCurrentPlayer();
                            play = 0;
                            playDice = false;

                            Message msg = new Message(Message.Message_Type.ChangePlayer);
                            msg.content = "Change Player";
                            Client.Send(msg);
                        }

                    }
                } else {

                    for (Triangle t : triangles) {
                        // Нажатие на треугольник
                        if ((t.x[0] <= X && t.x[2] >= X && t.y[1] >= Y && t.y[0] <= Y) || (t.x[0] <= X && t.x[2] >= X && t.y[1] <= Y && t.y[0] >= Y)) {
                            System.out.println(t.id);
                            try {
                                selectedPiece = t.getLast();
                                if (!selectedPiece.color.equals(CurrentPlayer)) {
                                    System.out.println("piece color: " + selectedPiece.color + ", current player color: " + CurrentPlayer);
                                    return;
                                }

                                // Выделение нажатого треугольника
                                play++;
                                if (play == 1) {
                                    if (CurrentPlayer.equals(Color.YELLOW)) {
                                        target_triangle = triangles.get((t.id + dice1) % 24);
                                    } else {
                                        target_triangle = triangles.get((t.id - dice1) % 24);
                                    }
                                } else if (play <= 4) {
                                    if (CurrentPlayer.equals(Color.YELLOW)) {
                                        target_triangle = triangles.get((t.id + dice2) % 24);
                                    } else {
                                        target_triangle = triangles.get((t.id - dice2) % 24);
                                    }
                                }

                                // Если попали на фишку противника, то съедаем её
                                if (target_triangle.size() == 1 && (!target_triangle.getLast().color.equals(selectedPiece.color))) {
                                    Piece p = target_triangle.remove();
                                    bar.add(p);
                                } else if (target_triangle.size() > 1 && (!target_triangle.getLast().color.equals(selectedPiece.color))) {
                                    play--;
                                    return;
                                }

                                selectedPiece = t.remove();
                                target_triangle.add(selectedPiece);
                                System.out.println("player: " + CurrentPlayer + ", play: " + play);
                                if ((dice1 == dice2 && play >= 4) || (dice1 != dice2 && play >= 2)) {
                                    // Изменение игрока на противоположного
                                    changeCurrentPlayer();
                                    play = 0;
                                    playDice = false;

                                    Message msg = new Message(Message.Message_Type.ChangePlayer);
                                    msg.content = "Change Player";
                                    Client.Send(msg);
                                }
                                break;
                            } catch (NoSuchElementException ignored) {

                            }

                        }

                    }
                }

                // Перерисовываем фишку после её перехода на другой треугольник
                jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);

                // Отправляем сообщение, что фишка была перемещена
                Message msg_p = new Message(Message.Message_Type.Triangles);
                msg_p.content = new LinkedList<>(triangles);
                Client.Send(msg_p);

                Message msg_b = new Message(Message.Message_Type.Bar);
                Bar b = new Bar();
                b.piecesBlue.addAll(bar.piecesBlue);
                b.piecesYellow.addAll(bar.piecesYellow);
                msg_b.content = b;
                Client.Send(msg_b);
                System.out.println("sent pieces");

                System.out.println("player: " + playerColor + "current color: " + CurrentPlayer);

            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Получение координат нажатия мышки и запись их в переменные
                X = Math.max(me.getX() - extraWidth, 0);
                Y = Math.max(me.getY() - extraHeight, 0);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //Отжатие кнопки мыши
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //Вхождение мыши на какой-то участок
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //Выход мыши с какого-то участка
            }

        });

        //Создание кнопок интерфейса

        connectServer = new JButton("Connect to server"); // Подключение пользователя
        connectServer.setSize(160, 30);
        connectServer.setLocation(800, 0);

        //Подключаем пользователя при нажатии на кнопку
        connectServer.addActionListener(e -> {
            Client.Start("127.0.0.1", 2000);
            connectServer.setEnabled(false);
        });

        diceButton = new JButton("Roll"); // Бросок костей
        diceButton.setSize(160, 30);
        diceButton.setLocation(800, 50);


        //Бросаем кости при нажатии на кнопку
        diceButton.addActionListener(ae -> {
            Random random = new Random();
            dice1 = random.nextInt(6) + 1;
            dice2 = random.nextInt(6) + 1;
            steps[0] = dice1;
            steps[1] = dice2;
            System.out.println("dice1: " + dice1 + ", dice2: " + dice2);
            if (dice1 == dice2) {
                steps[2] = dice2;
                steps[3] = dice2;
            } else {
                steps[2] = 0;
                steps[3] = 0;
            }
            playDice = true;
            System.out.println("current player: " + CurrentPlayer);
            jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);

            // Отправляем данные на сервер после броска костей
            Message msg = new Message(Message.Message_Type.Dice);
            msg.content = new int[]{dice1, dice2};
            Client.Send(msg);

        });

        JButton giveUp = new JButton("Give up"); // Кнопка сдаться
        giveUp.setSize(160, 30);
        giveUp.setLocation(800, 100);

        //Заканчиваем игра=у после того, как игрок сдался
        giveUp.addActionListener(ae -> {
            Message msg = new Message(Message.Message_Type.GiveUp);
            msg.content = "Give Up";
            Client.Send(msg);
            giveUpLabel.setText("Game Over!");
        });

        //Цвет игрока (клиента с которого сидим в окне)
        playerLabel = new JLabel();
        playerLabel.setSize(150, 30);
        playerLabel.setLocation(800, 130);
        playerLabel.setText("Your Color: ");

        //Цвет игрока, что бросил кости
        currentLabel = new JLabel();
        currentLabel.setSize(150, 30);
        currentLabel.setLocation(800, 180);
        currentLabel.setText("Current Player Color: ");

        giveUpLabel = new JLabel();
        giveUpLabel.setSize(150, 30);
        giveUpLabel.setLocation(800, 230);

        jPanel.setLayout(null);


        //Добавления всех кнопок и надписей на экрана
        jFrame.add(diceButton);
        jFrame.add(connectServer);
        jFrame.add(giveUp);
        jFrame.add(playerLabel);
        jFrame.add(currentLabel);
        jFrame.add(giveUpLabel);
        jFrame.add(jPanel);

        jFrame.setVisible(true);
    }

    // Добавления элементов на экран

    //Каждая секция это область экрана
    void addElements(LinkedList list) {
        Color color;
        int partLength = 6 * triangleW + middleBar;
        // Секция 1
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{partLength + (i - 1) * triangleW, partLength + (triangleW / 2) + triangleW * (i - 1), partLength + i * triangleW}, new int[]{0, triangleH, 0}, 6 - i, color));
        }

        // Секция 2
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{(i - 1) * triangleW, (triangleW / 2) + triangleW * (i - 1), i * triangleW}, new int[]{0, triangleH, 0}, 12 - i, color));
        }

        // Секция 3
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{(i - 1) * triangleW, (triangleW / 2) + triangleW * (i - 1), i * triangleW}, new int[]{actualHeight, actualHeight - triangleH, actualHeight}, i + 11, color));
        }

        // Секция 4
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{partLength + (i - 1) * triangleW, partLength + (triangleW / 2) + triangleW * (i - 1), partLength + i * triangleW}, new int[]{actualHeight, actualHeight - triangleH, actualHeight}, i + 17, color));
        }

        // Добавление фишек на каждый треугольник
        int[] count = {2, 5, 3, 5, 5, 3, 5, 2};
        Color[] pieceColor = {Color.yellow, Color.blue, Color.blue, Color.yellow, Color.blue, Color.yellow, Color.yellow, Color.blue};
        int j = 0;
        Triangle t;
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 5 || i == 7 || i == 11) {
                t = (Triangle) list.get(i);
                for (int k = 0; k < count[j]; k++) {
                    t.pieces.add(new Piece(t.x[0] + ((triangleW - pieceR) / 2), (k * pieceR), pieceR, pieceColor[j]));
                }
                j++;
            } else if (i == 12 || i == 16 || i == 18 || i == 23) {
                t = (Triangle) list.get(i);
                for (int k = 1; k <= count[j]; k++) {
                    t.pieces.add(new Piece(t.x[0] + ((triangleW - pieceR) / 2), actualHeight - (k * pieceR), pieceR, pieceColor[j]));
                }
                j++;
            }
        }

    }
    
    // Изменение игрового поля при изменениях экрана
    public static void repaint() {
        jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);
    }
    
    // Запись значения игровых костей
    public static void setDices(int d1, int d2) {
        dice1 = d1;
        dice2 = d2;
        changeDiceStates();
    }
    
    // Изменение значений игральных костей для всех клиентов
    private static void changeDiceStates(){
        diceButton.setEnabled(CurrentPlayer.equals(playerColor));
    }
    
    // Очистка от всех треугольников и добавление новых
    public static void setTriangles(LinkedList<Triangle> t) {
        triangles.clear();
        triangles.addAll(t);

    }
    
    // Удаление всех фишек всех игроков и установка новых
    public static void setBar(Bar b) {
        bar.piecesBlue.clear();
        bar.piecesYellow.clear();
        bar.piecesBlue.addAll(b.piecesBlue);
        bar.piecesYellow.addAll(b.piecesYellow);
    }
    
    // Установка цвета для игрока и текущего игрока(тот, что бросает кости)
    public static void setColor(int c) {
        if (c == 0) {
            CurrentPlayer = Color.YELLOW;
            playerColor = Color.YELLOW;
        } else {
            CurrentPlayer = Color.YELLOW;
            playerColor = Color.BLUE;
        }
        playerLabel.setText("<html>Your Color:<br/>" + (playerColor.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
        currentLabel.setText("<html>Current Player Color:<br/>" + (CurrentPlayer.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
    }

    // Изменение цвета текущего игрока на противоположный
    public static void changeCurrentPlayer() {
        if (CurrentPlayer.equals(Color.YELLOW)) {
            CurrentPlayer = Color.BLUE;
        } else {
            CurrentPlayer = Color.YELLOW;
        }

        currentLabel.setText("<html>Current Player Color:<br/>" + (CurrentPlayer.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
        
        changeDiceStates();
    }
    public static void giveUp() {
        giveUpLabel.setText("WIN!");
    }
    public static void main(String[] args) {
        new Backgammon();

    }

}

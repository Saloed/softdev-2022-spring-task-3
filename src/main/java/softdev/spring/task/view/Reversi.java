package softdev.spring.task.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Reversi implements KeyListener {

    private final ReversiPanel reversiPanel;

    public Reversi() {
        JFrame frame = new JFrame("Reversi");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        try {
            frame.setIconImage(ImageIO.read(new File("src/main/resources/reversi.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reversiPanel = new ReversiPanel();

        frame.getContentPane().add(reversiPanel);
        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        reversiPanel.handleInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

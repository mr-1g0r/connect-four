package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {

    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 300;
    private Board board;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Connect Four");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        createGameBoard();
        createResetButton();

        pack();
        setVisible(true);
    }

    private void createGameBoard() {
        board = new Board();
        board.render();

        add(board);
    }

    private void createResetButton() {
        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(e -> board.reset());
        resetButton.setFocusPainted(false);

        resetPanel.add(resetButton);
        add(resetPanel);
    }
}
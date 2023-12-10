package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLUMNS = 7;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Connect Four");

        setLayout(new GridLayout(MAX_ROWS, MAX_COLUMNS));
        Board board = new Board(this, MAX_ROWS, MAX_COLUMNS, () -> "X", () -> "O");
        board.render();

        setVisible(true);
    }
}
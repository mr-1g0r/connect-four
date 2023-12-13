package four;

import javax.swing.*;
import java.awt.*;

public class Board {
    public static final String BLANK = " ";
    private final JFrame mainWindow;
    private final JButton[][] cells;

    private final Player playerOne;
    private final Player playerTwo;

    private Player currentMove;
    private final int[] nextEmptyCellPerColumn;

    public Board(final JFrame mainWindow, final int maxRows, final int maxColumns, Player playerOne, Player playerTwo) {
        this.mainWindow = mainWindow;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.currentMove = playerOne;

        this.cells = new JButton[maxColumns][maxRows];
        this.nextEmptyCellPerColumn = new int[maxColumns];
        createBoardButtons(maxColumns, maxRows);
    }

    private void createBoardButtons(int maxColumns, int maxRows) {
        for (int y = maxRows - 1; y >= 0; y--) {
            for (int x = 0; x < maxColumns; x++) {
                JButton button = createButton(x, y);
                this.cells[x][y] = button;
            }
        }
    }

    private JButton createButton(int x, int y) {
        String text = String.format("%c%d", (char) 65 + x, 1 + y);
        JButton button = new JButton(BLANK);
        button.setName("Button" + text);
        button.setFocusPainted(false);
        button.addActionListener(e -> handleButtonAction(x, y));
        return button;
    }

    private void handleButtonAction(int x, int y) {
        if (nextEmptyCellPerColumn[x] < nextEmptyCellPerColumn.length) {
            y = nextEmptyCellPerColumn[x];
            nextEmptyCellPerColumn[x] += 1;
        }

        if (isMoveValid(x, y)) {
            cells[x][y].setText(currentMove.piece());
            switchPlayers();
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean isMoveValid(final int x, final int y) {
        return y < cells[0].length
                && x < cells.length
                && cells[x][y].getText().equals(BLANK);
    }

    private void switchPlayers() {
        currentMove = currentMove == playerOne ? playerTwo : playerOne;
    }

    public void render() {
        for (int y = cells[0].length - 1; y >= 0; y--) {
            for (JButton[] cell : cells) {
                mainWindow.add(cell[y]);
            }
        }
    }
}

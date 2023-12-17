package four;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends JPanel {

    private static final int MAX_ROWS = 6;
    private static final int MAX_COLUMNS = 7;

    public static final String EMPTY_CELL_TEXT = " ";
    public static final Color BUTTON_BACKGROUND_COLOUR = new Color(182, 211, 139);
    public static final Color BUTTON_VICTORY_COLOUR = new Color(104, 225, 130);
    public static final Dimension CELL_DRAWING_SIZE = new Dimension(40, 40);
    private final JButton[][] cells;

    private final Player playerOne = () -> "X";
    private final Player playerTwo = () -> "O";

    private Player currentMove;
    private final int[] nextEmptyCellPerColumn;
    private final WinningCellsDetector winningCellsDetector;

    public Board() {
        this.currentMove = playerOne;

        this.cells = new JButton[MAX_COLUMNS][MAX_ROWS];
        this.nextEmptyCellPerColumn = new int[MAX_COLUMNS];
        this.winningCellsDetector = new WinningCellsDetector(this.cells);

        setLayout(new GridLayout(MAX_ROWS, MAX_COLUMNS));
        createBoardButtons();
    }

    public void render() {
        for (int y = cells[0].length - 1; y >= 0; y--) {
            for (JButton[] cell : cells) {
                add(cell[y]);
            }
        }
    }

    public void reset() {
        for (int x = 0; x < Board.MAX_COLUMNS; x++) {
            for (int y = Board.MAX_ROWS - 1; y >= 0; y--) {
                JButton button = cells[x][y];
                button.setText(EMPTY_CELL_TEXT);
                button.setBackground(BUTTON_BACKGROUND_COLOUR);
            }
            nextEmptyCellPerColumn[x] = 0;
        }
        currentMove = playerOne;
    }

    private void createBoardButtons() {
        for (int y = Board.MAX_ROWS - 1; y >= 0; y--) {
            for (int x = 0; x < Board.MAX_COLUMNS; x++) {
                JButton button = createButton(x, y);
                this.cells[x][y] = button;
            }
        }
    }

    private JButton createButton(int x, int y) {
        String text = String.format("%c%d", (char) 65 + x, 1 + y);
        JButton button = new JButton(EMPTY_CELL_TEXT);
        button.setName("Button" + text);
        button.setFocusPainted(false);

        button.setUI(new BasicButtonUI());
        button.setBackground(BUTTON_BACKGROUND_COLOUR);
        button.setPreferredSize(CELL_DRAWING_SIZE);

        // adds white border and removes "..." as initial basic button text
        button.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        button.setMargin(new Insets(0, 0, 0, 0));    // removes margins

        button.setContentAreaFilled(true);
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
            List<JButton> winningCells = winningCellsDetector.getWinningCells(x, y);
            if (winningCells.size() == 4) {
                winningCells.forEach(cell -> cell.setBackground(BUTTON_VICTORY_COLOUR));
                currentMove = null; // Game Ended
            } else {
                switchPlayers();
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean isMoveValid(final int x, final int y) {
        return currentMove != null
                && y < cells[0].length
                && x < cells.length
                && cells[x][y].getText().equals(EMPTY_CELL_TEXT);
    }

    private void switchPlayers() {
        currentMove = currentMove == playerOne ? playerTwo : playerOne;
    }


    private class WinningCellsDetector {
        private final JButton[][] cells;

        WinningCellsDetector(JButton[][] cells) {
            this.cells = cells;
        }

        List<JButton> getWinningCells(final int x, final int y) {
            var winningCells = new ArrayList<JButton>();
            winningCells.addAll(getHorizontalWinningCells(y));
            winningCells.addAll(getVerticalWinningCells(x));
            winningCells.addAll(getDiagonalWinningCells());
            return winningCells;
        }

        private List<JButton> getDiagonalWinningCells() {
            String currentPlayer = currentMove.piece();
            List<JButton> winningCells = new ArrayList<>();

            for (int i = 0; i < MAX_COLUMNS - 3; i++) {
                for (int j = 0; j < MAX_ROWS - 3; j++) {
                    var firstCell = cells[i][j];
                    var secondCell = cells[i + 1][j + 1];
                    var thirdCell = cells[i + 2][j + 2];
                    var forthCell = cells[i + 3][j + 3];

                    if (currentPlayer.equals(firstCell.getText())
                            && firstCell.getText().equals(secondCell.getText())
                            && secondCell.getText().equals(thirdCell.getText())
                            && thirdCell.getText().equals(forthCell.getText())) {
                        winningCells.addAll(List.of(firstCell, secondCell, thirdCell, forthCell));
                    }
                }
            }

            for (int i = 0; i < MAX_COLUMNS - 3; i++) {
                for (int j = 3; j < MAX_ROWS; j++) {
                    var firstCell = cells[i][j];
                    var secondCell = cells[i + 1][j - 1];
                    var thirdCell = cells[i + 2][j - 2];
                    var forthCell = cells[i + 3][j - 3];

                    if (currentPlayer.equals(firstCell.getText())
                            && firstCell.getText().equals(secondCell.getText())
                            && secondCell.getText().equals(thirdCell.getText())
                            && thirdCell.getText().equals(forthCell.getText())) {
                        winningCells.addAll(List.of(firstCell, secondCell, thirdCell, forthCell));
                    }
                }
            }
            return winningCells;
        }

        private List<JButton> getVerticalWinningCells(final int x) {
            String currentPlayer = currentMove.piece();
            for (int row = 0; row < MAX_ROWS - 3; row++) {
                var firstCell = cells[x][row];
                var secondCell = cells[x][row + 1];
                var thirdCell = cells[x][row + 2];
                var forthCell = cells[x][row + 3];

                if (currentPlayer.equals(firstCell.getText())
                        && firstCell.getText().equals(secondCell.getText())
                        && secondCell.getText().equals(thirdCell.getText())
                        && thirdCell.getText().equals(forthCell.getText())) {
                    return List.of(firstCell, secondCell, thirdCell, forthCell);
                }
            }
            return Collections.emptyList();
        }

        private List<JButton> getHorizontalWinningCells(final int y) {
            String currentPlayer = currentMove.piece();
            for (int column = 0; column < MAX_COLUMNS - 3; column++) {
                var firstCell = cells[column][y];
                var secondCell = cells[column + 1][y];
                var thirdCell = cells[column + 2][y];
                var forthCell = cells[column + 3][y];

                if (currentPlayer.equals(firstCell.getText())
                        && firstCell.getText().equals(secondCell.getText())
                        && secondCell.getText().equals(thirdCell.getText())
                        && thirdCell.getText().equals(forthCell.getText())) {
                    return List.of(firstCell, secondCell, thirdCell, forthCell);
                }
            }
            return Collections.emptyList();
        }
    }
}

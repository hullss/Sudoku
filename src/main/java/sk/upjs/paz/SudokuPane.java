package sk.upjs.paz;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;
import sk.upjs.jpaz2.WinPane;

import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;


public class SudokuPane extends WinPane {
    private Turtle gridTurtle;

    private int oX = 20;
    private int oY = 20;
    private int cell = 50;
    private int size = 9 * cell;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean wasSolved;

    private int holes;

    private final SudokuBoard model = new SudokuBoard();
    private final SudokuGenerator gen = new SudokuGenerator();
    private final GameState gameState = new GameState();


    public SudokuPane() {
        gridTurtle = new Turtle();
        add(gridTurtle);
        gridTurtle.setVisible(false);
    }


    public void start() {
        model.loadPuzzle(gen.newPuzzle(5));
        reDraw();
    }

    private void reDraw() {
        clear();
        drawGrid();
        drawNumbers();
        drawSelection();
        drawLeftTable();
    }

    private void drawGrid() {
        gridTurtle.setPenColor(Color.black);
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 9) {
                gridTurtle.setPenWidth(7);
            } else if (i % 3 == 0) {
                gridTurtle.setPenWidth(4);
            } else gridTurtle.setPenWidth(0.5);
            gridTurtle.setPosition(oX, oY + i * cell);
            gridTurtle.setDirection(90);
            gridTurtle.step(size);
        }
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 9) {
                gridTurtle.setPenWidth(7);
            } else if (i % 3 == 0) {
                gridTurtle.setPenWidth(4);
            } else gridTurtle.setPenWidth(0.5);
            gridTurtle.setPosition(oX + i * cell, oY);
            gridTurtle.setDirection(180);
            gridTurtle.step(size);
        }
    }

    private void drawNumbers() {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                gridTurtle.setPenColor(Color.black);


                int v = model.getValue(r, c);
                ;
                if (v == 0) continue;

                double x = oX + c * cell + cell / 2.0;
                double y = oY + r * cell + cell / 2.0;

                gridTurtle.setPosition(x, y);
                gridTurtle.setDirection(90);
                gridTurtle.setFont(new Font("Press Start 2P", Font.BOLD, 20));
                gridTurtle.setPenWidth(1);
                if (model.isFixed(r, c)) {
                    gridTurtle.setPenColor(new Color(0, 65, 161));
                }
                if (model.isWrong(r, c)) {
                    gridTurtle.setPenColor(Color.red);
                }
                if (wasSolved) {

                    gridTurtle.setPenColor(new Color(0, 128, 0));
                }
                gridTurtle.printCenter(String.valueOf(v));
            }
        }
    }


    @Override
    protected void onMouseClicked(int x, int y, MouseEvent detail) {
        if ((x > 490 && x < 720) && (y > 132 && y < 169)) {
            holes = 40;
            model.loadPuzzle(gen.newPuzzle(holes));
            selectedRow = -1;
            selectedCol = -1;
            wasSolved = false;
            reDraw();
            return;
        } else if ((x > 490 && x < 720) && (y > 173 && y < 207)) {
            holes = 50;
            model.loadPuzzle(gen.newPuzzle(holes));
            selectedRow = -1;
            selectedCol = -1;
            wasSolved = false;
            reDraw();
            return;
        } else if ((x > 490 && x < 720) && (y > 212 && y < 253)) {
            holes = 60;
            model.loadPuzzle(gen.newPuzzle(holes));

            selectedRow = -1;
            selectedCol = -1;
            wasSolved = false;
            reDraw();
            return;
        }


        if ((x > 490 && x < 720) && (y > 307 && y < 347)) {
            model.loadPuzzle(gen.newPuzzle(holes));
            selectedRow = -1;
            selectedCol = -1;
            wasSolved = false;
            reDraw();
            return;
        }
        if ((x > 490 && x < 720) && (y > 352 && y < 389)) {
            resetGame();
            return;
        }

        //LOAD BUTTON
        if ((x > 527 && x < 598) && (y > 436 && y < 480)) {
            File f = new File("sudoku_start.csv");
            int[][] puzzle = gameState.loadPuzzle(f);
            if (puzzle != null) {
                model.loadPuzzle(puzzle);
                selectedRow = -1;
                selectedCol = -1;
                wasSolved = false;
                reDraw();
            }
            return;
        }
        //SAVE BUTTON
        if ((x > 610 && x < 681) && (y > 436 && y < 480)) {
            File f = new File("sudoku_start.csv");
            gameState.saveStartPuzzle(f, model);
            return;
        }


        if (x < oX || x >= oX + size || y < oY || y >= oY + size) return;

        int col = (x - oX) / cell;
        int row = (y - oY) / cell;
        selectedCol = col;
        selectedRow = row;

        reDraw();


    }

    @Override
    protected void onKeyTyped(KeyEvent e) {

        if (wasSolved) {
            return;
        }

        if (selectedRow == -1 || selectedCol == -1) {
            return;
        }
        if (model.isFixed(selectedRow, selectedCol)) {
            return;
        }


        char ch = e.getKeyChar();


        if (ch >= '1' && ch <= '9') {
            model.setValue(selectedRow, selectedCol, ch - '0');

            boolean nowSolved = model.isSolved();
            if (nowSolved && !wasSolved) {
                System.out.println("congratulation!!!");
            }
            wasSolved = nowSolved;
            reDraw();
            return;
        }


        if (ch == '0') {
            model.setValue(selectedRow, selectedCol, 0);


            reDraw();
        }

    }


    @Override
    protected void onKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_R) {
            resetGame();
            return;
        }
        if (code == KeyEvent.VK_N) {
            model.loadPuzzle(gen.newPuzzle(10));
            selectedRow = -1;
            selectedCol = -1;
            wasSolved = false;
            reDraw();
            return;
        }

        if (selectedRow == -1 || selectedCol == -1) {
            selectedRow = 0;
            selectedCol = 0;
            reDraw();
            return;
        }


        if (code == KeyEvent.VK_LEFT) {
            if (selectedCol > 0) selectedCol--;
            reDraw();
            return;
        }

        if (code == KeyEvent.VK_RIGHT) {
            if (selectedCol < 8) selectedCol++;
            reDraw();
            return;
        }

        if (code == KeyEvent.VK_UP) {
            if (selectedRow > 0) selectedRow--;
            reDraw();
            return;
        }

        if (code == KeyEvent.VK_DOWN) {
            if (selectedRow < 8) selectedRow++;
            reDraw();
            return;
        }

        if (wasSolved) {
            return;
        }
        if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
            if (model.isFixed(selectedRow, selectedCol)) {
                return;
            }
            model.setValue(selectedRow, selectedCol, 0);

            reDraw();
        }

    }

    public void drawSelection() {
        if (selectedRow == -1) {
            return;
        }

        double x0 = oX + selectedCol * cell;
        double y0 = oY + selectedRow * cell;

        gridTurtle.setPosition(x0, y0);
        gridTurtle.setPenColor(new Color(255, 0, 212));
        gridTurtle.setPenWidth(5);
        gridTurtle.setDirection(90);

        for (int i = 0; i < 4; i++) {
            gridTurtle.step(cell);
            gridTurtle.turn(90);
        }
        gridTurtle.setPenColor(Color.black);
        gridTurtle.setPenWidth(1);

    }

    public void drawLeftTable() {
        gridTurtle.setDirection(0);
        gridTurtle.setPosition(605, 250);
        gridTurtle.setScale(0.95);
        gridTurtle.setShape(new ImageTurtleShape("/best.png"));
        gridTurtle.stamp();
    }

    private void resetGame() {
        model.reset();
        selectedRow = -1;
        selectedCol = -1;
        wasSolved = false;
        reDraw();
    }

}

package sk.upjs.paz;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameState {
    public int[][] loadPuzzle(File file) {
        int[][] loadedPuzzle = new int[9][9];
        try (Scanner sc = new Scanner(file)) {
            sc.useDelimiter("[,\\s]+");
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (!sc.hasNextInt()) {
                        System.out.println("File must contain numbers!!");
                        return null;
                    }
                    int v = sc.nextInt();
                    if (v < 0 || v > 9) {
                        System.out.println("Numbers can be only 0,1...9");
                        return null;
                    } else {
                        loadedPuzzle[r][c] = v;
                    }
                }
            }
            if (sc.hasNextInt()) {
                System.out.println("File contains more, than 81 numbers");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e.getMessage());
            return null;
        }
        return loadedPuzzle;
    }

    public void saveStartPuzzle(File file, SudokuBoard model) {
        int[][] start = model.copyOfStart();
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    pw.print(start[r][c]);
                    if (c != 8) {
                        pw.print(',');
                    }
                }
                pw.println();
            }
        } catch (Exception e) {
            System.out.println("Cannot write to file: " + e.getMessage());
            return;
        }
    }
}

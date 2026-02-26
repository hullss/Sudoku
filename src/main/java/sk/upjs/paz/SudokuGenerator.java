package sk.upjs.paz;


public class SudokuGenerator {


    private int[][] baseSolved =
            {
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},

                    {2, 3, 4, 5, 6, 7, 8, 9, 1},
                    {5, 6, 7, 8, 9, 1, 2, 3, 4},
                    {8, 9, 1, 2, 3, 4, 5, 6, 7},

                    {3, 4, 5, 6, 7, 8, 9, 1, 2},
                    {6, 7, 8, 9, 1, 2, 3, 4, 5},
                    {9, 1, 2, 3, 4, 5, 6, 7, 8}
            };
    private int[][] mixedBase = new int[9][9];

    private void swapRows(int r1, int r2) {
        int[] tmp = mixedBase[r1];
        mixedBase[r1] = mixedBase[r2];
        mixedBase[r2] = tmp;
    }

    private void swapCols(int c1, int c2) {
        for (int r = 0; r < 9; r++) {
            int tmp = mixedBase[r][c1];
            mixedBase[r][c1] = mixedBase[r][c2];
            mixedBase[r][c2] = tmp;
        }
    }


    private void swapRowBands(int b1, int b2) {
        for (int i = 0; i < 3; i++) {
            swapRows(b1 * 3 + i, b2 * 3 + i);
        }
    }

    private void swapColStacks(int s1, int s2) {
        for (int i = 0; i < 3; i++) {
            swapCols(s1 * 3 + i, s2 * 3 + i);
        }
    }

    private void shuffle(int steps) {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                mixedBase[r][c] = baseSolved[r][c];
            }
        }
        for (int i = 0; i < steps; i++) {
            int action = (int) (Math.random() * 4);
            int b = (int) (Math.random() * 3);
            int s = (int) (Math.random() * 3);
            if (action == 0) {
                int start = b * 3;
                int r1 = (int) (start + Math.random() * 3);
                int r2 = (int) (start + Math.random() * 3);
                while (r1 == r2) {
                    r2 = (int) (start + Math.random() * 3);
                }

                swapRows(r1, r2);

            } else if (action == 1) {
                int start = s * 3;
                int c1 = (int) (start + Math.random() * 3);
                int c2 = (int) (start + Math.random() * 3);
                while (c1 == c2) {
                    c2 = (int) (start + Math.random() * 3);
                }
                swapCols(c1, c2);

            } else if (action == 2) {
                int b1 = (int) (Math.random() * 3);
                int b2 = (int) (Math.random() * 3);
                while (b1 == b2) {
                    b2 = (int) (Math.random() * 3);
                }
                swapRowBands(b1, b2);
            } else {
                int s1 = (int) (Math.random() * 3);
                int s2 = (int) (Math.random() * 3);
                while (s1 == s2) {
                    s2 = (int) (Math.random() * 3);
                }
                swapColStacks(s1, s2);

            }
        }
    }

    public int[][] newPuzzle(int holes) {
        if (holes < 0) holes = 0;
        if (holes > 81) holes = 81;

        int randomShuffle = (int) (Math.random() * 300) + 50;

        int[][] puzzle = new int[9][9];

        shuffle(randomShuffle);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                puzzle[r][c] = mixedBase[r][c];
            }
        }
        int removed = 0;
        while (removed < holes) {
            int randomRow = (int) (Math.random() * 9);
            int randomCol = (int) (Math.random() * 9);

            if (puzzle[randomRow][randomCol] != 0) {
                puzzle[randomRow][randomCol] = 0;
                removed++;
            }
        }
        return puzzle;
    }
}

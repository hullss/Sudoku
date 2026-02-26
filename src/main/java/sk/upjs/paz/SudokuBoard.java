package sk.upjs.paz;

public class SudokuBoard {
    private int[][] board = new int[9][9];
    private int[][] startBoard = new int[9][9];
    private boolean[][] fixed = new boolean[9][9];
    boolean[][] wrong = new boolean[9][9];


    public void loadPuzzle(int[][] puzzle) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                startBoard[r][c] = puzzle[r][c];
                board[r][c] = puzzle[r][c];
                fixed[r][c] = puzzle[r][c] != 0;
            }
        }
        recomputeWrong();
    }

    public void setValue(int r, int c, int v) {
        if (fixed[r][c]) return;
        if (v < 0 || v > 9) return;
        board[r][c] = v;
        recomputeWrong();
    }

    public void recomputeWrong() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                wrong[r][c] = false;
            }
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                if (v == 0) {
                    continue;
                } else {
                    boolean conflict = false;

                    for (int cc = 0; cc < 9; cc++) {
                        if (cc == c) {
                            continue;
                        }
                        if (board[r][cc] == v) {
                            conflict = true;
                            break;
                        }
                    }
                    if (!conflict) {
                        for (int rr = 0; rr < 9; rr++) {
                            if (rr == r) {
                                continue;
                            }
                            if (board[rr][c] == v) {
                                conflict = true;
                                break;
                            }
                        }
                    }
                    if (!conflict) {
                        int br = (r / 3) * 3;
                        int bc = (c / 3) * 3;

                        for (int rr = br; rr < br + 3; rr++) {
                            for (int cc = bc; cc < bc + 3; cc++) {
                                if (rr == r && cc == c) continue;
                                if (board[rr][cc] == v) {
                                    conflict = true;
                                    break;
                                }
                            }
                            if (conflict) break;
                        }
                    }
                    wrong[r][c] = conflict;
                }
            }

        }

    }


    public void reset() {
        for (int r = 0; r < 9; r++) {
            System.arraycopy(startBoard[r], 0, board[r], 0, 9);
        }
        recomputeWrong();
    }

    public boolean isSolved() {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0 || wrong[r][c]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getValue(int r, int c) {
        return board[r][c];
    }

    public boolean isFixed(int r, int c) {
        return fixed[r][c];
    }

    public boolean isWrong(int r, int c) {
        return wrong[r][c];
    }

    public int[][] copyOfStart() {
        int[][] copyOfStartBoard = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                copyOfStartBoard[r][c] = startBoard[r][c];
            }
        }
        return copyOfStartBoard;
    }
}

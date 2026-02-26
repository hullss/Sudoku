# Sudoku (Student Project)

Desktop **Sudoku** game written in Java with a GUI based on `jpaz2`.

## Features
- Random Sudoku puzzle generation.
- Difficulty levels by number of empty cells:
  - `40` (easy)
  - `50` (medium)
  - `60` (hard)
- Real-time conflict validation in rows, columns, and `3x3` boxes.
- Reset to puzzle start state.
- Save/load puzzle start state from `sudoku_start.csv`.

## Tech Stack
- Java (project is configured for Java `8` in Maven)
- Maven
- `sk.upjs:jpaz2:1.1.1`

## Project Structure
- `src/main/java/sk/upjs/paz/Launcher.java` - application entry point.
- `src/main/java/sk/upjs/paz/SudokuPane.java` - GUI and input handling.
- `src/main/java/sk/upjs/paz/SudokuBoard.java` - board state and validation.
- `src/main/java/sk/upjs/paz/SudokuGenerator.java` - puzzle generation.
- `src/main/java/sk/upjs/paz/GameState.java` - save/load CSV logic.
- `src/main/resources/best.png` - GUI image resource.

## How To Run

### Option A (Recommended): IntelliJ IDEA
1. Open the project in IntelliJ IDEA.
2. Ensure Project SDK is set:
   - `File -> Project Structure -> Project SDK`
3. Open `Launcher.java`.
4. Run `main()` (green Run button near the `main` method).

This is the most reliable option if terminal commands like `java` or `mvn` are not available in your system PATH.

### Option B: Maven (Terminal)
```bash
mvn clean package
mvn exec:java -Dexec.mainClass=sk.upjs.paz.Launcher
```

### Option C: Run Built JAR (Terminal)
```bash
java -jar target/Sudoku.jar
```

## Controls
- Mouse click on a cell: select a cell.
- `1..9`: enter value.
- `0`, `Backspace`, `Delete`: clear value.
- Arrow keys: move selected cell.
- `R`: reset board to initial state.
- `N`: generate a quick new game.
- Right panel buttons:
  - choose difficulty (`40/50/60`)
  - `New game`
  - `Reset`
  - `Load` / `Save` (`sudoku_start.csv`)

## Save File Format (`sudoku_start.csv`)
- Exactly `9` rows and `9` numbers per row.
- Allowed values: `0..9`.
- `0` means empty cell.
- Separators: commas or whitespace.

Example:
```csv
0,6,7,0,9,0,0,0,3
0,0,0,0,0,8,0,0,0
2,0,0,4,0,0,7,0,0
0,0,0,0,0,4,0,0,5
0,0,0,0,5,0,9,0,0
0,0,0,9,0,0,3,0,0
0,0,0,5,4,0,0,0,0
0,0,0,0,0,9,2,0,0
0,0,0,2,0,0,5,0,4
```

## Troubleshooting

### `mvn is not recognized`
Maven is not installed or not added to system `PATH`.

### `java is not recognized`
JDK is not installed or not added to system `PATH`.

### Quick fix
If terminal setup is not ready yet, run the project directly from IntelliJ using `Launcher.main()`.

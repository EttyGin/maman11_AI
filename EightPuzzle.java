import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This class represents a state of the puzzle.
Contains a recursive property, so that the states are interconnected from the source to the goal.
 */
public class EightPuzzle {
    private int[] tiles; // The current state
    private EightPuzzle lastState; // The previous state that brought us to this state
    private int depth; // Actually the number of steps from the origin to the current state
    public final int[] GOAL_STATE = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    // Initialize onstructor
    public EightPuzzle(int[] tiles) {
        lastState = null;
        depth = 0;
        this.tiles = tiles;
    }

    // Constructor for a state halfway to the goal
    public EightPuzzle(int[] tiles, EightPuzzle father) {
        lastState = father;
        depth = father.getDepth() + 1;
        this.tiles = Arrays.copyOf(tiles, tiles.length);
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isGoalState() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != GOAL_STATE[i])
                return false;
        }
        return true;
    }

    public EightPuzzle getFather() {
        return lastState;
    }

    // Returns a list of the following states from this state, according to the
    // steps allowed
    public List<EightPuzzle> getValidMoves() {
        List<EightPuzzle> validMoves = new ArrayList<>();

        int emptyTileIndex = getEmptyIndex();

        if (emptyTileIndex % 3 != 2) { // Go right
            validMoves.add(new EightPuzzle(swapTiles(emptyTileIndex, emptyTileIndex + 1), this));
        }
        if (emptyTileIndex % 3 != 0) { // Go left
            validMoves.add(new EightPuzzle(swapTiles(emptyTileIndex, emptyTileIndex - 1), this));
        }
        if (emptyTileIndex + 3 < tiles.length) { // Go down
            validMoves.add(new EightPuzzle(swapTiles(emptyTileIndex, emptyTileIndex + 3), this));
        }
        if (emptyTileIndex - 3 >= 0) { // Go up
            validMoves.add(new EightPuzzle(swapTiles(emptyTileIndex, emptyTileIndex - 3), this));
        }

        return validMoves;
    }

    // Returns the index of the empty tile
    public int getEmptyIndex() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    private int[] swapTiles(int index1, int index2) {
        int[] newTiles = Arrays.copyOf(tiles, tiles.length);
        int temp = newTiles[index1];
        newTiles[index1] = newTiles[index2];
        newTiles[index2] = temp;

        return newTiles;
    }

    // Checks for equality between 2 states, where equality = the same order of
    // tiles
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EightPuzzle) {
            EightPuzzle other = (EightPuzzle) obj;
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i] != other.getTiles()[i])
                    return false;
            }
        }
        return true;
    }

    // Returns the steps taken from the start to the goal
    public String updateResWay() {
        EightPuzzle tmp = this;
        String res = "";
        int ind = tmp.getEmptyIndex();
        while ((tmp = tmp.getFather()) != null) {
            res = tmp.getTiles()[ind] + " " + res;
            ind = tmp.getEmptyIndex();
        }
        return res;
    }

    public void print(String nameOfSearch, String res, int nodesExpanded) {
        System.out.println("Algorithm " + nameOfSearch);
        System.out.println("nodes expanded: " + nodesExpanded);
        System.out.println("The solution - move these tiles: \t" + res + "\n");
    }
}

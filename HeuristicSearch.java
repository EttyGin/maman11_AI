import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * A kind of generic class that implements the solving of the puzzle,
 * using heuristic algorithms.
 */
public class HeuristicSearch {

    private PriorityQueue<EightPuzzle> frontier;
    private EightPuzzle puzzle;
    private EightPuzzle curStatePuzzle;
    private LinkedList<EightPuzzle> exploredSet;
    private String res = "";
    private int nodesExpanded = 0;

    // A constructor that initializes the priority queue according to the requested
    // algorithm.
    public HeuristicSearch(EightPuzzle puzzle, String type) {
        this.puzzle = puzzle;
        exploredSet = new LinkedList<>();
        if (type.equals("A*")) {
            frontier = new PriorityQueue<>((a, b) -> {// Price calculator so far (number of steps) and heuristic
                    // evaluation
                    int value1 = heuristicCal(a) + a.getDepth();
                    int value2 = heuristicCal(b) + b.getDepth();
                    return value1 - value2;
                });
        } else if (type.equals("GBFS")) {
            frontier = new PriorityQueue<>((a, b) -> heuristicCal(a) - heuristicCal(b));
        }
    }

    // The heuristic calculation - explanation in "readMe" file
    public int heuristicCal(EightPuzzle state) {
        int distances = 0;
        for (int i = 0; i < state.getTiles().length; i++) {
            if (state.getTiles()[i] != i) {
                distances += 2;
                int place = findIndex(i, state.getTiles());
                if (state.getTiles()[i] == 0 && inNeighbor(place, i))
                    distances -= 1;
            }
        }
        return distances;
    }

    // Returns at which index the place is in the array
    private int findIndex(int place, int[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == place) {
                return i;
            }
        }
        return -1;
    }
    
    //Checks whether the value i is in one of the neighbors of its target location
    private boolean inNeighbor(int place, int i) {
        return (place + 1 == i && place % 3 != 2) ||
        (place - 1 == i && place % 3 != 0) || place == i + 3
        || place == i - 3;
    }

    public boolean solve() {
        frontier.add(puzzle);
        while (!frontier.isEmpty()) {
            curStatePuzzle = frontier.poll();
            if (curStatePuzzle.isGoalState()) {
                res = curStatePuzzle.updateResWay();
                return true;
            }

            for (EightPuzzle child : curStatePuzzle.getValidMoves()) {
                if (!exploredAlready(child))
                    frontier.add(child);
            }

            nodesExpanded++; // Count the number of explored nodes - when exiting the list
            exploredSet.add(curStatePuzzle);
        }
        return false;
    }

    // A Boolean method that checks whether the condition has already been
    // investigated
    private boolean exploredAlready(EightPuzzle child) {
        for (EightPuzzle tmp : exploredSet) {
            if (child.equals(tmp))
                return true;
        }
        return false;
    }  

    public int getNodesExp() {
        return nodesExpanded;
    }

    public String getRes() {
        return res;
    }

}

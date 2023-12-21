import java.util.LinkedList;
import java.util.Queue;

/*
 * A kind of generic class that implements the solving of the puzzle,
 * using un-informed algorithms.
 */
class UnInformedSearch {
    private EightPuzzle puzzle;
    private EightPuzzle curStatePuzzle;
    private LinkedList<EightPuzzle> exploredSet;
    private String res = "";
    private int nodesExpanded = 0;

    //A constructor that initializes the priority
    public UnInformedSearch(EightPuzzle puzzle) {
        this.puzzle = puzzle;
        exploredSet = new LinkedList<>();

    }

    public boolean bfsSolve() {
        Queue<EightPuzzle> frontier = new LinkedList<>();
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

            nodesExpanded++;
            exploredSet.add(curStatePuzzle);
        }
        return false;
    }

    public boolean iddfsSolve() { //Limiting the depth of the search
        int depth = 0;
        while (true) {
            if (dfs(depth)) {
                res = curStatePuzzle.updateResWay();
                return true;
            }
            depth++;
        }
    }

    private boolean dfs(int depth) {
        //Reset the data structures for the next search
        LinkedList<EightPuzzle> frontier = new LinkedList<>();
        exploredSet = new LinkedList<>();
        frontier.add(puzzle);

        while (!frontier.isEmpty()) {
            curStatePuzzle = frontier.remove();

            if (curStatePuzzle.isGoalState()) {
                return true;
            }

            for (EightPuzzle child : curStatePuzzle.getValidMoves()) {
                if (child.getDepth() <= depth && !exploredAlready(child)) {
                    frontier.add(child);
                }
            }

            nodesExpanded++; //Count the number of explored nodes - when exiting the list
            exploredSet.add(curStatePuzzle);
        }

        return false;
    }

    //A Boolean method that checks whether the condition has already been investigated
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
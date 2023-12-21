/*
 * A main class that actually solves the problem
 */
public class Main {
    public static void main(String[] args) {

        int[] initState = new int[9];

        // Fills the array with the arguments.
        for (int i = 0; i < args.length; i++) {
            initState[i] = Integer.parseInt(args[i]);
        }

        // Checks if the array contains 9 numbers.
        if (args.length != 9) {
            System.out.println("9 numbers must be entered");
            return;
        }

        EightPuzzle puzzle = new EightPuzzle(initState);

        // Solves the problem using the BFS algorithm
        UnInformedSearch solverBFS = new UnInformedSearch(puzzle);
        boolean resBfs = solverBFS.bfsSolve();
        if (!resBfs) {
            System.out.println("No result");
        } else {
            puzzle.print("BFS", solverBFS.getRes(), solverBFS.getNodesExp());
        }

        // Solves the problem using the IDDFS algorithm
        UnInformedSearch solverIDDFS = new UnInformedSearch(puzzle);
        boolean resIddfs = solverIDDFS.iddfsSolve();
        if (!resIddfs) {
            System.out.println("No result");
        } else {
            puzzle.print("IDDFS", solverIDDFS.getRes(), solverIDDFS.getNodesExp());
        }

        // Solves the problem using the GBFS algorithm
        HeuristicSearch solver3 = new HeuristicSearch(puzzle, "GBFS");
        boolean resGBFS = solver3.solve();
        if (!resGBFS) {
            System.out.println("No result");
        } else {
            puzzle.print("GBFS", solver3.getRes(), solver3.getNodesExp());
        }

        // Solves the problem using the A* algorithm
        HeuristicSearch solver4 = new HeuristicSearch(puzzle, "A*");
        boolean resAStar = solver4.solve();
        if (!resAStar) {
            System.out.println("No result");
        } else {
            puzzle.print("A*", solver4.getRes(), solver4.getNodesExp());
        }
    }
}

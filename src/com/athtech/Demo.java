package com.athtech;

import java.util.List;

public class Demo {

    public static void main(String[] args) {

        UnweightedShortestPathFinder<StateNode> finder = new BranchAndBoundAlgorithm<>();

        long startTime = System.currentTimeMillis();

        List<StateNode> path =
                finder.search(StateNode.getInitialStateNode(),
                        StateNode::isSolutionState);

        long endTime = System.currentTimeMillis();

        System.out.println("Duration: " + (endTime - startTime) + " milliseconds.");

        int fieldLength = ("" + path.size()).length();

        if (path.isEmpty()) {
            System.out.println("No solution.");
        }
        else {
            int i = 0;
            for (StateNode step : path) {
                System.out.printf("State %" + fieldLength + "d: %s\n", ++i, step);
            }
        }

    }
}

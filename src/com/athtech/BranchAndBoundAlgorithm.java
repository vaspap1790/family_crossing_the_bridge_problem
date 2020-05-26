package com.athtech;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class implements the branch&bound search path finder.
 *
 * @param <N> the node implementation type.
 */
public class BranchAndBoundAlgorithm<N extends Iterable<N>> implements UnweightedShortestPathFinder<N> {

    /**
     * Searches for a shortest path using branch&bound search.
     *
     * @param source          the source node.
     * @param targetPredicate the target node predicate.
     * @return the shortest path from source to the first node that passes the
     *         target node predicate.
     */
    @Override
    public List<N> search(N source, Predicate<N> targetPredicate) {

        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(targetPredicate, "The target predicate is null.");

        Map<N, N> parentMap = new HashMap<>();
        Deque<N> queue = new ArrayDeque<>();

        parentMap.put(source, null);
        queue.addLast(source);
        StateNode finalSolution = StateNode.getBadSolutionNode();

        while (!queue.isEmpty()) {

            N current = queue.removeFirst();

            if (targetPredicate.test(current)) {
                StateNode currentStateNode = (StateNode) current;
                if(Math.abs(currentStateNode.getTorch().getBatteryLife()) < Math.abs(finalSolution.getTorch().getBatteryLife())){
                    finalSolution = currentStateNode;
                }
            }

            for (N child : current) {
                StateNode childNode = (StateNode) child;
                if(childNode.getTorch().getBatteryLife() >= 0){
                    parentMap.put(child, current);
                    queue.addFirst(child);
                }
            }
        }

        return traceBackPath((N) finalSolution, parentMap);
    }
}
package com.athtech;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class implements the depth-first search path finder.
 *
 * @param <StateNode> the node implementation type.
 */
public class DepthFirstSearchPathFinder<StateNode extends Iterable<StateNode>> implements UnweightedShortestPathFinder<StateNode> {

    /**
     * Searches for a shortest path using depth-first search.
     * 
     * @param source          the source node.
     * @param targetPredicate the target node predicate.
     * @return the shortest path from source to the first node that passes the
     *         target node predicate.
     */
    @Override
    public List<StateNode> search(StateNode source, Predicate<StateNode> targetPredicate) {

        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(targetPredicate, "The target predicate is null.");

        Map<StateNode, StateNode> parentMap = new HashMap<>();
        Deque<StateNode> queue = new ArrayDeque<>();

        parentMap.put(source, null);
        queue.addLast(source);

        while (!queue.isEmpty()) {
            StateNode current = queue.removeFirst();

            if (targetPredicate.test(current)) {
                return tracebackPath(current, parentMap);
            }

            for (StateNode child : current) {
                if (!parentMap.containsKey(child)) {
                    parentMap.put(child, current);
                    queue.addFirst(child);
                }
            }
        }

        return Collections.<StateNode>emptyList();
    }
}
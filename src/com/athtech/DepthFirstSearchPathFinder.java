package com.athtech;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class implements the depth-first search path finder.
 *
 * @param <N> the node implementation type.
 */
public class DepthFirstSearchPathFinder<N extends Iterable<N>> implements UnweightedShortestPathFinder<N> {

    /**
     * Searches for a shortest path using depth-first search.
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

        while (!queue.isEmpty()) {
            N current = queue.removeFirst();

            if (targetPredicate.test(current)) {
                return traceBackPath(current, parentMap);
            }

            for (N child : current) {
                if (!parentMap.containsKey(child)) {
                    parentMap.put(child, current);
                    queue.addFirst(child);
                }
            }
        }

        return Collections.<N>emptyList();
    }
}
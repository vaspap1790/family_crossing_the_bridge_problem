package com.athtech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * This interface defines the API for shortest path algorithms in unweighted 
 * graphs.
 *
 */
public interface UnweightedShortestPathFinder<StateNode extends Iterable<StateNode>> {

    /**
     * Searches a shortest, unweighted path from {@code source} to any node for
     * which {@code targetPredicate.test} returns {@code true}. 
     * 
     * @param source          the source node.
     * @param targetPredicate the target node predicate.
     * @return a shortest path from {@code source} to the first node that passes
     *         the target node predicate, or an empty list if there is no such.
     */
    List<StateNode> search(StateNode source, Predicate<StateNode> targetPredicate);

    /**
     * Constructs a shortest path.
     * 
     * @param target    the target node.
     * @param parentMap the map mapping each node to its predecessor on the 
     *                  shortest path.
     * @return a shortest path.
     */
    default List<StateNode> tracebackPath(StateNode target, Map<StateNode, StateNode> parentMap) {
        List<StateNode> ret = new ArrayList<>();
        StateNode current = target;

        while (current != null) {
            ret.add(current);
            current = parentMap.get(current);
        }

        Collections.<StateNode>reverse(ret);
        return ret;
    }
}
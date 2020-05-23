package com.athtech;

import java.util.*;

/**
 * This class implements a state of the "Family crossing the bridge" problem.
 */
public class StateNode implements Iterable<StateNode> {

    /**
     * The minimum bridge capacity.
     */
    private static final int MIN_BRIDGE_CAPACITY = 1;

    /**
     * The maximum bridge capacity.
     */
    private final int MAX_BRIDGE_CAPACITY = 2;

    /**
     * The torch.
     */
    private static Torch torch = new Torch();

    /**
     * The Family Members.
     */
    private static final Grandpa grandpa = new Grandpa();
    private static final Father father = new Father();
    private static final Mother mother = new Mother();
    private static final Sister sister = new Sister();
    private static final Brother brother = new Brother();

    /**
     * The amount of family members at the source bank.
     */
    private final List<FamilyMember> figuresAtSourceBank;

    /**
     * The amount of family members at the target bank.
     */
    private final List<FamilyMember> figuresAtTargetBank;


    /**
     * Constructs a state.
     *
     * @param figuresAtSourceBank amount of figures at source bank.
     * @param figuresAtTargetBank amount of figures at target bank.
     * @param torch               the torch.
     */
    public StateNode(List<FamilyMember> figuresAtSourceBank, List<FamilyMember> figuresAtTargetBank, Torch torch) {
        this.figuresAtSourceBank = figuresAtSourceBank;
        this.figuresAtTargetBank = figuresAtTargetBank;
        this.torch = torch;
    }

    /**
     * Creates the source state node.
     *
     * @return the initial state node.
     */
    public static StateNode getInitialStateNode() {

        List figuresAtSourceBank = Arrays.asList(grandpa, father, mother, sister, brother);
        List figuresAtTargetBank = new ArrayList();
        torch.setLocation(TorchLocation.SOURCE_BANK);

        return new StateNode(figuresAtSourceBank,
                figuresAtTargetBank,
                torch);
    }

    /**
     * Checks whether this state encodes a solution state, in which all figures
     * are at the target bank.
     *
     * @return {@code true} if this state is a solution state.
     */
    public boolean isSolutionState() {
        return torch.getLocation() == TorchLocation.TARGET_BANK
                && torch.getBatteryLife() >= 0
                && figuresAtTargetBank.size() == 5
                && figuresAtSourceBank.size() == 0;
    }

    /**
     * Checks whether this state is terminal, which is the case whenever the torch
     * runs out of battery
     *
     * @return {@code true} if this state is terminal.
     */
    public boolean isTerminalState() {
        return torch.getBatteryLife() < 0;
    }

    /**
     * Returns an iterator over this state's neighbor states.
     *
     * @return an iterator.
     */
    @Override
    public Iterator<StateNode> iterator() {
        return new NeighborStateIterator();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");

        // Situation at the source bank.
        figuresAtSourceBank.forEach(figure -> sb.append(figure.toString() + " "));
        sb.append("]");

        // Draw torch location and the bridge.
        switch (torch.getLocation()) {
            case SOURCE_BANK: {
                sb.append(" =< |------|   ");
                break;
            }
            case TARGET_BANK: {
                sb.append("    |------|  >= ");
                break;
            }
        }

        // Situation at the destination bank.
        sb.append("[ ");
        figuresAtTargetBank.forEach(figure -> sb.append(figure.toString() + " "));
        sb.append("]");

        // Time left
        sb.append("   -Battery life: " + torch.getBatteryLife() + " minutes");

        return sb.toString();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateNode)) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(figuresAtSourceBank, stateNode.figuresAtSourceBank) &&
                Objects.equals(figuresAtTargetBank, stateNode.figuresAtTargetBank);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        return Objects.hash(figuresAtSourceBank, figuresAtTargetBank);
    }

    /**
     * Implements the actual iterator.
     */
    private class NeighborStateIterator implements Iterator<StateNode> {

        private final Iterator<StateNode> iterator;

        public NeighborStateIterator() {
            this.iterator = generateNeighbors();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public StateNode next() {
            return iterator.next();
        }

        // Populates the list of neighbor states.
        private Iterator<StateNode> generateNeighbors() {
            if (isTerminalState()) {
                // Ignore terminal state nodes.
                return Collections.<StateNode>emptyIterator();
            }

            List<StateNode> list = new ArrayList<>();

//            switch (torch.getLocation()) {
//                case SOURCE_BANK: {
//                    trySendFromSourceBank(list);
//                    break;
//                }
//
//                case TARGET_BANK: {
//                    trySendFromTargetBank(list);
//                    break;
//                }
//            }

            return list.iterator();
        }

        // Attempts to send some figures from the source bank to the target
        // bank.
//        private void trySendFromSourceBank(List<StateNode> list) {
//            int availableMissionaries = Math.min(missionaries, boatCapacity);
//            int availableCannibals = Math.min(cannibals, boatCapacity);
//
//            for (int capacity = 1; capacity <= boatCapacity; ++capacity) {
//                for (int m = 0; m <= availableMissionaries; ++m) {
//                    for (int c = 0; c <= availableCannibals; ++c) {
//                        if (0 < c + m && c + m <= capacity) {
//                            list.add(new StateNode(missionaries - m,
//                                    cannibals - c,
//                                    totalMissionaries,
//                                    totalCannibals,
//                                    boatCapacity,
//                                    BoatLocation.TARGET_BANK));
//                        }
//                    }
//                }
//            }
//        }
//
//        // Attempts to send some figures from the target bank to the source
//        // bank.
//        private void trySendFromTargetBank(List<StateNode> list) {
//            int availableMissionaries =
//                    Math.min(totalMissionaries - missionaries, boatCapacity);
//            int availableCannibals =
//                    Math.min(totalCannibals - cannibals, boatCapacity);
//
//            for (int capacity = 1; capacity <= boatCapacity; ++capacity) {
//                for (int m = 0; m <= availableMissionaries; ++m) {
//                    for (int c = 0; c <= availableCannibals; ++c) {
//                        if (0 < c + m && c + m <= capacity) {
//                            list.add(new StateNode(missionaries + m,
//                                    cannibals + c,
//                                    totalMissionaries,
//                                    totalCannibals,
//                                    boatCapacity,
//                                    BoatLocation.SOURCE_BANK));
//                        }
//                    }
//                }
//            }
//        }
    }


    /**
     * Checks that {@code integer} is no less than {@code minimum}, and if it
     * is, throws an exception with message {@code errorMessage}.
     *
     * @param integer      the integer to check.
     * @param minimum      the minimum allowed value of {@code integer}.
     * @param errorMessage the error message.
     * @throws IllegalArgumentException if {@code integer < minimum}.
     */
    private static void checkIntNotLess(int integer,
                                        int minimum,
                                        String errorMessage) {
        if (integer < minimum) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Checks that {@code integer} is not negative.
     *
     * @param integer      the integer to check.
     * @param errorMessage the error message for the exception upon failure.
     */
    private static void checkNotNegative(int integer, String errorMessage) {
        checkIntNotLess(integer, 0, errorMessage);
    }
}
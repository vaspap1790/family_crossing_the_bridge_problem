package com.athtech;

import java.util.*;

/**
 * This class implements a state of the "Family crossing the bridge" problem.
 */
public class StateNode implements Iterable<StateNode> {


    /**
     * The maximum bridge capacity.
     */
    private static final int MAX_BRIDGE_CAPACITY = 2;


    /**
     * The torch.
     */
    private Torch torch = new Torch();


    /**
     * The amount of family members at the source bank.
     */
    private final List<FamilyMember> figuresAtSourceBank;


    /**
     * The amount of family members at the target bank.
     */
    private final List<FamilyMember> figuresAtTargetBank;

    public Torch getTorch() {
        return torch;
    }

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

        List figuresAtSourceBank = Arrays.asList(new Grandpa(), new Father(), new Mother(), new Sister(), new Brother());
        List figuresAtTargetBank = new ArrayList();
        Torch torch = new Torch();

        return new StateNode(figuresAtSourceBank, figuresAtTargetBank, torch);
    }


    /**
     * Creates a bad solution state node.
     *
     * @return the initial state node.
     */
    public static StateNode getBadSolutionNode() {

        List figuresAtSourceBank = new ArrayList();
        List figuresAtTargetBank = Arrays.asList(new Grandpa(), new Father(), new Mother(), new Sister(), new Brother());
        Torch torch = new Torch();
        torch.setLocation(TorchLocation.TARGET_BANK);
        torch.setBatteryLife(-20);

        return new StateNode(figuresAtSourceBank, figuresAtTargetBank, torch);
    }


    /**
     * Checks whether this state encodes a solution state, in which all figures
     * are at the target bank.
     *
     * @return {@code true} if this state is a solution state.
     */
    public boolean isSolutionState() {
        return torch.getLocation() == TorchLocation.TARGET_BANK
                && figuresAtTargetBank.size() == 5
                && figuresAtSourceBank.size() == 0;
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
                sb.append(" =<  |------|    ");
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
        return Objects.equals(getTorch(), stateNode.getTorch()) &&
                Objects.equals(figuresAtSourceBank, stateNode.figuresAtSourceBank) &&
                Objects.equals(figuresAtTargetBank, stateNode.figuresAtTargetBank);
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTorch(), figuresAtSourceBank, figuresAtTargetBank);
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

            List<StateNode> list = CombinationUtil.generateCombination(StateNode.this.figuresAtSourceBank,
                    StateNode.this.figuresAtTargetBank, StateNode.this.torch, StateNode.MAX_BRIDGE_CAPACITY);

            return list.iterator();
        }

    }

}
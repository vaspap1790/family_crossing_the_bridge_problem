package com.athtech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

//        List<FamilyMember> notCrossed = Arrays.asList(new Grandpa(), new Mother(), new Father(), new Sister(), new Brother());
////        List<FamilyMember> notCrossed = Arrays.asList(new Father(), new Sister(), new Brother());
//        List<FamilyMember> crossed = new ArrayList<>();
//        Torch torch = new Torch();
//        int r = 2;

//        torch.setLocation(TorchLocation.TARGET_BANK);
//        torch.setBatteryLife(18);
//        crossed.add(new Grandpa());
//        crossed.add(new Mother());

//        for (StateNode stateNode : CombinationUtil.generateCombination(notCrossed, crossed, torch, r)) {
//            System.out.println(stateNode);
//        }

        UnweightedShortestPathFinder<StateNode> Dfinder = new DepthFirstSearchPathFinder<>();

        long startTime = System.currentTimeMillis();

        List<StateNode> path =
                Dfinder.search(StateNode.getInitialStateNode(),
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

package com.athtech;

import java.util.ArrayList;
import java.util.List;

/**
 * This utility class is used to generate the neighbour states of a StateNode
 */
public class CombinationUtil {

    /**
     *
     * @param notCrossed       ---> Input List of family Members at source bank
     * @param crossed          ---> Input List of family Members at target bank
     * @param torch            ---> the Torch
     * @param tempList         ---> Temporary List to store current combination
     * @param start            ---> Staring index in departureList
     * @param end              ---> Ending index in departureList
     * @param index            ---> Current index in tempList
     * @param bridge_capacity  ---> Size of a combination
     * @param returnList       ---> the returning List of nodes
     */
    static void combinationUtil(List<FamilyMember> notCrossed, List<FamilyMember> crossed, Torch torch,
                                List<FamilyMember> tempList, int start, int end, int index, int bridge_capacity,
                                List<StateNode> returnList) {

        List<FamilyMember> departList;
        if (torch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
            departList = notCrossed;
        } else {
            departList = crossed;
        }

        // Current combination is ready
        if (index == bridge_capacity) {

            List<FamilyMember> tempDep;
            List<FamilyMember> tempArr;

            if (torch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
                tempDep = new ArrayList<>(notCrossed);
                tempArr = new ArrayList<>(crossed);
            } else {
                tempDep = new ArrayList<>(crossed);
                tempArr = new ArrayList<>(notCrossed);
            }

            Torch tempTorch = new Torch(torch);

            for (int j = 0; j < bridge_capacity; j++) {
                tempArr.add(tempList.get(j));
                tempDep.remove(tempList.get(j));
            }

            if (tempList.get(0).getTimeToCross() > tempList.get(1).getTimeToCross()) {
                tempTorch.setBatteryLife(tempTorch.getBatteryLife() - tempList.get(0).getTimeToCross());
            } else {
                tempTorch.setBatteryLife(tempTorch.getBatteryLife() - tempList.get(1).getTimeToCross());
            }

            if (tempTorch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
                tempTorch.setLocation(TorchLocation.TARGET_BANK);
                returnList.add(new StateNode(tempDep, tempArr, tempTorch));
            } else {
                tempTorch.setLocation(TorchLocation.SOURCE_BANK);
                returnList.add(new StateNode(tempArr, tempDep, tempTorch));
            }
            return;
        }

        /**
         * Replace index with all possible elements. The condition
         * "end-i+1 >= bridge_capacity-index" makes sure that including
         * one element at index will make a combination with remaining
         * element at remaining positions
         */
        for (int i = start; i <= end && end - i + 1 >= bridge_capacity - index; i++) {
            tempList.set(index, departList.get(i));
            combinationUtil(notCrossed, crossed, torch, tempList, i + 1, end, index + 1, bridge_capacity, returnList);
        }
    }

    /**
     *
     * The main function that generates all combinations of size bridge_capacity
     * in List of size n. This function mainly uses combinationUtil()
     *
     * @param notCrossed       ---> Input List of family Members at source bank
     * @param crossed          ---> Input List of family Members at target bank
     * @param torch            ---> the Torch
     * @param bridge_capacity  ---> Size of a combination
     * @return                 ---> the returning List of nodes
     */
    static List<StateNode> generateCombination(List<FamilyMember> notCrossed,
                                               List<FamilyMember> crossed, Torch torch, int bridge_capacity) {

        List<StateNode> returnList = new ArrayList<>();
        List<FamilyMember> departList;
        if (torch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
            departList = notCrossed;
        } else {
            departList = crossed;
        }

        for (int j = 0; j < departList.size(); j++) {

            List<FamilyMember> tempDep;
            List<FamilyMember> tempArr;

            if (torch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
                 tempDep = new ArrayList<>(notCrossed);
                 tempArr = new ArrayList<>(crossed);
            } else {
                 tempDep = new ArrayList<>(crossed);
                 tempArr = new ArrayList<>(notCrossed);
            }
            Torch tempTorch = new Torch(torch);

            tempArr.add(departList.get(j));
            tempDep.remove(departList.get(j));
            tempTorch.setBatteryLife(tempTorch.getBatteryLife() - departList.get(j).getTimeToCross());

            if (tempTorch.getLocation().equals(TorchLocation.SOURCE_BANK)) {
                tempTorch.setLocation(TorchLocation.TARGET_BANK);
                returnList.add(new StateNode(tempDep, tempArr, tempTorch));
            } else {
                tempTorch.setLocation(TorchLocation.SOURCE_BANK);
                returnList.add(new StateNode(tempArr, tempDep, tempTorch));
            }
        }

        // A temporary List to store all combination one by one
        List<FamilyMember> tempList = new ArrayList<>();
        while (tempList.size() < bridge_capacity) tempList.add(new Brother());

        // Generate all combinations using tempList
        combinationUtil(notCrossed, crossed, torch, tempList, 0, departList.size() - 1, 0, bridge_capacity, returnList);

        return returnList;
    }

}

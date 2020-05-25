package com.athtech;

import java.util.ArrayList;
import java.util.List;

public class CombinationUtil {

    /**
     * crossed          ---> Input List of family Members at source bank
     * notCrossed       ---> Input List of family Members at target bank
     * tempList         ---> Temporary List to store current combination
     * start & end      ---> Staring and Ending indexes in departureList
     * index            ---> Current index in tempList
     * bridge_capacity  ---> Size of a combination
     * returnList       ---> the returning List of nodes
     */
    static void combinationUtil(List<FamilyMember> notCrossed, List<FamilyMember> crossed, Torch torch,
                                List<FamilyMember> tempList, int start, int end, int index, int bridge_capacity,
                                List<StateNode> returnList) {

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

        // replace index with all possible elements. The condition
        // "end-i+1 >= bridge_capacity-index" makes sure that including one element
        // at index will make a combination with remaining element at remaining positions
        for (int i = start; i <= end && end - i + 1 >= bridge_capacity - index; i++) {
            tempList.set(index, notCrossed.get(i));
            combinationUtil(notCrossed, crossed, torch, tempList, i + 1, end, index + 1, bridge_capacity, returnList);
        }
    }

    // The main function that generates all combinations of size r
    // in List1 of size n. This function mainly uses combinationUtil()
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

        // Generate all combinations using temporary array 'data[]'
        combinationUtil(notCrossed, crossed, torch, tempList, 0, departList.size() - 1, 0, bridge_capacity, returnList);

        return returnList;
    }

}

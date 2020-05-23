package com.athtech;

public class Brother extends FamilyMember{

    public Brother() {
        super(1);
    }

    @Override
    public String toString() {
        return "B{"+ timeToCross + '}';
    }
}

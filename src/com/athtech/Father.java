package com.athtech;

public class Father extends FamilyMember{

    public Father(){
        super(8);
    }

    @Override
    public String toString() {
        return "F{"+ timeToCross + '}';
    }
}
    
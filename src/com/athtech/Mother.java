package com.athtech;

public class Mother extends FamilyMember {

    public Mother(){
        super(6);
    }

    @Override
    public String toString() {
        return "M{"+ timeToCross + '}';
    }
}

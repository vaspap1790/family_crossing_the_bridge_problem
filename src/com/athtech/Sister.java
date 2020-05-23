package com.athtech;

public class Sister extends FamilyMember{

    public Sister(){
        super(3);
    }

    @Override
    public String toString() {
        return "S{"+ timeToCross + '}';
    }
}

package com.athtech;

public class Grandpa extends FamilyMember {

    public Grandpa() {
        super(12);
    }

    @Override
    public String toString() {
        return "G{"+ timeToCross + '}';
    }

}

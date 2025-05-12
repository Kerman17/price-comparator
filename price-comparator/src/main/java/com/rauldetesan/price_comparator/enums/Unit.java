package com.rauldetesan.price_comparator.enums;

public enum Unit {
    ML, L, G, KG, ROLE, BUC;


    /**
     * Converts every unit input to enum standard.
     *
     * @param inputUnit input
     * @return eliminates spaces and converts to uppercase to match enum.
     */

    public static Unit convertInput(String inputUnit){
        return Unit.valueOf(inputUnit.trim().toUpperCase());
    }

}

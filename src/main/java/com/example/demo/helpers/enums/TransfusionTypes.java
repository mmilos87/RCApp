package com.example.demo.helpers.enums;

public enum TransfusionTypes {
  BLOOD(0),   //100
  BLOOD_PLASMA(0),    //15
  PLATELETS(0),   //15
  BLOOD_REJECTED(0),
  BLOOD_PLASMA_REJECTED(0),
  PLATELETS_REJECTED(0);

private final int daysToNextGiving;

  TransfusionTypes(int daysToNextGiving) {
    this.daysToNextGiving = daysToNextGiving;
  }

  public int getDaysToNextGiving() {
    return daysToNextGiving;
  }
}

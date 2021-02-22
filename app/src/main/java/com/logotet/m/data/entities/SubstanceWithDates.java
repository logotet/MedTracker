package com.logotet.m.data.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubstanceWithDates {
    @Embedded
    public Substance substance;

    @Relation(parentColumn = "name", entityColumn = "substance_name", entity = ActiveDate.class)
    public List<ActiveDate> dates;

    public Substance getSubstance() {
        return substance;
    }

    public void setSubstance(Substance substance) {
        this.substance = substance;
    }

    public List<ActiveDate> getDates() {
        return dates;
    }

    public void setDates(List<ActiveDate> dates) {
        this.dates = dates;
    }
}

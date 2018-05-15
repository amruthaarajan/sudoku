package com.agilysys.models;

import lombok.Data;

/**
 * Created by amruthaa on 5/14/18.
 */
@Data
public class Cell {
    int val;
    int row;
    int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getVal() {
        return val;
    }
}

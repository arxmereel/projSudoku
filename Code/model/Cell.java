package model;

import java.util.HashSet;
import java.util.Set;

/** 
 * Basic code for the Cell Class for Sudoku.
 * 
 * @author maxtung
 */

public class Cell {
    protected boolean editable;
    protected int value;
    protected Set<Integer> candidates;

    //DEFAULTS Cell value to ZERO. Use for solvable Cells
    public Cell() {
        this.editable = true;
        this.value = 0;
        this.candidates = new HashSet<>();
    }

    //Fixed Value. Use to form Puzzle Problem
    public Cell(int val) {
        this.editable = false;
        this.value = val;
        this.candidates = new HashSet<>();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getValue() {
        return value;
    }

    //Setter. Will return value if cell is editable, -1 if not.
    public int setValue(int val){
        if (editable){
            this.value = val;
            return val;
        } else{
            return -1;
        }
    }

    public Set<Integer> getCandidates() {
        return candidates;
    }

    //Adds More candidates. PSA: still takes regular aray as Param
    public void setCandidates(int[] newCandidates) {
        for (int candidate : newCandidates) {
            this.candidates.add(candidate);
        }
    }

    public void clearCandidates(){
        this.candidates = new HashSet<>();
    } 

    //Removes a Singel Candidate
    public void delCandidate(int del){
        this.candidates.remove(del);
    }

}
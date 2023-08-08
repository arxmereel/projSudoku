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

    /**
     * Base Constructor. Defaults to an editable cell with value Zero.
     */
    public Cell() {
        this.editable = true;
        this.value = 0;
        this.candidates = new HashSet<>();
    }

    /**
     *  Fixed value constructor to form uzzle problem
     * @param val   cell value
     */
    public Cell(int val) {
        this.editable = false;
        this.value = val;
        this.candidates = new HashSet<>();
    }

    /**
     * Clone Constructor
     * 
     * @param otherCell cell to clone
     */

    public Cell(Cell otherCell) {
        this.editable = otherCell.editable;
        this.value = otherCell.value;
        this.candidates = new HashSet<>(otherCell.candidates);
    }

    /**
     * Returns if the cell is Editable
     * 
     * @return  if the cell is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets a cell's editability status
     * @param editable  cell's new editable value
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /** Returns the Value of the cell
     * 
     * @return  cell value
     */
    public int getValue() {
        return value;
    }

    /** Basic Value setter.
     * 
     * @param val   new value
     * @return  value if cell is editable, -1 if not.
     */
    public boolean setValue(int val){
        if (editable){
            this.value = val;
            return true;
        } else{
            return false;
        }
    }

    /** Returns the candidates for a given cell.
     * 
     * @return set of integer candidates.
     */
    public Set<Integer> getCandidates() {
        return candidates;
    }

    /** Adds a single new candidate to the set of candidates.
     * 
     * @param newCandidate
     */
    public void addCandidate(int newCandidate) {
        this.candidates.add(newCandidate);
    }

    public void clearCandidates(){
        this.candidates = new HashSet<>();
    } 

    //Removes a Singel Candidate
    public void delCandidate(int del){
        this.candidates.remove(del);
    }

}

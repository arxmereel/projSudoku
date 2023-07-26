/**
 * @author maxtung
 */

public class Cell {
    protected boolean editable;
    protected int value;
    protected int[] candidates;

    public Cell(int val) {
        this.editable = true;
        this.value = val;
        this.candidates = new int[0];
    }

    public boolean isEditable() {
        return editable;
    }


}
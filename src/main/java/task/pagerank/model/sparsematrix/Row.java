package task.pagerank.model.sparsematrix;

import java.util.HashMap;

/**
 * Created by Nail on 20.03.2017.
 */
public class Row extends HashMap<Integer, Double> implements Vector{

    private int length;

    /** KEYS are column positions **/
    public Row(int length) {
        super();
        this.length = length;
    }

    public void setQuick(int column, double value) {
        this.put(column, value);
    }

    public int getLength() {
        return length;
    }

    public double getColumn(int column) {
        return this.get(column);
    }

}

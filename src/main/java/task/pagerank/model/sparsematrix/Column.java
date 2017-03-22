package task.pagerank.model.sparsematrix;

import java.util.HashMap;

/**
 * Created by Nail on 20.03.2017.
 */
public class Column extends HashMap<Integer, Double> implements Vector {

    private int length;

    /** KEYS are row positions **/
    public Column(int length) {
        super();
        this.length = length;
    }

    public void setQuick(int row, double value) {
        this.put(row, value);
    }

    public int getLength() {
        return length;
    }

    public double getRow(int row) {
        return this.get(row);
    }

}

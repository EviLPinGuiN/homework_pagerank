package task.pagerank.model.sparsematrix;

import java.util.Map;

/**
 * Created by Nail on 20.03.2017.
 */
public interface Vector extends Map<Integer, Double> {

    void setQuick(int column, double value);

    int getLength();

}

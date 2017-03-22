package task.pagerank.rank;

import task.pagerank.model.sparsematrix.SparseMatrix;

/**
 * Created by Nail on 22.03.2017.
 */
public interface IIterative {

    double [] calculate (SparseMatrix sparseMatrix);

    long getTime();

}

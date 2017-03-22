package task.pagerank.rank;

import task.pagerank.model.sparsematrix.SparseMatrix;

import java.util.concurrent.Callable;

import static task.pagerank.rank.Iterative.DAMPING_FACTOR;

/**
 * Created by Nail on 21.03.2017.
 */
public class IterativeRun implements Callable<Double> {



    private double[] preRank;
    private SparseMatrix sparseMatrix;
    private int index;

    /**Class tell how one thread compute pagerank**/
    public IterativeRun(double[] preRank, SparseMatrix sparseMatrix, int index) {
        this.preRank = preRank;
        this.sparseMatrix = sparseMatrix;
        this.index = index;
    }

    @Override
    public Double call() {

        double[] linkSum = sparseMatrix.linkSum();
        double newRank = 1.0;
        double tempSum = 0.0;
        for (int j = 0; j < sparseMatrix.getNumColumns(); j++) {
            if (sparseMatrix.getValue(j, index) == 1.0) {
                tempSum += preRank[j] / linkSum[j];
            }
        }

        newRank = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * tempSum;
        return newRank;
    }
}

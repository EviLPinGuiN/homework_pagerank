package task.pagerank.rank;

import task.pagerank.model.sparsematrix.SparseMatrix;


import java.util.concurrent.*;

/**
 * Created by Nail on 21.03.2017.
 */
public class MultiIterative extends Iterative implements IIterative{

    @Override
    public double[] calculate(SparseMatrix sparseMatrix) {

        long startTime = System.currentTimeMillis();
        /**while  PRECISION > 1.0e-4**/
        do {
            tempArray = new double[MATRIX_SIZE];
            for (int i = 0; i < tempArray.length; i++) {
                tempArray[i] = pagerank[i];
            }
            ExecutorService executor = Executors.newCachedThreadPool();
            /**One thread = one page PR **/
            for (int i = 0; i < sparseMatrix.getNumRows(); i++) {
                Future<Double> future = executor.submit(new IterativeRun(tempArray, sparseMatrix, i));
                try {
                    pagerank[i] = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } while (difference(pagerank, tempArray) > PRECISION);

        long stopTime = System.currentTimeMillis();

        this.time = stopTime - startTime;

        return pagerank;

    }


}

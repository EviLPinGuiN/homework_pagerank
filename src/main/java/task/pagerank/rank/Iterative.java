package task.pagerank.rank;

import task.pagerank.model.sparsematrix.SparseMatrix;

/**
 * Created by Nail on 18.03.2017.
 */
public class Iterative implements IIterative {

    protected static final double DAMPING_FACTOR = 0.85;
    protected static final double PRECISION = 1.0e-4;
    protected final static int MATRIX_SIZE = 100;


    protected double[] pagerank;
    protected double[] tempArray;
    protected long time;
    private double sum;

    public Iterative() {
        this.pagerank = new double[MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            this.pagerank[i] = 1.0;
            this.sum += pagerank[i];

        }
    }

    public double[] calculate(double[][] matrix) {
        double[] linkSum = new double[MATRIX_SIZE];
        for (int i = 0; i < matrix.length; i++) {
            double temp = 0.0;
            for (int j = 0; j < matrix.length; j++) {
                temp += matrix[i][j];
            }
            linkSum[i] = temp;
        }
        double prec = 0;
        int iter = 0;
        long startTime = System.currentTimeMillis();
        do {
            tempArray = new double[MATRIX_SIZE];
            for (int i = 0; i < tempArray.length; i++) {
                tempArray[i] = pagerank[i];
            }
            /** PR(i) = (1-d) + d * (PR(j)/n(j) )
             *  d - damping factor
             *  PR(j) - the PageRank of the page j pointing to page i
             *  n(j) - the number of links from j page
             *  **/
            for (int i = 0; i < matrix.length; i++) {
                double tempSum = 0.0;
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[j][i] > 0) {
                        tempSum += tempArray[j] / linkSum[j];
                    }
                }
                pagerank[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * tempSum;
            }

            for (int k = 0; k < pagerank.length; k++) {
                System.out.print(pagerank[k] + " ");
            }
            System.out.println();
            System.out.println("precision: " + prec + " sum: " + sum + " iter: " + iter);
            iter++;
        } while (difference(pagerank, tempArray) > PRECISION);
        long stopTime = System.currentTimeMillis();
        this.time = stopTime - startTime;
        return pagerank;
    }


    /**
     * 3
     **/
    @Override
    public double[] calculate(SparseMatrix sparseMatrix) {
        double prec = 0;
        int iter = 0;
        double[] linkSum = sparseMatrix.linkSum();
//        this.time = 0;
        long startTime = System.currentTimeMillis();
        /**while  PRECISION > 1.0e-4**/
        do {
            tempArray = new double[MATRIX_SIZE];
            for (int i = 0; i < tempArray.length; i++) {
                tempArray[i] = pagerank[i];
            }
            /** PR(i) = (1-d) + d * (PR(j)/n(j) )
             *  d - damping factor
             *  PR(j) - the PageRank of the page j pointing to page i
             *  n(j) - the number of links from j page
             *  **/
            for (int i = 0; i < sparseMatrix.getNumRows(); i++) {
                double tempSum = 0.0;
                for (int j = 0; j < sparseMatrix.getNumColumns(); j++) {
                    if (sparseMatrix.getValue(j, i) == 1.0) {
                        tempSum += tempArray[j] / linkSum[j];
                    }
                }
                pagerank[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * tempSum;
            }

            for (int k = 0; k < pagerank.length; k++) {
                System.out.print(pagerank[k] + " ");
            }
            System.out.println();
            prec = difference(pagerank, tempArray);
            System.out.println("precision: " + prec + " sum: " + sum + " iter: " + iter);
            iter++;
        } while (difference(pagerank, tempArray) > PRECISION);

        long stopTime = System.currentTimeMillis();
        this.time = stopTime - startTime;

        return pagerank;
    }

    protected Double difference(double[] page, double[] temp) {
        double prec = 0.0;
        this.sum = 0;
        if (temp == null)
            return 1.0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            prec += Math.abs(page[i] - temp[i]);
            this.sum += pagerank[i];
        }
        return prec;

    }

    @Override
    public long getTime() {
        return time;
    }

    public double[] getPagerank() {
        return pagerank;
    }

    public void setPagerank(double[] pagerank) {
        this.pagerank = pagerank;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}

package task.pagerank.rank;

import task.pagerank.model.sparsematrix.SparseMatrix;

import java.util.Arrays;

/**
 * Created by Nail on 19.03.2017.
 */
public class Power {


    private static final double PRECISION = 1.0e-4;
    private final static int MATRIX_SIZE = 100;
    private static final double DAMPING_FACTOR = 0.85;


    private double[] pagerank;


    public Power() {
        double node = (1 - DAMPING_FACTOR) / MATRIX_SIZE;
        this.pagerank = new double[MATRIX_SIZE];
        Arrays.fill(this.pagerank, node);
    }

    public double[] calculate(double[][] matrix) {

        pagerank = new double[MATRIX_SIZE];

        double[] linkSum = new double[MATRIX_SIZE];
        for (int i = 0; i < matrix.length; i++) {
            double temp = 0.0;
            for (int j = 0; j < matrix.length; j++) {
                temp += matrix[i][j];
            }
            linkSum[i] = temp;
        }
        /** P  **/
        double[][] probability = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0.0) {
                    probability[j][i] = 0.0;
                } else {
                    if (linkSum[j] != 0.0)
                        probability[j][i] = 1.0 / linkSum[j];
                    else
                        probability[j][i] = 0;
                }

            }
        }

        /** M **/
        double[][] mMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        double[][] sMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        double sNode = 1.0 / MATRIX_SIZE;
        for (double[] row : sMatrix)
            Arrays.fill(row, sNode);
        for (int i = 0; i < mMatrix.length; i++) {
            for (int j = 0; j < mMatrix.length; j++) {
                mMatrix[i][j] = ((DAMPING_FACTOR * probability[i][j]) + ((1 - DAMPING_FACTOR) * sMatrix[i][j]));
            }
        }
        System.out.println();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(probability[i][j] + " ");
            }
            System.out.println();
        }

        double prec = 0;
        int iter = 0;
        double sum = 0.0;

        /**PR(n) = M * P(n-1) **/
        while ((prec > PRECISION || iter == 0) && iter < 1000) {
            double[] tempArray = new double[MATRIX_SIZE];
            for (int i = 0; i < tempArray.length; i++) {
                tempArray[i] = pagerank[i];
            }

            for (int i = 0; i < matrix.length; i++) {
                double tempSum = 0;
                for (int j = 0; j < matrix.length; j++) {
//                    tempSum += DAMPING_FACTOR * probability[i][j] * tempArray[j];
                    tempSum += mMatrix[i][j] * tempArray[j];
//                    tempSum += DAMPING_FACTOR * tempArray[j] * 1D / (linkSum[j] == 0 ? 1 : linkSum[j]);
                }
                pagerank[i] = tempSum;
            }

            for (int k = 0; k < pagerank.length; k++) {
                System.out.print(pagerank[k] + " ");
            }
            System.out.println();
            prec = 0;
            sum = 0.0;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                prec += Math.abs(pagerank[i] - tempArray[i]);
                sum += pagerank[i];
            }
            System.out.println("precision: " + prec + " sum: " + sum + " iter: " + iter);
            iter++;
        }

        return pagerank;
    }


    public double[] calculate(SparseMatrix sparseMatrix) {

        double[] pagerank = new double[MATRIX_SIZE];
        double node = 1.0 / MATRIX_SIZE;
        Arrays.fill(pagerank, node);


        double[] linkSum = new double[MATRIX_SIZE];
        for (int i = 0; i < sparseMatrix.getNumRows(); i++) {
            double temp = 0.0;
            for (int j = 0; j < sparseMatrix.getNumColumns(); j++) {
                temp += sparseMatrix.getValue(i, j);
            }
            linkSum[i] = temp;
        }
        /** P  **/
        double[][] probability = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < sparseMatrix.getNumRows(); i++) {
            for (int j = 0; j < sparseMatrix.getNumColumns(); j++) {
                if (sparseMatrix.getValue(i, j) == 1.0) {
                    probability[j][i] = 1.0 / linkSum[j];
                } else {
                    probability[j][i] = 0.0;
                }

            }
        }

        /** M **/
        double[][] mMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        double[][] sMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        double sNode = 1.0 / MATRIX_SIZE;
        for (double[] row : sMatrix)
            Arrays.fill(row, sNode);
        for (int i = 0; i < mMatrix.length; i++) {
            for (int j = 0; j < mMatrix.length; j++) {
                mMatrix[i][j] = DAMPING_FACTOR * probability[i][j] + (1 - DAMPING_FACTOR) * sMatrix[i][j];
            }
        }

        double prec = 0;
        int iter = 0;
        double sum = 0.0;

        while ((prec > PRECISION || iter == 0) && iter < 1000) {
            double[] tempArray = new double[MATRIX_SIZE];
            for (int i = 0; i < tempArray.length; i++) {
                tempArray[i] = pagerank[i];
            }

            for (int i = 0; i < sparseMatrix.getNumRows(); i++) {
                double tempSum = 0.0;
                for (int j = 0; j < sparseMatrix.getNumColumns(); j++) {

                    tempSum += mMatrix[i][j] * tempArray[j];
                }
                pagerank[i] = tempSum;
            }

            for (int k = 0; k < pagerank.length; k++) {
                System.out.print(pagerank[k] + " ");
            }
            System.out.println();
            prec = 0;
            sum = 0.0;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                prec += Math.abs(pagerank[i] - tempArray[i]);
                sum += pagerank[i];
            }
            System.out.println("precision: " + prec + " sum: " + sum + " iter: " + iter);
            iter++;
        }

        return pagerank;
    }


    public double[] getPagerank() {
        return pagerank;
    }

    public void setPagerank(double[] pagerank) {
        this.pagerank = pagerank;
    }


}

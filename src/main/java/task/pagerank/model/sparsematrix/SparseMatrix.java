package task.pagerank.model.sparsematrix;

import java.util.HashMap;

/**
 * Created by Nail on 20.03.2017.
 */
public class SparseMatrix {

    private HashMap<Integer, Row> rows; // A Row is a hash that tells me the columns
    private HashMap<Integer, Column> columns; // A Column is a hash that tells me the rows

    private int numRows;
    private int numColumns;

    private double[] linkSum;

    public SparseMatrix(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.rows = new HashMap<Integer, Row>(numRows);
        this.columns = new HashMap<Integer, Column>(numColumns);
        this.linkSum = new double[numRows];
    }



    public void setQuick(int row, int column, double value) {
//    long time = System.nanoTime();

        Row rowObj;
        if ((rowObj = rows.get(row)) != null) {
            rowObj.setQuick(column, value);
        } else {
            rowObj = new Row(numColumns);
            rowObj.setQuick(column, value);
            rows.put(row, rowObj);
        }

        Column colObj;
        if ((colObj = columns.get(column)) != null) {
            colObj.setQuick(row, value);
        } else {
            colObj = new Column(numRows);
            colObj.setQuick(row, value);
            columns.put(column, colObj);
        }

    }


    public Column getColumn(int i) {
        return columns.get(i);
    }

    public Row getRow(int i) {
        return rows.get(i);
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public HashMap<Integer, Row> getRows() {
        return rows;
    }

    public void setRows(HashMap<Integer, Row> rows) {
        this.rows = rows;
    }

    public HashMap<Integer, Column> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<Integer, Column> columns) {
        this.columns = columns;
    }


    public double getValue(int row, int column){
        Row rowObj;
        if( (rowObj = rows.get(row)) != null){
            return rowObj.get(column) != null ? rowObj.get(column) : 0.0;
        }
        else {
            return 0.0;
        }
    }

    public double[] linkSum(){

        for (int i = 0; i < numRows; i++) {
            double temp = 0.0;
            for (int j = 0; j < numColumns; j++) {
                temp += getValue(i, j);
            }
            linkSum[i] = temp;
        }

        return linkSum;
    }


    public void print() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumColumns(); column++) {
                if(rows.get(row) != null){
                    System.out.print(rows.get(row).get(column) + " ");
                }
                else {
                    System.out.print(null + " ");
                }
            }
            System.out.println();
        }
    }


}


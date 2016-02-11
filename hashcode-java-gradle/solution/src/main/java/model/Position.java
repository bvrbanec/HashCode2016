package model;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double distanceTo(final Position that) {
        return Math.sqrt(Math.pow(row - that.row, 2) + Math.pow(column - that.column, 2));
    }

}

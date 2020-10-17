package model;

public class Node {

    private int value;
    private Node right;
    private Node down;
    private int row;
    private int column;

    public Node() {

    }

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node right, Node down, int row, int column) {
        this.value = value;
        this.right = right;
        this.down = down;
        this.row = row;
        this.column = column;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}

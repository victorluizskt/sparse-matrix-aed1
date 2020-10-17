package model;

public class Node {

    private int line;
    private int column;
    private Node right;
    private Node below;
    private int value;

    public Node(int linha, int coluna, int valor, Node right, Node below) {

        this.line = linha;
        this.column = coluna;
        this.value = valor;
        this.right = right;
        this.below = below;

    }

    public Node() {
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getBelow() {
        return below;
    }

    public void setBelow(Node below) {
        this.below = below;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
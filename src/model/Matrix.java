package model;

public class Matrix {
    private Node head;
    private int rows;
    private int columns;

    public Matrix() {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        createSentinel();
    }

    public Matrix(int rows, int columns) {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        this.rows = rows;
        this.columns = columns;
        createSentinel();
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    /* Cria as sentinelas das colunas */
    private void createSentinel() {
        Node aux = new Node();
        aux.setColumn(0);
        head.setRight(aux); // Adiciona a celula no valor a direita da cabeça.
        aux = head.getRight();
        for (int i = 1; i < columns; i++) {
            aux.setRight(new Node()); // Cria uma nova celula a direita da ultima celula criada.
            aux.getRight().setColumn(i);
            aux = aux.getRight(); // Pega a celula a direita da ultima celula criada.
        }
        /* Cria a sentinela das linhas */
        aux = new Node();
        aux.setRow(0);
        head.setDown(aux); // Adiciona a celula no valor abaixo da cabeça.
        aux = head.getDown();
        for (int i = 1; i < rows; i++) {
            aux.setDown(new Node()); // Cria uma nova celula abaixo da ultima celula criada.
            aux.getDown().setRow(i);
            aux = aux.getDown(); // Pega a celula abaixo da ultima celula criada.
        }
    }

}

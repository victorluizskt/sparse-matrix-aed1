package model;

import model.exception.MatrixException;

/*
 *
 * @param <T>
 * @brief Classe Matrix
 * @author Victor Luiz Gonçalves
 * @date   24/10/2019
 * @since  24/10/2019
 *
 */

public class Matrix<T> {

    /* Sentinela principal, posição (-1, -1) */
    private Node head;

    /* Numero de linhas e colunas da matriz. */
    private final int nLine;
    private final int nColumn;

    /* Celulas não nulas. */
    private long nNode;

    private class Node {
        /*
            Classe node, com referência das celulas direita e em baixo
            valor dar cor escolhida, linha e coluna da matriz.
            Construtor com linha e coluna para criar sentinela e uma com valor para cor.
         */
        private Node right, below;
        private T value;
        private int line, column;

        public Node(int line, int column) {
            this.line = line;
            this.column = column;
        }

        public Node(int line, int column, T value) {
            this.line = line;
            this.column = column;
            this.value = value;
        }
    }

    /* Construtor da matriz que inicializa a sentinela primaria (-1, -1) */
    public Matrix(int line, int column) {
        this.nNode = 0;
        this.nLine = line;
        this.nColumn = column;
        this.head = new Node(-1, -1);
        addHeads();
    }

    /* Cria uma sentinela para cada linha e coluna */
    private void addHeads() {
        Node aux = head;
        for (int line = 0; line < nLine; line++) { //percorre toda a coluna -1
            aux.below = new Node(line, -1);
            aux = aux.below;
        }

        aux = head;
        for (int column = 0; column < nColumn; column++) { //percorre toda a linha -1
            aux.right = new Node(-1, column);
            aux = aux.right;
        }
    }

    /*
    Metodo para retornar as sentinelas de acordo com os valores recebidos por paramêtro.
    */
    public Node getHead(int line, int column) {
        Node aux = head, aux1;
        while (aux != null && aux.column != column) { // Vê se chegou na coluna escolhida.
            aux = aux.right;
        }

        aux1 = aux;
        while (aux1 != null && aux1.line != line) { // Vê se chegou na linha escolhida.
            aux1 = aux1.below;
        }
        return aux1;
    }

    /* Retorna o node da posição (line, column) */
    public Node getHeadPosition(int lin, int col) {
        Node aux = head;
        while (aux != null && aux.line != lin) { // Vai até a linha passada por paramêtro.
            aux = aux.below;
        }

        if (aux != null) {
            while (aux != null && aux.column != col) { // Vai até a coluna passada por paramêtro.
                aux = aux.right;
            }
            return aux;
        }
        return null;
    }

    /* Metódo para inserir uma celula com valor valido na lista. */
    public void insert(T value, int line, int column){
        if (value == null) {
            throw new IllegalArgumentException("Valor nulo");
        }
        if (line >= this.nLine || line < 0 || column >= this.nColumn || column < 0) {
            throw new ArrayIndexOutOfBoundsException("Posição inválida.");
        }

        // Cria um novo Node com os valores recebidos por parâmetro e pega as sentinelas e colunas.
        Node noob = new Node(line, column, value), nodeHor = this.getHead(line, -1), nodeVer = this.getHead(-1, column),
                auxHor = null, auxVer = null;

        while (nodeHor != null && nodeHor.column != noob.column) {
            auxHor = nodeHor;
            nodeHor = nodeHor.right;
        }

        while (nodeVer != null && nodeVer.line != noob.line) {
            auxVer = nodeVer;
            nodeVer = nodeVer.below;
        }

        if (nodeHor == null || nodeVer == null) { // Caso esteja na ultima celula de linha ou coluna.
            auxHor.right = noob;
            auxVer.below = noob;
        } else {
            auxHor.right = noob;
            noob.right = nodeHor.right;
            auxVer.below = noob;
            noob.below = nodeVer.below;
        }
        this.nNode++;
    }

    /* Metódo que insere uma borda em toda imagem */
    public void insertEdge(int qntEdge, T valueColor) throws MatrixException {
        int counter = 1;
        if (qntEdge >= (nColumn / 2) || qntEdge >= (nLine / 2)) {
            throw new MatrixException("Quantidade de bordas insuportada.");
        }

        int line = 0, column = 0;
        while (counter <= qntEdge) {
            for (int c = 0; c < nColumn; c++) {
                insert(valueColor, line, c); // Insere nas 3 primeiras linhas.
                insert(valueColor, nLine - (line + 1), c); // Insere nas 3 ultimas linhas.
            }

            for (int l = 0; l < nLine; l++) {
                insert(valueColor, l, column); // Insere nas 3 primeiras colunas.
                insert(valueColor, l, nColumn - (column + 1)); // Insere nas 3 ultimas colunas.
            }
            counter++;
            line++;
            column++;
        }
    }

    /* Metódo que cria e retorna uma nova matriz rotacionada. */
    public Matrix<T> rotateImage() {
        Matrix<T> newMatrix = new Matrix<T>(nColumn, nLine);
        for (int line = 0; line < newMatrix.nLine; line++) {
            int aux = newMatrix.nColumn - 1; // Começa a partir da ultima coluna
            for (int column = 0; column < newMatrix.nColumn; column++) {
                if (getHeadPosition(aux, line) != null && getHeadPosition(aux, line).value != null) {
                    T value = getHeadPosition(aux, line).value; // Troca linha e coluna
                    newMatrix.insert(value, line, column);
                }
                aux--;
            }
        }
        return newMatrix;
    }

    /* Método que inverte as cores da imagem, criando novos Nodes caso a anterior fosse 0. */
    public void invertColor(T maxValue) throws Exception {
        try {
            for (int line = 0; line < nLine; line++) {
                for (int column = 0; column < nColumn; column++) {
                    Node current = getHeadPosition(line, column); // Seleciona a celula da coordenada atual.
                    if (current == null) {
                        insert(maxValue, line, column); // Caso não exista, cria valendo 255.
                    } else if (current.value == maxValue) {
                        deleteHead(line, column); // Se existir e valer 255 exclui a celula.
                    } else {
                        Integer max = (Integer) maxValue;
                        Integer valueCurrent = (Integer) current.value;
                        current.value = (T) (Integer) (max - valueCurrent);
                    }
                }
            }
        } catch(Exception e){
            throw new Exception("Impossível inverter cores.");
        }
    }

    /* Método que exclui o Node na posição(line, column), caso exista. */
    private void deleteHead(int line, int column) throws ArrayIndexOutOfBoundsException, MatrixException, NullPointerException {
        if (line >= nLine || column >= nColumn) {
            throw new ArrayIndexOutOfBoundsException("Erro ao excluir elemento.");
        }
        if (nNode == 0) {
            throw new MatrixException("Matriz vazia.");
        }
        if (getHeadPosition(line, column) == null) {
            throw new NullPointerException("Elemento nulo.");
        }

        Node nodeHor = getHead(line, -1);
        Node antHor = nodeHor;
        nodeHor = nodeHor.right;
        Node nodeVer = getHead(-1, column);
        Node antVer = nodeVer;
        nodeVer = nodeVer.below;
        while (nodeHor != null && nodeHor.column != column) {
            antHor = nodeHor;
            nodeHor = nodeHor.right;
        }

        while (nodeVer != null && nodeVer.line != line) {
            antVer = nodeVer;
            nodeVer = nodeVer.below;
        }

        if (nodeHor == null || nodeVer == null) {
            throw new IllegalArgumentException("Node nao encontrado.");
        }
        antHor.right = nodeHor.right;
        antVer.below = nodeVer.below;
        this.nNode--;
    }

    /* Escrever arquivo em .pgm. */
    public String createStringPgm(T maxvalue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("P2\n");
        stringBuilder.append(this.nColumn).append(" ").append(this.nLine).append("\n");
        stringBuilder.append(maxvalue).append("\n");
        for (int line = 0; line < this.nLine; line++) {
            for (int column = 0; column < this.nColumn; column++) {
                Node aux = getHeadPosition(line, column);
                if (aux == null) {
                    stringBuilder.append("0");
                } else if (aux.value != null) {
                    stringBuilder.append(aux.value);
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /* Metódo toString() que lista os elementos validos da lista. */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int line = 0; line < this.nLine; line++) {
            for (int column = 0; column < this.nColumn; column++) {
                Node aux = getHeadPosition(line, column);
                if (aux == null) {
                    stringBuilder.append(".");
                } else if (aux.value != null) {
                    stringBuilder.append(aux.value);
                }
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
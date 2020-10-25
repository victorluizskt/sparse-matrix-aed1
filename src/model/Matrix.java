package model;

/*
 *
 * @param <T>
 * @brief Classe Matrix
 * @author Victor Luiz Gonçalves
 * @date   24/10/2019
 * @since  24/10/2019
 *
 */

import model.exception.MatrixException;

public class Matrix<T> {

    // Sentinela principal na posição (-1, -1)
    private Node head;

    // Linhas e colunas de toda a matriz
    private final int nLine;
    private final int nColumn;

    // Nodes não vazios
    private long nNode;

    private class Node {

        private Node direita, abaixo; // Node direita e abaixo
        private T value; // Valor da cor
        private int line, column; // Linha e coluna da matriz

        // Construtor com linhas e colunas para criação da sentinela.
        public Node(int line, int column) {
            this.line = line;
            this.column = column;
        }

        //construtor completo para criar nodes com valores de cor
        public Node(int line, int column, T value) {
            this.line = line;
            this.column = column;
            this.value = value;
        }
    }
    
    // Construtor da matriz que inicializa a sentinela primaria (-1, -1)
    public Matrix(int line, int column){
        this.nNode = 0;
        this.nLine = line;
        this.nColumn = column;
        this.head = new Node(-1, -1);
        addHeads();
    }

    private void addHeads() {
        Node aux = head;
        for(int line = 0; line < nLine; line++){
            aux.abaixo = new Node(line, -1);
            aux = aux.direita;
        }

        aux = head;
        for(int col = 0; col < nColumn; col++){
            aux.direita = new Node(-1, col);
            aux = aux.direita;
        }
    }

    // Metodo para retornar as sentinelas de acordo com os valores recebidos por paramêtro.
    public Node getHead(int line, int column){
        Node aux = head, aux1;
        while(aux != null && aux.column != column){ // Vê se chegou na coluna escolhida.
            aux = aux.direita;
        }

        aux1 = aux;
        while(aux1 != null && aux1.line != line){ // Vê se chegou na linha escolhida.
            aux1 = aux1.abaixo;
        }

        return aux1;
    }

    // Retorna o node da posição (line, column)
    public Node getHeadPosition(int line, int column){
        Node aux = head;
        while(aux != null && aux.line != line){ // Vai até a linha passada por paramêtro.
            aux = aux.direita;
        }

        if(aux != null){
            while(aux != null && aux.column != column){ // Vai até a coluna passada por paramêtro.
                aux = aux.direita;
            }
            return aux;
        }
        return null;
    }

    // Metódo para inserir uma celula com valor valido na lista.
    public void insert(T value, int line, int column){
        if(value == null){
            throw new IllegalArgumentException("Valor nulo");
        }
        if(line >= this.nLine || line < 0 || column > this.nColumn || column < 0){
            throw new ArrayIndexOutOfBoundsException("Posição inválida.");
        }

        // Cria um novo Node com os valores recebidos por parâmetro e pega as sentinelas e colunas.
        Node noob = new Node(line, column, value), nodeHor = this.getHead(line, -1), nodeVer = this.getHead(-1, column),
                auxHor = null, auxVer = null;

        while(nodeHor != null && nodeHor.column != noob.column){
            auxHor = nodeHor;
            nodeHor = nodeHor.direita;
        }

        while(nodeVer != null && nodeVer.line != noob.line){
            auxVer = nodeVer;
            nodeVer = nodeVer.abaixo;
        }

        if(nodeHor == null || nodeVer == null){
            auxHor.direita = noob;
            auxVer.abaixo = noob;
        } else {
            auxHor.direita = noob;
            noob.direita = nodeHor.direita;
            auxVer.abaixo = noob;
            noob.abaixo = nodeVer.abaixo;
        }
        this.nNode++;
    }

    // Metódo que insere uma borda em toda imagem
    public void insertEdge(int qntEdge, T valueColor) throws MatrixException {
        int counter = 1;
        if(qntEdge >= (nColumn / 2) || qntEdge >= (nLine / 2)){
            throw new MatrixException("Quantidade de bordas insuportada.");
        }

        int line = 0, column = 0;
        while(counter < qntEdge){
            for(int c = 0; c < nColumn; c++){
                insert(valueColor, line, c);  // Insere nas 3 primeiras linhas.
                insert(valueColor, nLine - (line + 1), c);
            }

            for(int l = 0; l < nLine; l++){
                insert(valueColor, l, column);
                insert(valueColor, l, nColumn - (column + 1));
            }

            counter++;
            line++;
            column++;
        }
    }

    // Metódo que cria e retorna uma nova matriz rotacionada.
    public Matrix<T> rotateImage(){
        Matrix<T> newMatrix = new Matrix<T>(nColumn, nLine);
        for(int l = 0; l < newMatrix.nLine; l++){
            int aux = newMatrix.nColumn - 1;
            for(int c = 0; c < newMatrix.nColumn; c++){
                if(getHeadPosition(aux, l) != null && getHeadPosition(aux, l).value != null){
                    T value = getHeadPosition(aux, l).value;
                    newMatrix.insert(value, l, c);
                }
                aux --;
            }
        }
        return newMatrix;
    }

    // Método que inverte as cores da imagem, criando novos Nodes caso a anterior fosse 0.
    public void invertColor(T maxValue) throws Exception {
        try {
            for (int l = 0; l < nLine; l++) {
                for (int c = 0; c < nColumn; c++) {
                    Node current = getHeadPosition(l, c);
                    if (current == null) {
                        insert(maxValue, l, c);
                    } else if (current.value == maxValue) {
                        deleteHead(l, c);
                    } else {
                        Integer max = (Integer) maxValue;
                        Integer valueCurrent = (Integer) current.value;
                        current.value = (T) (Integer) (max - valueCurrent);
                    }
                }
            }
        } catch (Exception e){
            throw new Exception("Impossível inverter cores.");
        }
    }

    // Método que exclui o Node na posição(line, column), caso exista.
    private void deleteHead(int line, int column) throws MatrixException {
        if(line >= nLine || column >= nColumn){
            throw new ArrayIndexOutOfBoundsException("Erro ao excluir elemento.");
        }
        if(nNode == 0){
            throw new MatrixException("Matriz vazia.");
        }
        if(getHeadPosition(line, column) == null){
            throw new NullPointerException("Elemento nulo.");
        }

        Node nodeH = getHead(line, -1);
        Node antH = nodeH;
        nodeH = nodeH.direita;
        Node nodeV = getHead(-1, column);
        Node antV = nodeV;
        nodeV = nodeV.abaixo;

        while(nodeH != null && nodeH.column != column){
            antH = nodeH;
            nodeH = nodeH.direita;
        }

        while(nodeV != null && nodeV.line != column){
            antV = nodeV;
            nodeV = nodeV.abaixo;
        }

        if(nodeH == null || nodeV == null){
            throw new IllegalArgumentException("Node nao encontrado.");
        }
        antH.direita = nodeH.direita;
        antV.abaixo = nodeV.abaixo;
        this.nNode--;
    }

    // Escrever arquivo em .pgm.
    public String createStringPgm(T maxValue){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("P2\n");
        stringBuilder.append(this.nColumn).append(" ").append(this.nLine).append("\n");
        stringBuilder.append(maxValue).append("\n");

        for(int line = 0; line < this.nLine; line++){
            for(int column = 0; column < this.nColumn; column++){
                Node aux = getHeadPosition(line, column);
                if(aux == null){
                    stringBuilder.append("0");
                } else if(aux.value != null){
                    stringBuilder.append(aux.value);
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // Metódo toString() que lista os elementos validos da lista.
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int line = 0; line < this.nLine; line++){
            for(int column = 0; column < this.nColumn; column++){
                Node aux = getHeadPosition(line, column);
                if(aux == null){
                    stringBuilder.append(".");
                } else if(aux.value != null){
                    stringBuilder.append(aux.value);
                }
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
package model;

public class Matrix {
    private Node head;
    private Node back;
    private Node aux;
    private int nRow;
    private int nColumn;

    /* Inicializa a matriz com apenas a sentinela -1, -1 */
    public Matrix(){
        head = new Node(-1, -1, -1, null, null);
        head = new Node(-1, -1, -1, head, head); /* Celula sentinela principal */
        back = head;
    }

    public void insertNode(int row, int column, int value, int nRow, int nColumn){
        this.nRow = nRow;
        this.nColumn = column;
        if(back == head){

            for(int i = 0; i < nColumn; i++){
                back.setRight(new Node(-1, i, -1, head, null));
                back = back.getRight();
                back.setBelow(back);
            }

            back = head;
            for(int i = 0; i < nRow; i++){
                back.setBelow(new Node(i, -1, -1, null, head));
                back = back.getBelow();
                back.setRight(back);
            }

            back = head;
            while(back != null){
                back = back.getRight();
            }

            assert false;
            if(back.getBelow() == back){
                back.setBelow(new Node(row, column, value, null, back));
                back = back.getBelow();
            }

            aux = head;
            while(aux.getLine() != row){
                aux = aux.getBelow();
            }

            if(aux.getBelow() == aux){
                aux.setRight(back);
                back.setRight(aux);
            } else {

                if(aux.getRight().getColumn() > back.getColumn()){
                    back.setRight(aux.getRight());
                    aux.setRight(back);
                } else {
                    while(aux.getColumn() <= back.getColumn() && aux.getRight().getColumn() != -1){
                        aux = aux.getRight();
                    }

                    back.setRight(aux.getRight());
                    aux.setRight(back);
                }
            }
        } else {

            if(back.getBelow().getLine() > row) {
                back.setBelow(new Node(row, column, value, null, back.getBelow()));
                back = back.getBelow();
                aux = head;
                while (aux.getLine() != row)
                    aux = aux.getBelow();


                if (aux.getRight() == aux) {
                    aux.setRight(back);
                    back.setRight(aux);
                } else {

                    if (aux.getRight().getColumn() > back.getColumn()) {
                        aux = aux.getRight();
                        back.setRight(aux.getRight());
                        aux.setRight(back);
                    } else {
                        while (aux.getColumn() <= back.getColumn() && aux.getRight().getColumn() != -1) {
                            aux = aux.getRight();
                        }
                        back.setRight(aux.getRight());
                        aux.setRight(back);
                    }
                }
            } else {
                aux = back;
                while(aux.getLine() <= row && aux.getBelow().getLine() <= row && aux.getBelow().getLine() != -1)
                    aux = aux.getBelow();

                back = aux;
                back.setBelow(new Node(row, column, value, null, back.getBelow()));
                back = back.getBelow();
                aux = head;

                while(aux.getBelow() != null && aux.getLine() != row)
                    aux = aux.getBelow();

                if(aux.getRight() == aux){
                    aux.setRight(back);
                    back.setRight(back);
                } else {

                    if(aux.getRight().getColumn() > back.getColumn()){
                        back.setRight(aux.getRight());
                        aux.setRight(back);
                    } else {
                        while(aux.getRight().getColumn() <= back.getColumn() && aux.getRight().getColumn() != -1)
                            aux = aux.getRight();
                        back.setRight(aux.getRight());
                        aux.setRight(back);
                    }
                }
            }
        }
    }

    public void reverseImage(){

    }

    public void addEdge(Matrix matrix, int length, int color, boolean change){

    }

    public void rotateImage(){

    }

    /* Cria as sentinelas das colunas */
    private void createSentries() {
        Node aux = new Node(); // cria um nova celula
        aux.setColumn(0);
        head.setRight(aux); // adiciona a celula no valor a direita da cabeça
        aux = head.getRight();
        for (int i = 1; i < nColumn; i++) {
            aux.setRight(new Node()); // cria uma nova celula a direita da ultima celula criadaa
            aux.getRight().setColumn(i);
            aux = aux.getRight(); // pega a celula a direita da ultima celula criada
        }

        // cria as sentinelas das linhas
        aux = new Node();
        aux.setLine(0);
        head.setBelow(aux); // adiciona a celula no valor abaixo da cabeça
        aux = head.getBelow();
        for (int i = 1; i < nRow; i++) {
            aux.setBelow(new Node()); // cria uma nova celula abaixo da ultima celula criada
            aux.getBelow().setLine(1);
            aux = aux.getBelow(); // pega a celula abaixo da ultima celula criada
        }
    }
}

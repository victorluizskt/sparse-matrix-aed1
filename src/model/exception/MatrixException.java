package model.exception;

    /*
  Exception para tratamento de erros mais focados da matriz.
    */

public class MatrixException extends Exception{

    public MatrixException() {
        super();
    }

    public MatrixException(String msg) {
        super(msg);
    }
}

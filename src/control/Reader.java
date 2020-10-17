package control;

import model.Matrix;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    char identifier;
    char identifier1;
    int nLines;
    int nColumn;
    int maxColor;
    char character;
    BufferedReader reader;
    Matrix matrix = new Matrix();

    public void reader(String url) {
        try {
            reader = new BufferedReader(new FileReader(url));
            identifier = (char) reader.read(); // Ler o primeiro char
            identifier1 = (char) reader.read(); // Ler o segundo char correspondente ao identificador
            character = (char) reader.read();

            StringBuilder column = new StringBuilder();
            StringBuilder lines = new StringBuilder();
            StringBuilder color = new StringBuilder();

            optimize();

            while (character != ' ' && character != '\n' && character != '\t') {
                column = new StringBuilder("" + column + character);
                character = (char) reader.read();
            }

            optimize();

            while (character != ' ' && character != '\n' && character != '\t') {
                lines = new StringBuilder("" + lines + character);
                character = (char) reader.read(); //
            }

            optimize();

            while (character != ' ' && character != '\n' && character != '\t') {
                color = new StringBuilder("" + color + character);
                character = (char) reader.read(); //

            }

            this.nColumn = Integer.parseInt(column.toString());
            this.nLines = Integer.parseInt(lines.toString()); // Numero de linhas e colunas
            this.maxColor = Integer.parseInt(color.toString());

            StringBuilder info = new StringBuilder();
            int i = 0;
            int j = 0;

            while (i != nLines && j != nColumn) {
                while (character == '0' || character == ' ' || character == '\n' || character == '\t') {
                    character = (char) reader.read();
                    if (character == '0') {
                        j++;
                        if (j == nColumn) {
                            j = 0;
                            i++;
                        }
                    }
                }

                int number = Character.getNumericValue(character);
                while (character != ' ' && character != '\n' && character != '\t' && number >= 1 && number <= 9) {
                    info = new StringBuilder("" + info + character);
                    character = (char) reader.read();
                }

                if (!info.toString().equals("")) {
                    int value = Integer.parseInt(info.toString());
                    //this.matriz.inserirCelula(i, j, valor, nLinhas, nColunas); // Faz a insercao da da celula na matriz
                    info = new StringBuilder();
                }

                j++;
                if (j == nColumn) { // se chegar no final da linha
                    j = 0;
                    i++; // nova linha
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void optimize() {
        try {
            while (character == '0' || character == ' ' || character == '\n' || character == '\t') { // p/ anular
                character = (char) reader.read(); // ler os caracteres inuteis
            }
        } catch (IOException e){
            System.out.println("Error" + e.getMessage()) ;
        }
    }
}
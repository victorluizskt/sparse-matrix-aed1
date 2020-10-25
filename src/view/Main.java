package view;

/*
 *
 * @param <T>
 * @brief Classe Matrix
 * @author Victor Luiz Gonçalves
 * @date   24/10/2019
 * @since  24/10/2019
 *
 */


 /*
    Classe principal, interface com o user.
  */
import model.Matrix;
import model.exception.MatrixException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
     private static Scanner input = new Scanner(System.in);
     private static Scanner read;
     private static Matrix<Integer> matrixImage;
     private static Integer maxValue;
     private static int choice = 10;
     private static String nameArchive;
    public static void main(String[] args) {
        Initializable();
    }

    public static void Initializable(){
        try {
            do {
                System.out.println("Opções: ");
                System.out.println("Escolha um arquivo na pasta raiz do projeto para leitura.");
                System.out.println("[1] - Finalizar programa.");
                System.out.println("[2] - Ler arquivo.");
                choice = input.nextInt();
                
                switch (choice){
                    case 1:
                        System.out.println("Finalizando aplicação.");
                        break;
                    case 2:
                        readArchive();
                        break;
                    default:
                        System.out.println("Opção invalida.");
                }
            } while(choice != 1);
        } catch(NumberFormatException e){
            throw new NumberFormatException("Valor inválido.");
        }
    }
    
    // Metódo para receber o nome do arquivo.
    private static void readArchive() {
        System.out.println("Informe o nome do arquivo:");
        nameArchive = input.next();
        nameArchive = nameArchive + ".pgm";
        loadArchive(nameArchive);
        menuImage();
    }

    private static void menuImage() {
        boolean quit = true;
        try {
            while (quit) {
                System.out.println("Selecione uma opção:");
                System.out.println("[1] Exibir imagem na saída.");
                System.out.println("[2] Inserir borda de 3px na imagem.");
                System.out.println("[3] Inverter as cores da imagem.");
                System.out.println("[4] Rotacionar a imagem 90°.");
                System.out.println("[5] Salve image.");
                System.out.println("[6] Sair do programa.");
                System.out.print("Option: ");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        printImage();
                        break;
                    case 2:
                        insertEdge(3);
                        break;
                    case 3:
                        invertColor();
                        break;
                    case 4:
                        rotateImage();
                        break;
                    case 5:
                        saveArchive();
                        break;
                    case 6:
                        quit = false;
                        break;
                    default:
                        System.out.println("Opção invalida.");
                }
            }
        } catch(InputMismatchException e){
            System.out.println("Valor invalido, tente novamente.");
        }
    }

    private static void printImage() {
        System.out.println("Imagem: \n\n " + matrixImage);
    }

    private static void loadArchive(String nomeArquivo) {
        try {
            File arq = new File(nomeArquivo);
            read = new Scanner(arq);

            if(arq.exists()) {
                String type = read.next();
                if (!type.equals("P2"))
                    throw new IOException("Arquivo com formato não suportado.");

                int nColumn = read.nextInt();
                int nLine = read.nextInt();
                maxValue = read.nextInt();
                matrixImage = new Matrix<Integer>(nLine, nColumn);

                for (int line = 0; line < nLine; line++) {
                    for (int column = 0; column < nColumn; column++) {
                        int bit = read.nextShort();
                        if (bit != 0)
                            matrixImage.insert(bit, line, column);
                    }
                }
            }
            read.close();
        } catch (FileNotFoundException e){
            System.err.println("Arquivo não encontrado.");
            Initializable();
        } catch (IOException ex){
            System.err.println("Erro ao ler arquivo: " + ex.getMessage());
            Initializable();
        } catch (Exception e){
            System.err.println("Erro ao ler arquivo. " + e.getMessage());
            Initializable();
        }
    }

    private static void insertEdge(int qntPixel){
        try{
            matrixImage.insertEdge(qntPixel, 255);
            System.out.println("Borda inserida com sucesso.");
        } catch (MatrixException e){
            System.out.println("Erro ao inserir borda: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erro ao inserir borda.");
        }
    }

    private static void invertColor() {
        try {
            matrixImage.invertColor(maxValue);
            System.out.println("Cores invertidas com sucesso.");
        } catch(ArrayIndexOutOfBoundsException | NullPointerException | MatrixException e){
            System.out.println("Erro ao inverter as cores da imagem: " + e.getMessage());
        } catch(Exception e){
            System.out.println("Erro inesperado ao inverter imagem.");
        }
    }

    private static void rotateImage(){
        try {
            matrixImage = matrixImage.rotateImage();
            System.out.println("Imagem rotacionada com sucesso.");
        } catch(Exception e){
            System.out.println("Erro ao rotacionar imagem.");
        }
    }

    private static void saveArchive(){
        BufferedWriter arq;
        String nameAch = generateName();
        try{
            arq = new BufferedWriter(new FileWriter(nameAch));
            String matrixImg = matrixImage.createStringPgm(maxValue);
            arq.write(matrixImg);
            arq.close();
            java.awt.Desktop.getDesktop().open(new File(nameAch));
            System.out.println("Arquivo criado na raiz do projeto.");
        } catch(IOException e){
            System.out.println("Erro ao abrir ou criar arquivo.");
        }
    }

    private static String generateName() {
        Date date = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        return "imagem-editada" + "_" + today.get(Calendar.DAY_OF_MONTH) + "-" +
                today.get(Calendar.MONTH) + "-" + today.get(Calendar.YEAR) + "_" + today.get(Calendar.HOUR) +
                "h" + today.get(Calendar.MINUTE) + "m" + today.get(Calendar.SECOND) +
                "s" + ".pgm";
    }
}

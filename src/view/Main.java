package view;

/*
 *
 * @brief Classe Main
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
    private static final Scanner input = new Scanner(System.in);
    private static Matrix<Integer> matrixImage;
    private static Integer maxValue;
    private static int choice = 10;

    public static void main(String[] args) {
        Initializable();
    }

    public static void Initializable(){
        try {
            while(choice != 2) {
                System.out.println("Escolha um arquivo na pasta raiz do projeto para leitura.");
                System.out.println("\t\t\t[1]  Ler arquivo.");
                System.out.println("\t\t\t[2]  Finalizar programa.");
                choice = input.nextInt();
                switch (choice){
                    case 1:
                        readArchive();
                        break;
                    case 2:
                        System.out.println("Finalizando aplicação.");
                        break;
                    default:
                        System.out.println("Opção invalida.");
                }
            }
        } catch(NumberFormatException e){
            throw new NumberFormatException("Valor inválido.");
        }
    }

    // Metódo para receber o nome do arquivo.
    private static void readArchive() {
        System.out.println("\nInforme o nome do arquivo:");
        String nameArchive = input.next();
        nameArchive = nameArchive + ".pgm";
        loadArchive(nameArchive);
        menuImage();
    }

    /* Metódo com todas as operações para o usuário. */
    private static void menuImage() {
        boolean quit = true;
        try {
            while (quit) {
                System.out.println("\nSelecione uma opção:");
                System.out.println("\t\t\t[1] Exibir imagem na saída.");
                System.out.println("\t\t\t[2] Inserir borda na imagem.");
                System.out.println("\t\t\t[3] Inverter as cores da imagem.");
                System.out.println("\t\t\t[4] Rotacionar a imagem 90°.");
                System.out.println("\t\t\t[5] Salve image.");
                System.out.println("\t\t\t[6] Sair do programa.");
                System.out.print("Option: ");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        printImage();
                        break;
                    case 2:
                        System.out.println("Quantidade de pixel desejado: ");
                        try {
                            int pixel = input.nextInt();
                            insertEdge(pixel);
                        } catch(InputMismatchException e){
                            System.out.println("Valor invalido, tente novamente.");
                        }
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

    /* Metódo para impressão da imagem no console */
    private static void printImage() {
        System.out.println("Imagem: \n\n " + matrixImage);
    }

    /* Carregamento do arquivo */
    private static void loadArchive(String nomeArquivo) {
        try {
            File arq = new File(nomeArquivo);
            Scanner read = new Scanner(arq);

            if(arq.exists()) {
                String type = read.next();
                if (!type.equals("P2"))
                    throw new IOException("Arquivo com formato não suportado.");

                int nColumn = read.nextInt();
                int nLine = read.nextInt();
                maxValue = read.nextInt();
                matrixImage = new Matrix<>(nLine, nColumn);

                // Salvando o arquivo na matriz.
                for (int line = 0; line < nLine; line++) {
                    for (int column = 0; column < nColumn; column++) {
                        int bit = read.nextShort();
                        if (bit != 0)
                            matrixImage.insert(bit, line, column);
                    }
                }
            }
            read.close();
            System.out.println("Arquivo lido com sucesso.\n");
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

    /* Função que executa a inserção de borda na imagem. */
    private static void insertEdge(int qntPixel){
        try{
            matrixImage.insertEdge(qntPixel, 255);
            System.out.println("Borda inserida com sucesso.\n");
        } catch (MatrixException e){
            System.out.println("Erro ao inserir borda: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erro ao inserir borda.");
        }
    }

    /* Função que executa a inversão de cores na imagem */
    private static void invertColor() {
        try {
            matrixImage.invertColor(maxValue);
            System.out.println("Cores invertidas com sucesso.\n");
        } catch(ArrayIndexOutOfBoundsException | NullPointerException | MatrixException e){
            System.out.println("Erro ao inverter as cores da imagem: " + e.getMessage());
        } catch(Exception e){
            System.out.println("Erro inesperado ao inverter imagem.");
        }
    }

    /* Função que executa a rotação de 90° na imagem */
    private static void rotateImage(){
        try {
            matrixImage = matrixImage.rotateImage();
            System.out.println("Imagem rotacionada com sucesso.\n");
        } catch(Exception e){
            System.out.println("Erro ao rotacionar imagem.");
        }
    }

    /* Função que executa o salvamento da imagem */
    private static void saveArchive(){
        BufferedWriter arq;
        String nameAch = generateName();
        try{
            arq = new BufferedWriter(new FileWriter(nameAch));
            String matrixImg = matrixImage.createStringPgm(maxValue);
            arq.write(matrixImg);
            arq.close();
            java.awt.Desktop.getDesktop().open(new File(nameAch));
            System.out.println("Arquivo criado na raiz do projeto.\n");
        } catch(IOException e){
            System.out.println("Erro ao abrir ou criar arquivo.");
        }
    }

    /* Função para gerar data para a imagem */
    private static String generateName() {
        Date date = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        return "nova-imagem" + "_" + today.get(Calendar.DAY_OF_MONTH) + "-" +
                today.get(Calendar.MONTH) + "-" + today.get(Calendar.YEAR) + "_" + today.get(Calendar.HOUR) +
                "h" + today.get(Calendar.MINUTE) + "m" + today.get(Calendar.SECOND) +
                "s" + ".pgm";
    }
}

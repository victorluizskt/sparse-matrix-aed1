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
        O programa lê apenas imagens que estão na riz do projeto Trabalho1 é um exemplo,
    decidi efetuar essa ideia para não haver dificuldades ao tentar efetuar a leitura do mesmo,
    e não precisa digitar a extensão pgm, apenas o nome do arquivo.

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

/* Criação de variaveis estaticas para utilização ao decorrer de todo código */
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static Matrix<Integer> matrixImage;
    private static Integer maxValue;
    private static int choice = 10;

    /* Chama o metodo de inicialização para começar a iteração com o usuário. */
    public static void main(String[] args) {
        Initializable();
    }

    public static void Initializable(){
        try {
            while(choice != 2) {
                System.out.println("\tEscolha um arquivo na pasta raiz do projeto para leitura.");
                System.out.println("\t\t\t[1]  Ler arquivo.");
                System.out.println("\t\t\t[2]  Finalizar programa.");
                System.out.print("\t\t =>\t");
                choice = input.nextInt();
                switch (choice){
                    case 1:
                        readArchive();
                        break;
                    case 2:
                        System.out.println("\t\tFinalizando aplicação.");
                        break;
                    default:
                        System.out.println("Opção invalida.");
                }
            }
        } catch(InputMismatchException e){
            throw new InputMismatchException("Valor inválido.");
        }
    }

    // Metódo para receber o nome do arquivo.
    private static void readArchive() {
        System.out.println("\n\tInforme o nome do arquivo:");
        System.out.print("\t\t =>\t");
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
                System.out.println("\n\tSelecione uma opção:");
                System.out.println("\t\t\t[1] Exibir imagem na saída.");
                System.out.println("\t\t\t[2] Inserir borda na imagem.");
                System.out.println("\t\t\t[3] Inverter as cores da imagem.");
                System.out.println("\t\t\t[4] Rotacionar a imagem 90°.");
                System.out.println("\t\t\t[5] Salvar imagem.");
                System.out.println("\t\t\t[6] Sair do programa.");
                System.out.print("\tOption => ");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        printImage();
                        break;
                    case 2:
                        System.out.println("\tQuantidade de pixel desejado: ");
                        try {
                            System.out.print("\t\t =>\t");
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
            System.out.println("\tArquivo lido com sucesso.");
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
            matrixImage.insertEdge(qntPixel, 2515);
            System.out.println("\tBorda inserida com sucesso.");
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
            System.out.println("\tCores invertidas com sucesso.");
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
            System.out.println("\tImagem rotacionada com sucesso.");
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
            System.out.println("\tArquivo criado na raiz do projeto.");
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

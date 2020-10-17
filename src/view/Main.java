package view;

import control.Generate;
import control.Reader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Initializable();
    }

    public static void Initializable() {
        Scanner input = new Scanner(System.in);
        boolean quit = true;
        System.out.println("Please insert url to image, for example: /home/user/Documentos/Falculdade/ ....");
        String url = input.nextLine();
        while(url == null){
            System.out.println("Andress is null, please try again.");
            url = input.nextLine();
        }
        uploadImage(url);
        try {
            while (quit) {
                System.out.println("Select one option:");
                System.out.println("[1] Insert edge");
                System.out.println("[2] Reverse colors.");
                System.out.println("[3] Turned 90° degrees");
                System.out.println("[4] Salve image.");
                System.out.println("[0] Quit program.");
                System.out.print("Option: ");
                int option = input.nextInt();
                switch (option) {
                    case 1:

                    case 2:

                    case 3:

                    case 4:
                        Generate generate = new Generate();
                        //generate.builderFile();
                        returnPrincipal();
                        break;
                    case 0:
                        quit = false;
                        break;
                    default:
                        System.out.println("Option invalid.");
                }
            }
        } catch(InputMismatchException e){
            System.out.println("Value is invalid, try again.");
        }
    }

    private static void uploadImage(String url)  {
        if(url != null){
            Reader reader = new Reader();
            reader.reader(url);
            System.out.println("Image loaded successfully.");
        } else {
            System.out.println("Crash!!!!!");
        }
    }

    private static void returnPrincipal(){
        System.out.println("Do you want to upload another photo? [Y/n]");
        Scanner input = new Scanner(System.in);
        String confirm = input.next();
        confirm.toLowerCase();
        if(confirm.equals("y")){
            Initializable();
        }
    }
}

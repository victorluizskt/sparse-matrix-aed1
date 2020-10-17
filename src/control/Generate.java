package control;

import java.io.*;

public class Generate {

    public void builderFile(String img, String storageLocation){
        try {
            String content = img;
            File file = new File(storageLocation);

            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(content);
            bw.close();

            FileReader reader = new FileReader(storageLocation);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
            System.out.println();
            System.out.println("A imagem editada foi salva em ["+storageLocation+"] com sucesso!");
        } catch (IOException e){
            throw new NullPointerException("Impossível abrir imagem.");
        }
    }
}

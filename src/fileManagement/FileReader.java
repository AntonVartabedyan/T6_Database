package fileManagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    File file;
    List<List<String>> info;
    public FileReader(){
    }
    public void readFile(String fileName) throws IOException {
        try{


            this.file = new File(fileName);
            Scanner myReader = new Scanner(file);
            String headerLine = myReader.nextLine();
            String[] headerLines = headerLine.split(" ");
            info = new ArrayList<>(headerLines.length);

            for(String word : headerLines){
                //info.getFirst().add(word);
            }
            int j = 0;
            while(myReader.hasNextLine()){
                info.add(new ArrayList<>());
                String data = myReader.nextLine();
                String[] dataPieces = data.split("(; )|;");
                for (int i = 0; i < dataPieces.length; i++){
                    if (dataPieces[i].isEmpty() || dataPieces[i].equals(" ")){
                        dataPieces[i] = null;
                        System.out.println("NULL");
                        info.get(j).add(null);
                        continue;
                    }
                    System.out.println(dataPieces[i]);
                    info.get(j).add(dataPieces[i]);
                }
                j++;
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            if(file.createNewFile()){
                System.out.println("File created: " + file.getName());
            }
        } catch (Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public String closeFile(){
        String endMessage;
        endMessage = "Successfully closed ";
        endMessage += file.getName();
        try{
            file = null;
        }catch (Exception e){
            endMessage = "Something went wrong";
        }
        return endMessage;
    }
}

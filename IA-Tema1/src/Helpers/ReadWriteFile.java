package Helpers;

import java.io.*;

public class ReadWriteFile {
    public String readFromFile(String filePath , int StartIndex) {
        String mesaj = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int i = 1;
            while(i < StartIndex){
                br.readLine();
                i++;
            }
            String st;
            while ((st = br.readLine()) != null) {
                mesaj += st;
                mesaj += " ";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesaj;
    }

    public void writeToFile(String mesaj , String FileName) throws IOException {
            BufferedWriter write = new BufferedWriter(new FileWriter(FileName,false));
            write.append('\n');
            write.append(mesaj);


            write.close();
        }


}

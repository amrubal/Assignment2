import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Assign2 {
    public String comma = ",";
    public void read(String file1, List<String> word, List<Long> freq) { //for all words
        try {
            File file = new File(file1);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] arr;
            br.readLine();

            while((line = br.readLine()) != null) {
                arr = line.split(comma);
                String str = arr[0];
                String count = arr[1];

                word.add(str);
                freq.add(Long.parseLong(count));
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String file2, List<String> word) { //for misspelled words
        try {
            File file = new File(file2);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] arr;
            br.readLine();
            while((line = br.readLine()) != null) {
                arr = line.split(comma);
                String str = arr[1];
                word.add(str);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


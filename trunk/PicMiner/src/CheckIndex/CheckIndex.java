package CheckIndex;

// NOTE: images folder must already exist
// [file_path, file_date, img_output_folder_path}

import org.apache.commons.io.FileUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckIndex {
    private static ArrayList<String[]> index = new ArrayList<>();
    private static String csv = FileSystemView.getFileSystemView()
            .getDefaultDirectory().getPath()
            + "/PicMiner/images/img_indexing.csv";

    public CheckIndex() {
        createIndexCSV();
        parseIndex();
    }

    public void createIndexCSV() {
        File indexCSV = new File(csv);
        if (!indexCSV.isFile()) {
            try {
                if (indexCSV.createNewFile()) {
                    System.out.println("New img_indexing.csv file created");
                } else {
                    System.out.println("img_indexing.csv file creation failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void parseIndex() {
        //parsing a CSV file into Scanner class constructor
        try {
            Scanner sc = new Scanner(new File(csv));
            sc.useDelimiter(", ");   //sets the delimiter pattern

            while (sc.hasNextLine()) {  //returns a boolean value
                String[] currentLine = sc.nextLine().split(", ");
                index.add(currentLine);
            }
            sc.close();  //closes the scanner
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fileIndexCheck(String file) {
        boolean foundInIndex = false;
        for (String [] currentEntry : index) {
            if (currentEntry[0].equals(file)) {
                foundInIndex = true;
                break;
            }
        }
        if (!foundInIndex) {
            appendToIndex(file);
        } else if (!compareMetadata(file)) {
            updateIndex(file);
        }
    }

    public boolean compareMetadata(String file) {
        // read in file data from the csv that has been found and store in csvFileDate
        int csvFileDate = 0;
        for (String [] currentEntry : index) {
            if (currentEntry[0].equals(file)) {
                csvFileDate = Integer.parseInt(currentEntry[1]);
                break;
            }
        }

        if (new File(file).lastModified() == csvFileDate) {
            return true;
        }
        return false;
    }

    // IMAGE FINDER NEEDS TO OUTPUT IMGS
    public void appendToIndex(String file) {
        File ourFile = new File(file);
        String fileName = ourFile.getName();
        File img_folder_dir = new File(FileSystemView.getFileSystemView()
                .getDefaultDirectory().getPath()
                + "/PicMiner/images/" + fileName); // 2 folders same name issue
        img_folder_dir.mkdirs();

        String[] entry = {file, String.valueOf(ourFile.lastModified()), img_folder_dir.toString()};
        index.add(entry);
    }

    // IMAGE FINDER NEEDS TO OUTPUT IMGS
    public void updateIndex(String file) {
        for (String [] currentEntry : index) {
            if (currentEntry[0].equals(file)) {

                File dir = new File(currentEntry[2]);
                try {
                    FileUtils.deleteDirectory(dir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dir.mkdirs();

                currentEntry[1] = String.valueOf(new File(file).lastModified());
                break;
            }
        }
    }

    public void outputToCSV() throws IOException {
        File file = new File(csv);
        FileWriter writer = new FileWriter(file);
        BufferedWriter outFile = new BufferedWriter(writer);
        StringBuilder sb = new StringBuilder();
        for (String[] entry : index) {
            for (String str : entry) {
                sb.append(str);
                if(!str.equals(entry[2])) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        outFile.write(String.valueOf(sb));
        outFile.close();
    }

    public String getCsv() {
        return csv;
    }

    public ArrayList<String[]> getIndex() {
        return index;
    }

    public void resetCsv() {
        csv = "";
    }

    public void resetIndex() {
        index = null;
    }
}
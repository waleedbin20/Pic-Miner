package FileLocator;


import java.io.File;
import java.io.FileNotFoundException;

public class FileSearchtestTest {
    //FileSearch search;

    @org.junit.Test
    public void initialize() {
        System.out.print("Initializing");
      //  search = new FileSearch();
//        search.start();

    }

    @org.junit.Test
    public void test1() throws FileNotFoundException {
        System.out.print("Test1: Start Searching\n");

        File file = new File("M:\\year2");
//        FileSearch.FindFiles(file);



        System.out.print("Test1: Finished Search\n");
    }


}
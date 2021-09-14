import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilePaths {
    private List<String> filePaths;
    private Scanner scanner;

    public FilePaths() {
        this.filePaths = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Initialize path of the file you want to copy for example:  C:\Users\tuppu\Documents\test.txt
    // and path of the file where to copy for example:  C:\Users\tuppu\Documents\text\copyTest.txt
    // if file doesn't exist it is created.
    public void initFilePaths() {

        System.out.println("Give path of the file you want to copy: ");
        String pathToFileToBeCopied = scanner.nextLine();
        filePaths.add(pathToFileToBeCopied);

        System.out.println("Give path of the file you want to copy to, if it doesn't exist file is created");
        String pathToFileToCopyTo = scanner.nextLine();
        scanner.close();

        filePaths.add(createFile(pathToFileToCopyTo));

        System.out.println(filePaths.get(0));
        System.out.println(filePaths.get(1));

    }

    public List<String> getFilePaths() {
        return this.filePaths;
    }

    public String createFile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return path;

    }


}

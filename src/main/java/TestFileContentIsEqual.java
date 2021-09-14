import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.nio.file.Files.readAllBytes;

public class TestFileContentIsEqual {
    private Reader reader;
    private Writer writer;
    private String pathToFileToBeCopied;
    private String pathToFileToCopyTo;

    public TestFileContentIsEqual(Reader reader, Writer writer, String pathToFileToBeCopied, String pathToFileToCopyTo) throws InterruptedException {
        this.reader = reader;
        this.writer = writer;
        this.pathToFileToBeCopied = pathToFileToBeCopied;
        this.pathToFileToCopyTo = pathToFileToCopyTo;
    }

    public void checkFileContents() throws InterruptedException, IOException {
        while (!(reader.isClosed() && writer.isClosed())) {                             // Wait that both threads are finished
            Thread.sleep(300);
        }

        boolean filesEqual = 0 == Arrays.compare(readAllBytes(Paths.get(pathToFileToBeCopied)), readAllBytes(Paths.get(pathToFileToCopyTo)));
        if (filesEqual) {
            System.out.println("Files are equal after copying!");
        } else {
            System.out.println("File copying failed!");
        }
    }
}

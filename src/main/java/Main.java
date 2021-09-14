// Java program to implement solution of producer
// consumer pro

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        FilePaths filePaths = new FilePaths();
        filePaths.initFilePaths();

        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(100); //Define capacity of buffer
        Reader reader = new Reader(buffer, filePaths.getFilePaths().get(0));
        Writer writer = new Writer(buffer, filePaths.getFilePaths().get(1));

        // Start both threads
        reader.start();
        writer.start();

        // Check that copying has been successful
        TestFileContentIsEqual testFileContentIsEqual = new TestFileContentIsEqual(reader, writer, filePaths.getFilePaths().get(0),
                filePaths.getFilePaths().get(1));
        testFileContentIsEqual.checkFileContents();

    }

}
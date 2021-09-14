import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Reader {
    private final BlockingQueue<Integer> buffer;
    private final FileReader fileReader;
    private final java.io.Reader bufferedReader;
    private volatile boolean closed;


    public Reader(BlockingQueue<Integer> buffer, String pathToFileToBeCopied) throws IOException {
        File file = new File(pathToFileToBeCopied);
        this.fileReader = new FileReader(file);
        this.bufferedReader = new BufferedReader(fileReader);
        this.buffer = buffer;
    }

    // Function called by reader thread
    public void read() throws InterruptedException, IOException {

        while (true) {
            try {
                int b = bufferedReader.read();
                buffer.put(b);
                if (b != -1) {
                    System.out.println("Read from file: " + (char) b);          //Display the Character
                } else {
                    System.out.println("Reader got end of file");
                    break;                                                      // Received EOF exit loop
                }
            } catch (IOException e) {
                e.printStackTrace();
                buffer.put(-1);                                              // Break writer on error
                break;
            }
        }
    }

    // Check if thread is running
    public boolean isClosed() {
        return this.closed;
    }

    public void start() {
        // Create reader thread
        Thread t = new Thread(() -> {
            try {
                this.read();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileReader.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.closed = true;
            }
        });
        t.setDaemon(false);
        t.setName("Reader");
        t.start();
    }
}

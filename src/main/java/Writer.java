import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class Writer {
    private final BlockingQueue<Integer> buffer;
    private PrintWriter out;                                  // Write into the file
    private volatile boolean closed;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public Writer(BlockingQueue<Integer> buffer, String pathToFileToCopyTo) throws IOException {
        this.fileWriter = new FileWriter(pathToFileToCopyTo);
        this.bufferedWriter = new BufferedWriter(fileWriter);
        this.out = new PrintWriter(bufferedWriter);
        this.buffer = buffer;
    }

    // Function called by writer thread
    public void write() throws InterruptedException, IOException {

        // Writer thread waits while buffer is empty
        while (true) {
            Integer val = buffer.take();
            if (val == -1) {
                System.out.println("Writer received end of file");
                break;
            }
            System.out.println("Writing to file: " + (char) val.intValue());
            out.print((char) val.intValue());
            out.flush();
        }

    }

    // Check if thread is running
    public boolean isClosed() {
        return this.closed;
    }

    public void start() {
        // Create writer thread
        Thread t = new Thread(() -> {
            try {
                this.write();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.close();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
                this.closed = true;
            }
        });
        t.setDaemon(false);
        t.setName("Writer");
        t.start();
    }
}


import java.io.IOException;

public class Main {

    public static void main(String [] argc) throws IOException {
        FileGenerator fileGenerator = new FileGenerator("/Users/artamonov/Desktop/Problem #1/test", ".txt");

        fileGenerator.makeFile("/Users/artamonov/Desktop/Problem #1/output.txt");
        fileGenerator.setTextFormat(".log");
        fileGenerator.makeFile("/Users/artamonov/Desktop/Problem #1/output.log");
    }
}

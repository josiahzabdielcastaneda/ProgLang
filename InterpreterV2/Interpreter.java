import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {
    public static void main(String[] args) {
        // Read input from a file
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("$"); // Using $ as next line or carriage return
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = sb.toString();

        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer);

        // Start parsing and interpreting
        parser.parse();
    }
}

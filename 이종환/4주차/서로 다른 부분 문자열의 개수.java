import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();

        Set<String> partialWords = new HashSet<String>();

        for (int i = 1; i <= input.length(); i++) {
            for (int j = 0; j + i <= input.length(); j++) {
                String word = input.substring(j, j+i);
                partialWords.add(word);
            }
        }

        System.out.println(partialWords.size());
    }
}

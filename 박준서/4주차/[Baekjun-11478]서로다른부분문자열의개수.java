import java.io.*;
import java.util.*;

class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static String[] in;

    public static void main(String args[]) throws Exception {
        in = br.readLine().split(" ");
        String tmp = in[0];

        // 부분 문자열을 저장할 HashSet 선언 (중복 제거)
        Set<String> s = new HashSet<>();

        // 모든 가능한 부분 문자열을 생성
        for (int i = 0; i < tmp.length(); i++)
            for (int j = i + 1; j <= tmp.length(); j++)
                s.add(tmp.substring(i, j)); // i부터 j-1까지의 부분 문자열을 Set에 추가

        // 부분 문자열의 개수
        sb.append(s.size()).append("\n");

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}

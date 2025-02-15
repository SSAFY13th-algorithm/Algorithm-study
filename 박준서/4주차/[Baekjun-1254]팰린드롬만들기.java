import java.io.*;

class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static String[] in;
    private static int ans;

    public static void main(String args[]) throws Exception {
        in = br.readLine().split(" ");
        String tmp = in[0];
        // 초기 정답값을 문자열 길이의 2배로 설정
        ans = tmp.length() * 2;

        // 입력 문자열의 역순 문자열 생성
        sb.append(tmp);
        String rev = sb.reverse().toString();
        sb.setLength(0);

        // 원본 문자열 뒤에 역순 문자열의 일부를 붙여가며 최소 팰린드롬 길이 찾기
        for (int i = 0; i <= rev.length(); i++) {
            ans = Math.min(ans, check(tmp + rev.substring(rev.length() - i)));
        }

        sb.append(ans).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // 문자열이 팰린드롬인지 확인하는 함수
    public static int check(String str) {
        // 문자열의 앞뒤를 비교하여 팰린드롬 여부 확인
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1))
                return Integer.MAX_VALUE; // 팰린드롬이 아닐 경우 최대값 반환
        }

        return str.length(); // 팰린드롬일 경우 문자열 길이 반환
    }
}

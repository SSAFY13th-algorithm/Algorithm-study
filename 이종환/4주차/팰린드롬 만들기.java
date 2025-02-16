import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static String[] input;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        input = br.readLine().split("");

        int ans = input.length;

        while(true) {
            if ((ans%2 == 0 && isPel(ans/2 - 1,ans/2) )
                    || (ans%2 == 1 && isPel(ans/2 - 1, ans/2 + 1))){
                // 2로 나누어지면 그 나누어진 값과 -1 인덱스 투입
                // 아닌 경우 -1 +1 한 값들 투입
                // 내부로 들어온 경우 팰린드롬임
                System.out.println(ans);
                return;
            } else {
                ans++;
            }
        }
    }


    public static boolean isPel(int lIdx, int rIdx) {

        while (lIdx >= 0 && rIdx < input.length) {
            // if문이 아니라 while문 조건이 false가 되어
            // 반복문이 깨지면 이는 팰린드롬이라는 뜻
            if (input[lIdx--].equals(input[rIdx++])) {
                // 비교하면서 동시에 증감도 진행
                // 일치한다면 다음 비교
                continue;
            } else {
                // 일치하지 않는다면 false 리턴
                return false;
            }
        }

        return true;
    }
}

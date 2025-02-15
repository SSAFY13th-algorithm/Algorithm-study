import java.io.*;
import java.util.*;

class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static final int MAXNUM = 501; // 최대 정점 수
    private static final int MAXVAL = 1000000000; // 무한대 값

    private static String[] in;
    private static int TC, N, M, W; // 테스트케이스, 정점수, 도로수, 웜홀수
    private static boolean ans; // 음의 사이클 존재 여부

    // 최단거리 배열과 간선 리스트
    private static int[] dist = new int[MAXNUM];
    private static ArrayList<Block> vertices;

    // 간선 정보를 저장하는 클래스
    static class Block {
        int s, e, t; // 시작점, 도착점, 소요시간

        public Block(int s, int e, int t) {
            this.s = s;
            this.e = e;
            this.t = t;
        }
    }

    public static void main(String args[]) throws Exception {
        in = br.readLine().split(" ");
        TC = Integer.parseInt(in[0]);

        while (TC-- != 0) {
            in = br.readLine().split(" ");
            vertices = new ArrayList<>();
            N = Integer.parseInt(in[0]);
            M = Integer.parseInt(in[1]);
            W = Integer.parseInt(in[2]);

            // 도로 정보 입력 (양방향)
            for (int i = 0; i < M; i++) {
                in = br.readLine().split(" ");
                int S = Integer.parseInt(in[0]);
                int E = Integer.parseInt(in[1]);
                int T = Integer.parseInt(in[2]);
                vertices.add(new Block(S, E, T));
                vertices.add(new Block(E, S, T));
            }

            // 웜홀 정보 입력 (단방향, 음의 가중치)
            for (int i = 0; i < W; i++) {
                in = br.readLine().split(" ");
                int S = Integer.parseInt(in[0]);
                int E = Integer.parseInt(in[1]);
                int T = Integer.parseInt(in[2]);
                vertices.add(new Block(S, E, -T));
            }

            // 벨만-포드 알고리즘으로 음의 사이클 확인
            ans = bellmanFord();

            sb.append(ans ? "YES" : "NO").append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // 벨만-포드 알고리즘
    public static boolean bellmanFord() {
        Arrays.fill(dist, MAXVAL);
        dist[1] = 0; // 시작점 거리를 0으로 초기화

        // (N-1)번 반복하여 최단거리 갱신
        for (int i = 1; i < N; i++) {
            for (Block tmp : vertices) {
                if (dist[tmp.e] > dist[tmp.s] + tmp.t)
                    dist[tmp.e] = dist[tmp.s] + tmp.t;
            }
        }

        // 음의 사이클 존재 여부 확인
        for (Block tmp : vertices) {
            if (dist[tmp.e] > dist[tmp.s] + tmp.t)
                return true; // 음의 사이클 존재
        }

        return false; // 음의 사이클 없음
    }
}

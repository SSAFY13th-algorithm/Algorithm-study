import java.io.*;
import java.util.*;

class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static final int MAXNUM = 50001;

    private static ArrayList<ArrayList<Block>> matrix = new ArrayList<>();
    private static int[] dist = new int[MAXNUM];

    // 간선 정보를 저장하는 클래스
    static class Block implements Comparable<Block> {
        int next, d; // 도착 정점, 거리

        public Block(int next, int d) {
            this.next = next;
            this.d = d;
        }

        @Override
        public int compareTo(Block o) {
            return this.d - o.d; // 거리 기준 오름차순 정렬
        }
    }

    private static String[] in;
    private static int N, M; // 정점 수, 간선 수

    public static void main(String args[]) throws Exception {
        in = br.readLine().split(" ");
        N = Integer.parseInt(in[0]);
        M = Integer.parseInt(in[1]);

        for (int i = 0; i <= N; i++)
            matrix.add(new ArrayList<Block>());

        // 간선 정보 입력 (양방향)
        for (int i = 0; i < M; i++) {
            in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            int d = Integer.parseInt(in[2]);
            matrix.get(a).add(new Block(b, d));
            matrix.get(b).add(new Block(a, d));
        }

        Arrays.fill(dist, Integer.MAX_VALUE);

        // 다익스트라 알고리즘 수행
        dijkstra(1);

        // 결과 출력 (1번에서 N번 정점까지의 최단 거리)
        sb.append(dist[N]).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // 다익스트라 알고리즘
    public static void dijkstra(int start) {
        PriorityQueue<Block> pq = new PriorityQueue<>();
        pq.add(new Block(start, 0));

        while (!pq.isEmpty()) {
            int now = pq.peek().next;
            int d = pq.peek().d;
            pq.poll();

            // 이미 처리된 정점이면 스킵
            if (dist[now] < d)
                continue;
            dist[now] = d;

            // 인접한 정점들의 거리 갱신
            for (Block i : matrix.get(now)) {
                if (d + i.d < dist[i.next])
                    pq.add(new Block(i.next, i.d + d));
            }
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Edge implements Comparable<Edge> {
        int node, weight;

        Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight; // 여물 값이 작은 경로를 우선 선택
        }
    }

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int nodeCnt = Integer.parseInt(st.nextToken());
        int lineCnt = Integer.parseInt(st.nextToken());

        // 인접 리스트 (HashMap → List<Edge>로 변경)
        List<Edge>[] nodes = new ArrayList[nodeCnt + 1];
        for (int i = 1; i <= nodeCnt; i++) {
            nodes[i] = new ArrayList<>();
        }

        // 간선 입력 받기
        for (int i = 0; i < lineCnt; i++) {
            st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());
            int lineWeight = Integer.parseInt(st.nextToken());

            // 양방향 간선 추가
            nodes[node1].add(new Edge(node2, lineWeight));
            nodes[node2].add(new Edge(node1, lineWeight));
        }

        // 다익스트라 알고리즘 사용 (우선순위 큐)
        int[] minValues = new int[nodeCnt + 1];
        Arrays.fill(minValues, Integer.MAX_VALUE);
        minValues[1] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(1, 0)); // 시작 노드(1)에서 여물 비용 0으로 시작

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int nowNode = current.node;
            int nowWeight = current.weight;

            // 기존 최소값보다 크다면 무시
            if (nowWeight > minValues[nowNode]) continue;

            // 현재 노드와 연결된 모든 간선 확인
            for (Edge next : nodes[nowNode]) {
                int newWeight = nowWeight + next.weight;

                if (newWeight < minValues[next.node]) {
                    minValues[next.node] = newWeight;
                    pq.add(new Edge(next.node, newWeight));
                }
            }
        }

        // 목적지(N번 헛간)까지의 최소 여물 출력
        System.out.println(minValues[nodeCnt]);
    }
}

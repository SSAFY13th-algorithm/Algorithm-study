import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main
{
    static class Edge implements Comparable<Edge>{
        int node, weight;

        Edge(int node, int weight){
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    public static void main(String args[]) throws NumberFormatException, IOException
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int tc = Integer.parseInt(br.readLine());

        loop:
        for (int test_case = 1; test_case <= tc; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int nodeCnt = Integer.parseInt(st.nextToken());
            int edgeCnt = Integer.parseInt(st.nextToken());
            int wormholeCnt = Integer.parseInt(st.nextToken());

            //인접 리스트
            List<Edge>[] nodes = new ArrayList[nodeCnt +1];
            for (int i = 1; i <= nodeCnt; i++) {
                nodes[i] = new ArrayList<>();
            }


            // 간선 입력 받기
            for (int i = 0; i < edgeCnt; i++) {
                st = new StringTokenizer(br.readLine());

                int node1 = Integer.parseInt(st.nextToken());
                int node2 = Integer.parseInt(st.nextToken());
                int edgeWeight = Integer.parseInt(st.nextToken());

                nodes[node1].add(new Edge(node2,edgeWeight));
                nodes[node2].add(new Edge(node1,edgeWeight));
            }

            for (int i = 0; i < wormholeCnt; i++) {
                st = new StringTokenizer(br.readLine());

                int stNode = Integer.parseInt(st.nextToken());
                int endNode = Integer.parseInt(st.nextToken());
                int minusWeight = Integer.parseInt(st.nextToken());

                // 웜홀은 방향성이 있기에 한 방향으로만 입력
                // 시간 == 가중치로 여김
                // 가중치는 -로
                nodes[stNode].add(new Edge(endNode,(-1)*minusWeight));
            }

            // 처음에는 다익스트라 알고리즘을 사용하려 했으나,
            // 가중치가 음수인 경우가 있기에 오답이 발생
            // 따라서 벨만포드 알고리즘 수행

            int[] minWeights = new int[nodeCnt+1];

            Arrays.fill(minWeights,1000000000);
            minWeights[1] = 0;

            for (int i = 1; i <= nodeCnt; i++) {
                for (int j = 1; j <= nodeCnt; j++) {
                    for (int k = 0; k < nodes[j].size(); k++) {
                        Edge target = nodes[j].get(k);
                        if (minWeights[target.node] > minWeights[j] + target.weight) {
                            minWeights[target.node] = minWeights[j] + target.weight;
                            if ( i == nodeCnt ) {
                                System.out.println("YES");
                                continue loop;
                            }
                        }
                    }
                }
            }

            System.out.println("NO");
        }


    }


}


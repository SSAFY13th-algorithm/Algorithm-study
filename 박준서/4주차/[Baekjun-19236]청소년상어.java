import java.io.*;
import java.util.*;

class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 8방향 이동을 위한 방향 벡터 (반시계 방향: ←, ↖, ↑, ↗, →, ↘, ↓, ↙)
    private static final int[] dx = { 0, -1, -1, -1, 0, 1, 1, 1 };
    private static final int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static final int MAXNUM = 4; // 격자 크기
    private static int ans; // 상어가 먹을 수 있는 물고기 번호의 최대 합

    // 물고기 또는 상어의 상태를 나타내는 클래스
    static class Fish {
        int x, y, d; // 위치(x,y)와 방향(d)
        boolean alive; // 생존 여부

        Fish(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.alive = true;
        }

        // 죽은 물고기 생성자
        Fish(boolean alive) {
            this.x = 0;
            this.y = 0;
            this.d = 0;
            this.alive = alive;
        }

        // 깊은 복사를 위한 복사 생성자
        Fish(Fish another) {
            this.x = another.x;
            this.y = another.y;
            this.d = another.d;
            this.alive = another.alive;
        }
    }

    public static void main(String args[]) throws Exception {
        int[][] matrix = new int[MAXNUM][MAXNUM]; // 격자 상태
        ArrayList<Fish> fish = new ArrayList<>(); // 물고기 리스트 (0번: 상어)

        for (int i = 0; i <= MAXNUM * MAXNUM; i++)
            fish.add(new Fish(0, 0, 0));

        for (int i = 0; i < MAXNUM; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < MAXNUM; j++) {
                int num = Integer.parseInt(st.nextToken()); // 물고기 번호
                int direc = Integer.parseInt(st.nextToken()); // 방향
                matrix[i][j] = num;
                fish.set(num, new Fish(j, i, direc - 1));
            }
        }

        // 초기 상태: 상어가 (0,0) 위치의 물고기를 먹음
        int ate = matrix[0][0];
        fish = eat(matrix, fish, 0, 0);
        matrix = setMatrix(fish);

        // DFS로 모든 경우 탐색
        dfs(matrix, fish, ate);

        sb.append(ans).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    // DFS로 상어의 이동 가능한 모든 경우를 탐색
    public static void dfs(int[][] matrix, ArrayList<Fish> fish, int ate) {
        ans = Math.max(ans, ate); // 최대값 갱신

        // 현재 상태에서 물고기들 복사
        ArrayList<Fish> nFish = deepCopyFish(fish);
        nFish = move(nFish);
        int[][] nMatrix = setMatrix(nFish);

        // 상어의 현재 방향으로 이동 가능한 모든 위치 탐색
        int d = nFish.get(0).d;
        int nx = nFish.get(0).x + dx[d];
        int ny = nFish.get(0).y + dy[d];

        while (nx >= 0 && ny >= 0 && nx < MAXNUM && ny < MAXNUM) {
            if (nMatrix[ny][nx] != -1) { // 물고기가 있는 칸이면
                int nAte = ate + nMatrix[ny][nx]; // 물고기를 먹음
                ArrayList<Fish> tmpFish = eat(nMatrix, nFish, nx, ny);
                int[][] tmpMatrix = setMatrix(tmpFish);

                dfs(tmpMatrix, tmpFish, nAte); // 다음 상태로 재귀
            }

            nx += dx[d]; // 같은 방향으로 계속 이동
            ny += dy[d];
        }
    }

    // 상어가 (x,y) 위치의 물고기를 먹는 함수
    public static ArrayList<Fish> eat(int[][] matrix, ArrayList<Fish> fish, int x, int y) {
        int target = matrix[y][x]; // 먹을 물고기 번호
        ArrayList<Fish> retFish = deepCopyFish(fish);

        // 상어의 위치와 방향을 먹은 물고기의 것으로 갱신
        retFish.set(0, new Fish(retFish.get(target).x, retFish.get(target).y, retFish.get(target).d));
        retFish.set(target, new Fish(false)); // 물고기를 죽음 처리
        return retFish;
    }

    // 모든 물고기의 이동을 처리하는 함수
    public static ArrayList<Fish> move(ArrayList<Fish> fish) {
        int[][] matrix = setMatrix(fish);
        // 1번부터 16번 물고기까지 순서대로 이동
        for (int i = 1; i <= MAXNUM * MAXNUM; i++) {
            if (!fish.get(i).alive)
                continue; // 죽은 물고기는 건너뜀
            Fish nowFish = fish.get(i);
            int nowx = nowFish.x;
            int nowy = nowFish.y;
            int nowd = nowFish.d;

            // 8방향을 순서대로 확인하며 이동 가능한 칸 찾기
            for (int j = 0; j < 8; j++) {
                int nd = (nowd + j) % 8;
                int nx = nowx + dx[nd];
                int ny = nowy + dy[nd];

                // 이동 가능한 칸인지 확인 (경계 내부이고 상어가 없는 칸)
                if (nx >= 0 && ny >= 0 && nx < MAXNUM && ny < MAXNUM && matrix[ny][nx] != 0) {
                    if (matrix[ny][nx] == -1) { // 빈 칸으로 이동
                        fish.get(i).y = ny;
                        fish.get(i).x = nx;
                        fish.get(i).d = nd;
                    } else { // 다른 물고기와 위치 교환
                        int target = matrix[ny][nx];
                        Fish tmp1 = new Fish(nx, ny, nd);
                        Fish tmp2 = new Fish(fish.get(i).x, fish.get(i).y, fish.get(target).d);
                        fish.set(i, tmp1);
                        fish.set(target, tmp2);
                    }
                    matrix[nowy][nowx] = matrix[ny][nx];
                    matrix[ny][nx] = i;
                    break;
                }
            }
        }
        return fish;
    }

    // 현재 물고기들의 상태로 격자를 갱신하는 함수
    public static int[][] setMatrix(ArrayList<Fish> fish) {
        int[][] ret = new int[MAXNUM][MAXNUM];
        for (int i = 0; i < MAXNUM; i++)
            Arrays.fill(ret[i], -1); // 빈 칸은 -1
        for (int i = 0; i <= MAXNUM * MAXNUM; i++) {
            if (fish.get(i).alive)
                ret[fish.get(i).y][fish.get(i).x] = i;
        }
        return ret;
    }

    // ArrayList<Fish>의 깊은 복사를 수행하는 함수
    public static ArrayList<Fish> deepCopyFish(ArrayList<Fish> fish) {
        ArrayList<Fish> ret = new ArrayList<>();
        for (Fish f : fish)
            ret.add(new Fish(f));
        return ret;
    }

    // 디버깅용 격자 출력 함수
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < MAXNUM; i++) {
            for (int j = 0; j < MAXNUM; j++)
                System.out.printf("%d ", matrix[i][j]);
            System.out.println();
        }
        System.out.println();
    }
}

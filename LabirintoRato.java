import java.util.Random;

/**
 * Classe que implementa a lógica do labirinto do rato.
 */
public class LabirintoRato {
    private static final int TAMANHO_PADRAO = 10;
    private static final double PROBABILIDADE_CAMINHO = 0.8;
    private static final int MAX_TENTATIVAS = 10; // Número máximo de tentativas para gerar um labirinto válido
    
    private final int N;
    private final int[][] maze;
    private final int[][] solution;
    private final Random rand;

    /**
     * Construtor padrão que cria um labirinto 10x10.
     */
    public LabirintoRato() {
        this(TAMANHO_PADRAO);
    }

    /**
     * Construtor que permite especificar o tamanho do labirinto.
     * @param tamanho Tamanho do labirinto (tamanho x tamanho)
     */
    public LabirintoRato(int tamanho) {
        this.N = tamanho;
        this.maze = new int[N][N];
        this.solution = new int[N][N];
        this.rand = new Random();
    }

    private void limparMatrizes() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                maze[i][j] = 0;
                solution[i][j] = 0;
            }
        }
    }

    /**
     * Gera um novo labirinto aleatório.
     */
    public void gerarLabirinto() {
        int tentativas = 0;
        boolean temSolucao = false;
        
        while (!temSolucao && tentativas < MAX_TENTATIVAS) {
            // Limpa completamente as matrizes
            limparMatrizes();
            
            // Gera novo labirinto
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    maze[i][j] = (rand.nextDouble() < PROBABILIDADE_CAMINHO) ? 1 : 0;
                }
            }
            
            // Garante que início e fim estão livres
            maze[0][0] = 1;
            maze[N - 1][N - 1] = 1;

            // Tenta resolver o labirinto
            temSolucao = solve(0, 0);
            tentativas++;
        }
        
        // Se não encontrou solução após várias tentativas, força um caminho
        if (!temSolucao) {
            limparMatrizes();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    maze[i][j] = 1;
                }
            }
            solve(0, 0); // Resolve o labirinto completamente livre
        }
    }

    /**
     * Verifica se uma posição é segura para mover.
     */
    private boolean isSafe(int x, int y) {
        return (x >= 0 && y >= 0 && x < N && y < N 
                && maze[x][y] == 1 
                && solution[x][y] == 0);
    }

    /**
     * Resolve o labirinto usando backtracking.
     * @return true se encontrou uma solução, false caso contrário
     */
    public boolean solve(int x, int y) {
        // Verifica se chegou ao final
        if (x == N - 1 && y == N - 1) {
            solution[x][y] = 1;
            return true;
        }

        // Verifica se a posição atual é válida
        if (isSafe(x, y)) {
            // Marca a posição atual como parte da solução
            solution[x][y] = 1;

            // Tenta mover em todas as direções
            if (solve(x + 1, y)) return true; // Direita
            if (solve(x, y + 1)) return true; // Baixo
            if (solve(x - 1, y)) return true; // Esquerda
            if (solve(x, y - 1)) return true; // Cima

            // Se nenhum caminho funcionou, desmarca a posição atual
            solution[x][y] = 0;
            return false;
        }
        return false;
    }

    /**
     * Retorna o labirinto atual.
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * Retorna a solução atual.
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Retorna o tamanho do labirinto.
     */
    public int getSize() {
        return N;
    }
}

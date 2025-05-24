import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LabirintoRatoGUI extends JPanel {
    private static final int TAMANHO_CELULA = 40;
    private LabirintoRato labirinto;
    private List<Point> caminhoAnimacao;
    private int indiceAnimacao;
    private Timer timer;
    private boolean animando;
    private List<Point> caminhoPercorrido;
    public JLabel mensagemLabel;
    public JButton botaoNovoLabirinto;
    public JButton botaoAnimacao;

    public LabirintoRatoGUI() {
        labirinto = new LabirintoRato();
        labirinto.gerarLabirinto();
        labirinto.solve(0, 0);
        setPreferredSize(new Dimension(labirinto.getSize() * TAMANHO_CELULA, 
                                     labirinto.getSize() * TAMANHO_CELULA));
        
        // Inicializa a animação
        caminhoAnimacao = new ArrayList<>();
        caminhoPercorrido = new ArrayList<>();
        indiceAnimacao = 0;
        animando = false;
        
        // Cria o timer para a animação
        timer = new Timer(200, e -> {
            if (indiceAnimacao < caminhoAnimacao.size()) {
                caminhoPercorrido.add(caminhoAnimacao.get(indiceAnimacao));
                repaint();
                indiceAnimacao++;
            } else {
                timer.stop();
                animando = false;
                botaoNovoLabirinto.setEnabled(true);
                botaoAnimacao.setEnabled(true);
            }
        });
    }

    public void iniciarAnimacao() {
        if (!animando) {
            try {
                // Desabilita os botões durante a animação
                botaoAnimacao.setEnabled(false);
                botaoNovoLabirinto.setEnabled(false);

                // Coleta o caminho da solução
                caminhoAnimacao.clear();
                caminhoPercorrido.clear();
                int[][] solution = labirinto.getSolution();
                boolean temSolucao = false;
                
                for (int i = 0; i < labirinto.getSize(); i++) {
                    for (int j = 0; j < labirinto.getSize(); j++) {
                        if (solution[i][j] == 1) {
                            caminhoAnimacao.add(new Point(j, i));
                            temSolucao = true;
                        }
                    }
                }
                
                if (!temSolucao) {
                    mensagemLabel.setText("⚠️ Não há solução para este labirinto!");
                    mensagemLabel.setForeground(Color.RED);
                    botaoNovoLabirinto.setEnabled(true);
                    botaoAnimacao.setEnabled(true);
                    return;
                }
                
                mensagemLabel.setText("Rato percorrendo o labirinto...");
                mensagemLabel.setForeground(Color.BLACK);
                indiceAnimacao = 0;
                animando = true;
                timer.start();
            } catch (Exception e) {
                mensagemLabel.setText("Erro ao iniciar animação!");
                mensagemLabel.setForeground(Color.RED);
                botaoNovoLabirinto.setEnabled(true);
                botaoAnimacao.setEnabled(true);
            }
        }
    }

    public void novoLabirinto() {
        try {
            // Para a animação atual se estiver rodando
            if (timer.isRunning()) {
                timer.stop();
            }
            
            // Desabilita os botões durante a geração
            botaoAnimacao.setEnabled(false);
            botaoNovoLabirinto.setEnabled(false);
            
            // Gera novo labirinto
            labirinto = new LabirintoRato();
            labirinto.gerarLabirinto();
            labirinto.solve(0, 0);
            
            // Limpa as listas de animação
            caminhoAnimacao.clear();
            caminhoPercorrido.clear();
            indiceAnimacao = 0;
            animando = false;
            
            // Atualiza a interface
            mensagemLabel.setText("Clique em 'Iniciar Animação' para começar");
            mensagemLabel.setForeground(Color.BLACK);
            repaint();
            
            // Reabilita os botões
            botaoAnimacao.setEnabled(true);
            botaoNovoLabirinto.setEnabled(true);
        } catch (Exception e) {
            mensagemLabel.setText("Erro ao gerar novo labirinto!");
            mensagemLabel.setForeground(Color.RED);
            botaoAnimacao.setEnabled(true);
            botaoNovoLabirinto.setEnabled(true);
        }
    }

    private void desenharRato(Graphics g, int x, int y) {
        // Corpo do rato
        g.setColor(Color.GRAY);
        g.fillOval(x + 5, y + 5, 30, 20);
        
        // Orelhas
        g.setColor(Color.PINK);
        g.fillOval(x + 8, y + 2, 8, 8);
        g.fillOval(x + 24, y + 2, 8, 8);
        
        // Olhos
        g.setColor(Color.BLACK);
        g.fillOval(x + 12, y + 10, 4, 4);
        g.fillOval(x + 24, y + 10, 4, 4);
        
        // Focinho
        g.setColor(Color.PINK);
        g.fillOval(x + 5, y + 15, 8, 6);
        
        // Rabo
        g.setColor(Color.GRAY);
        g.drawLine(x + 35, y + 15, x + 45, y + 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] maze = labirinto.getMaze();
        int N = labirinto.getSize();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Desenha as paredes
                if (maze[i][j] == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * TAMANHO_CELULA, i * TAMANHO_CELULA, TAMANHO_CELULA, TAMANHO_CELULA);
                } else {
                    // Caminho livre
                    g.setColor(Color.WHITE);
                    g.fillRect(j * TAMANHO_CELULA, i * TAMANHO_CELULA, TAMANHO_CELULA, TAMANHO_CELULA);
                }

                // Desenha o grid
                g.setColor(Color.GRAY);
                g.drawRect(j * TAMANHO_CELULA, i * TAMANHO_CELULA, TAMANHO_CELULA, TAMANHO_CELULA);

                // Marca início e fim
                if (i == 0 && j == 0) {
                    g.setColor(Color.BLUE);
                    g.fillOval(j * TAMANHO_CELULA + 10, i * TAMANHO_CELULA + 10, 20, 20);
                }
                if (i == N - 1 && j == N - 1) {
                    g.setColor(Color.RED);
                    g.fillOval(j * TAMANHO_CELULA + 10, i * TAMANHO_CELULA + 10, 20, 20);
                }
            }
        }

        // Desenha o caminho percorrido
        for (Point p : caminhoPercorrido) {
            g.setColor(new Color(144, 238, 144)); // Verde claro
            g.fillRect(p.x * TAMANHO_CELULA, p.y * TAMANHO_CELULA, TAMANHO_CELULA, TAMANHO_CELULA);
        }

        // Desenha o rato na posição atual da animação
        if (animando && indiceAnimacao < caminhoAnimacao.size()) {
            Point posicaoAtual = caminhoAnimacao.get(indiceAnimacao);
            desenharRato(g, posicaoAtual.x * TAMANHO_CELULA, posicaoAtual.y * TAMANHO_CELULA);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Labirinto do Rato - GUI");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                LabirintoRatoGUI panel = new LabirintoRatoGUI();
                frame.add(panel);
                
                // Painel para botões e mensagens
                JPanel painelControle = new JPanel(new BorderLayout());
                
                // Painel para botões
                JPanel painelBotoes = new JPanel();
                panel.botaoAnimacao = new JButton("Iniciar Animação");
                panel.botaoAnimacao.addActionListener(e -> panel.iniciarAnimacao());
                
                panel.botaoNovoLabirinto = new JButton("Novo Labirinto");
                panel.botaoNovoLabirinto.addActionListener(e -> panel.novoLabirinto());
                
                painelBotoes.add(panel.botaoAnimacao);
                painelBotoes.add(panel.botaoNovoLabirinto);
                
                // Label para mensagens
                panel.mensagemLabel = new JLabel("Clique em 'Iniciar Animação' para começar");
                panel.mensagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                painelControle.add(painelBotoes, BorderLayout.NORTH);
                painelControle.add(panel.mensagemLabel, BorderLayout.SOUTH);
                
                frame.add(painelControle, BorderLayout.SOUTH);
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar a aplicação: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}


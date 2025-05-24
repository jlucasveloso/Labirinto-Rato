import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Preparar a interface gráfica
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Labirinto do Rato");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);

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
                panel.botaoNovoLabirinto.setEnabled(false);
                
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

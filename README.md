O programa utiliza as seguintes tecnologias e bibliotecas do Java:

1. **Java Swing** (`javax.swing.*`):
   - `JFrame`: Para criar a janela principal
   - `JPanel`: Para criar painéis na interface
   - `JButton`: Para os botões de controle
   - `JLabel`: Para exibir mensagens
   - `BorderLayout`: Para organizar os componentes na interface
   - `SwingUtilities`: Para garantir que a interface gráfica seja executada na thread correta

2. **Java AWT** (`java.awt.*`):
   - `Graphics`: Para desenhar o labirinto e o rato
   - `Color`: Para definir cores dos elementos
   - `Dimension`: Para definir tamanhos
   - `Point`: Para representar coordenadas
   - `BorderLayout`: Para layout dos componentes

3. **Java Collections** (`java.util.*`):
   - `ArrayList`: Para armazenar o caminho do rato
   - `List`: Interface para manipular listas
   - `Random`: Para gerar números aleatórios no labirinto

4. **Recursos do Java Core**:
   - `Timer`: Para controlar a animação
   - `Exception`: Para tratamento de erros
   - `System`: Para operações do sistema

5. **Conceitos de Programação**:
   - Programação Orientada a Objetos
   - Threads (através do SwingUtilities)
   - Eventos (ActionListener)
   - Backtracking (algoritmo de resolução do labirinto)

6. **Estruturas de Dados**:
   - Matrizes bidimensionais (para o labirinto)
   - Listas (para o caminho)
   - Pilha (implementada implicitamente no backtracking)

7. **Padrões de Design**:
   - MVC (Model-View-Controller) simplificado:
     - Model: `LabirintoRato.java`
     - View: `LabirintoRatoGUI.java`
     - Controller: Lógica distribuída entre as classes

8. **Recursos de Interface**:
   - Eventos de mouse
   - Eventos de botão
   - Repaint para atualização da tela
   - Layout managers

9. **Recursos de Sistema**:
   - Gerenciamento de memória
   - Thread de interface gráfica
   - Sistema de eventos

10. **Bibliotecas de Utilidades**:
    - `javax.swing.Timer`: Para animações
    - `java.awt.Graphics`: Para desenho
    - `java.util.Random`: Para geração aleatória

Para executar o programa, você precisa de:
1. JDK (Java Development Kit) instalado
2. Ambiente de desenvolvimento Java (IDE ou compilador)
3. Sistema operacional com suporte a interface gráfica
4. Memória suficiente para executar a JVM (Java Virtual Machine)

O programa é multiplataforma, podendo rodar em:
- Windows
- Linux
- macOS

Desde que o Java Runtime Environment (JRE) esteja instalado no sistema.

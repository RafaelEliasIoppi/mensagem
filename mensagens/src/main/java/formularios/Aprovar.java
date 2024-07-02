package formularios;

import dao.MensagemDao;
import entidades.Mensagem;
import externos.EmailSender;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Aprovar extends JFrame {
    private JTable tabelaMensagens;
    private JTextArea txtObservacoes;
    private MensagemDao mensagemDao;
    private DefaultTableModel modeloTabela;

    public Aprovar(MensagemDao mensagemDao) throws Throwable {
        this.mensagemDao = mensagemDao;
        criarTela();
    }

    public void criarTela() {
        setTitle("Aprovar Mensagens");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com título
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelTopo.setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Aprovar Mensagens", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        painelTopo.add(lblTitulo, BorderLayout.NORTH);

        add(painelTopo, BorderLayout.NORTH);

        // Modelo de tabela
        modeloTabela = new DefaultTableModel();
        tabelaMensagens = new JTable(modeloTabela);
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Remetente");
        modeloTabela.addColumn("Setor");
        modeloTabela.addColumn("Destinatário");
        modeloTabela.addColumn("Nível");
        modeloTabela.addColumn("Data");
        modeloTabela.addColumn("Conteúdo");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("Status1");
        modeloTabela.addColumn("Status2");
        modeloTabela.addColumn("Observação");

        // Ajuste a largura das colunas
        TableColumnModel columnModel = tabelaMensagens.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(100); // Remetente
        columnModel.getColumn(2).setPreferredWidth(100); // Setor
        columnModel.getColumn(3).setPreferredWidth(100); // Destinatário
        columnModel.getColumn(4).setPreferredWidth(50); // Nível
        columnModel.getColumn(5).setPreferredWidth(100); // Data
        columnModel.getColumn(6).setPreferredWidth(400); // Conteúdo
        columnModel.getColumn(7).setPreferredWidth(300); // Email
        columnModel.getColumn(8).setPreferredWidth(100); // Status1
        columnModel.getColumn(9).setPreferredWidth(100); // Status2
        columnModel.getColumn(10).setPreferredWidth(200); // Observação

        // Adicionar renderizador de célula personalizado
        tabelaMensagens.setDefaultRenderer(Object.class, new MensagemCellRenderer());

        carregarMensagens(); // Carrega mensagens na tabela

        // Adicionando listener de clique na tabela
        tabelaMensagens.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Verifica se foi clique
                    int linhaSelecionada = tabelaMensagens.getSelectedRow();
                    exibirMensagemCompleta(linhaSelecionada);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaMensagens);
        add(scrollPane, BorderLayout.CENTER);

        // Configuração do painel de observações e botões
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BorderLayout());
        JLabel lblObservacoes = new JLabel("Digite as observações abaixo (obrigatório em caso de reprovação)", SwingConstants.CENTER);
        painelInferior.add(lblObservacoes, BorderLayout.NORTH);
        txtObservacoes = new JTextArea(5, 40);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        painelInferior.add(new JScrollPane(txtObservacoes), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnAprovar1 = new JButton("Aprovar1");
        JButton btnAprovar2 = new JButton("Aprovar2");
        JButton btnAprovar3 = new JButton("Aprovar3");
        JButton btnReprovar1 = new JButton("Reprovar1");
        JButton btnReprovar2 = new JButton("Reprovar2");
        JButton btnReprovar3 = new JButton("Reprovar3");

        painelBotoes.add(btnAprovar1);
        painelBotoes.add(btnAprovar2);
        painelBotoes.add(btnAprovar3);
        painelBotoes.add(btnReprovar1);
        painelBotoes.add(btnReprovar2);
        painelBotoes.add(btnReprovar3);

        painelInferior.add(painelBotoes, BorderLayout.SOUTH);
        add(painelInferior, BorderLayout.SOUTH);

        pack(); // Redimensiona a janela para o tamanho ideal
    }

    private void carregarMensagens() {
        List<Mensagem> mensagens = mensagemDao.consultarTodasMensagens(); // carrega todas as mensagens e colocar em uma lista
        modeloTabela.setRowCount(0); // Limpa a tabela

        for (Mensagem mensagem : mensagens) {
            // Depuração para verificar o email
            System.out.println("Email: " + mensagem.getDestinatario().getEmail());
            System.out.println("Nome: " + mensagem.getDestinatario().getNome());
            
            modeloTabela.addRow(new Object[]{
                mensagem.getMsgId(),
                mensagem.getRemetente(),
                mensagem.getSetor(),
                mensagem.getDestinatario().getNome(), // Aqui exibimos o nome do destinatário
                mensagem.getDestinatario().getNivel(),
                mensagem.getDataEnvio(), // Aqui exibimos a data de envio
                mensagem.getMensagem(), // Aqui exibimos o conteúdo da mensagem completa
                mensagem.getDestinatario().getEmail(), // Aqui exibimos o email do destinatário
                mensagem.getStatus1(),
                mensagem.getStatus2(),
                mensagem.getObservacao()
            });
        }
    }

    private void exibirMensagemCompleta(int linhaSelecionada) {
        if (linhaSelecionada >= 0) {
            String mensagem = (String) modeloTabela.getValueAt(linhaSelecionada, 6);
            JOptionPane.showMessageDialog(this, mensagem, "Conteúdo da Mensagem", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limparCampoObservacoes() {
        txtObservacoes.setText("");
    }

    private void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    private class MensagemCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Personalize o renderizador de célula conforme necessário
            return this;
        }
    }
}

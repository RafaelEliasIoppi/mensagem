package formularios;

import dao.MensagemDao;
import entidades.Mensagem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ConsultarMensagem extends JFrame {
    private MensagemDao mensagemDao;
    private JTextPane areaTexto;
    private JLabel lblTitulo;
    private JLabel lblEstatisticas;

    public ConsultarMensagem(JFrame parent, MensagemDao mensagemDao) {
        super("Consultar Mensagens");
        this.mensagemDao = mensagemDao;

        // Configurações da janela
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicialização do painel de título e estatísticas
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelTopo.setBackground(new Color(240, 240, 240));

        // Título
        lblTitulo = new JLabel("Consulta de Mensagens", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        painelTopo.add(lblTitulo, BorderLayout.NORTH);

        // Estatísticas
        lblEstatisticas = new JLabel("", JLabel.CENTER);
        lblEstatisticas.setFont(new Font("Arial", Font.PLAIN, 16));
        painelTopo.add(lblEstatisticas, BorderLayout.CENTER);

        add(painelTopo, BorderLayout.NORTH);

        // Inicialização da área de texto
        areaTexto = new JTextPane();
        areaTexto.setEditable(false);
        areaTexto.setMargin(new Insets(20, 20, 20, 20));

        // Scroll pane para a área de texto
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Consulta as mensagens ao iniciar
        consultarMensagens();
    }

    private void consultarMensagens() {
        List<Mensagem> mensagens = mensagemDao.consultarTodasMensagens();
        atualizarEstatisticas(mensagens);
        exibirMensagens(mensagens);
    }

    private void atualizarEstatisticas(List<Mensagem> mensagens) {
        long totalMensagens = mensagens.size();
        long mensagensNovas = mensagens.stream().filter(m -> "Nova".equals(m.getStatus1())).count();
        lblEstatisticas.setText("<html><body><b>Total de Mensagens:</b> " + totalMensagens + "<br><b>Mensagens Novas:</b> " + mensagensNovas + "</body></html>");
    }

    public void exibirMensagens(List<Mensagem> mensagens) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; background-color: #f0f0f0; }");
        sb.append(".mensagem-container { border: 2px solid #0066cc; padding: 15px; border-radius: 8px; margin-bottom: 20px; background-color: #e0e0e0; }");
        sb.append(".mensagem-container h4 { margin: 0; padding: 0 0 10px 0; border-bottom: 1px solid #0066cc; }");
        sb.append(".mensagem-container p { margin: 5px 0; }");
        sb.append("</style>");
        sb.append("</head><body>");

        for (Mensagem mensagem : mensagens) {
            sb.append("<div class='mensagem-container'>");
            sb.append("<h4>ID: ").append(mensagem.getMsgId()).append("</h4>");
            sb.append("<p><b>Remetente:</b> ").append(mensagem.getRemetente()).append("</p>");
            sb.append("<p><b>Setor:</b> ").append(mensagem.getSetor()).append("</p>");
            sb.append("<p><b>Nível:</b> ").append(mensagem.getNivel()).append("</p>");
            sb.append("<p><b>Destinatário:</b> ").append(mensagem.getDestinatario().getNome()).append("</p>");
            sb.append("<p><b>Data Envio:</b> ").append(mensagem.getDataEnvio()).append("</p>");
            sb.append("<p><b>Mensagem:</b><br>").append(quebrarTexto(mensagem.getMensagem())).append("</p>");
            sb.append("<p><b>Email:</b> ").append(mensagem.getEmail()).append("</p>");
            sb.append("<p><b>Status1:</b> ").append(mensagem.getStatus1()).append("</p>");
            sb.append("<p><b>Status2:</b> ").append(mensagem.getStatus2()).append("</p>");
            sb.append("<p><b>Observações:</b> ").append(mensagem.getObservacao()).append("</p>");
            sb.append("</div>");
        }

        sb.append("</body></html>");

        areaTexto.setContentType("text/html");
        areaTexto.setText(sb.toString());
    }

    private String quebrarTexto(String texto) {
        StringBuilder sb = new StringBuilder();
        int caracteresPorLinha = 100;
        int index = 0;
        while (index < texto.length()) {
            sb.append(texto, index, Math.min(index + caracteresPorLinha, texto.length())).append("<br>");
            index += caracteresPorLinha;
        }
        return sb.toString();
    }

    public void atualizarTabela() {
        consultarMensagens();
    }
}

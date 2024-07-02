package testes;

import formularios.Aprovar;

import formularios.ConsultarMensagem;
import formularios.MensagemForm;
import dao.MensagemDao;
import entidades.Mensagem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuMensagem;
    private JMenu menuSobre;
    private JMenuItem menuItemCadastrarMensagem;
    private JMenuItem menuItemConsultarMensagens;
    private JMenuItem menuItemExcluirMensagem;
    private JMenuItem menuItemAprovarMensagem;
    private JMenuItem menuItemSair;

    public Menu() {
        setTitle("Menu Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criação da barra de menu
        menuBar = new JMenuBar();

        // Criação do menu "Mensagem"
        menuMensagem = new JMenu("Mensagem");
        menuBar.add(menuMensagem);
        menuMensagem.addSeparator();
        
               
        // Criação dos itens de menu do menu "Mensagem"
        menuItemCadastrarMensagem = new JMenuItem("Cadastrar Mensagem");
        menuMensagem.add(menuItemCadastrarMensagem);

        menuItemConsultarMensagens = new JMenuItem("Consultar Mensagens");
        menuMensagem.add(menuItemConsultarMensagens);

        menuItemExcluirMensagem = new JMenuItem("Excluir Mensagem");
        menuMensagem.add(menuItemExcluirMensagem);
        
        menuItemAprovarMensagem = new JMenuItem("Aprovar Mensagem");
        menuMensagem.add(menuItemAprovarMensagem);
        
        
        menuItemSair = new JMenuItem("Sair");
        menuMensagem.add(menuItemSair);
        
        // Criação do menu "Sobre" 
        menuSobre = new JMenu("Ajuda");
        menuBar.add(menuSobre);
        
        // Criação do item de menu "Sobre" dentro do menu "Opções"
        JMenuItem menuItemSobre = new JMenuItem("Sobre");
        menuSobre.add(menuItemSobre);
        
        menuItemSobre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSobre();
            }
        });
        

        
        menuItemCadastrarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ConsultarMensagem consultarMensagemInstance = new ConsultarMensagem(Menu.this, new MensagemDao());
                    MensagemForm mensagemForm = new MensagemForm(consultarMensagemInstance);
                    mensagemForm.setVisible(true);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        menuItemConsultarMensagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioConsultarMensagens();
            }
        });

        menuItemExcluirMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					excluirMensagem();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
            }
        });
        
        menuItemAprovarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MensagemDao mensagemDao = new MensagemDao();
                	Aprovar aprovar = new Aprovar(mensagemDao);
                	aprovar.setVisible(true);
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuItemSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sairAplicacao();
            }
        });

        // Adiciona a barra de menu ao frame
        setJMenuBar(menuBar);
    }

    
    private void abrirFormularioConsultarMensagens() {
        try {
            // Criar uma instância do MensagemDao para acessar as mensagens no banco de dados
            MensagemDao mensagemDao = new MensagemDao();

            // Criar uma instância do formulário ConsultarMensagem e exibi-lo
            ConsultarMensagem form = new ConsultarMensagem(this, mensagemDao);
            form.setSize(800, 600); // Definir o tamanho do formulário
            form.setVisible(true);
        } catch (Throwable throwable) {
            // Em caso de erro ao abrir o formulário, exibir mensagem de erro
            JOptionPane.showMessageDialog(this, "Erro ao abrir formulário de consulta: " + throwable.getMessage());
        }
    }

    private void excluirMensagem() throws Throwable {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Informe o ID da mensagem a ser excluída:");
            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr);
                MensagemDao mensagemDao = new MensagemDao();
                Mensagem mensagem = mensagemDao.consultarMensagemPorId(id);
                if (mensagem != null) {
                    mensagemDao.excluirMensagem(id);
                    JOptionPane.showMessageDialog(this, "Mensagem excluída com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Mensagem com o ID informado não encontrada.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir mensagem: " + e.getMessage());
        }
    }


        
    private void mostrarSobre() {
        // Texto para exibir
        String sobreTexto = "<html><body>" +
                            "<h2>Aplicativo de Mensagens</h2>" +
                            "<p>Versão 1.0</p>" +
                            "<p>Desenvolvido por Rafael Elias Ioppi</p>" +
                            "</body></html>";

        // Caminho absoluto da imagem
        String caminhoImagem = "C:\\Users\\rafae\\eclipse-work\\mensagens\\src\\main\\java\\rafael33.png";

        // Redimensionar a imagem
        ImageIcon icon = carregarImagemRedimensionada(caminhoImagem, 100, 100);

        // Verifica se a imagem foi carregada corretamente
        if (icon != null) {
            // Criar um JLabel para exibir o texto e a imagem
            JLabel label = new JLabel(sobreTexto, icon, JLabel.CENTER);

            // Mostrar o JOptionPane com o JLabel personalizado
            JOptionPane.showMessageDialog(this, label, "Sobre", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Imagem não encontrada ou não pôde ser carregada.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon carregarImagemRedimensionada(String caminhoImagem, int largura, int altura) {
        try {
            File imgFile = new File(caminhoImagem);
            Image img = ImageIO.read(imgFile);

            // Redimensionar a imagem
            Image imgRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

            return new ImageIcon(imgRedimensionada);
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            return null;
        }
    }

    private void sairAplicacao() {
        System.exit(0);
    }
    
    public static void main(String[] args) {
        // Garantir que a criação da interface gráfica seja feita na thread de despacho de eventos (Event Dispatch Thread - EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true); // Tornar o frame do menu visível
            
            }
        });

}
}
    	

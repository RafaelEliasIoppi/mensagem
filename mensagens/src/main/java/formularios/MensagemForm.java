package formularios;

import dao.MensagemDao;
import entidades.Destinatario;
import entidades.Mensagem;
import externos.EmailSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import formularios.ConsultarMensagem;

public class MensagemForm extends JFrame {
    private JTextField txtRemetente;
    private JTextField txtSetor;
    private JTextArea txtMensagem;
    private JTextField txtStatus1;
    private JTextField txtStatus2;
    private JButton btnSalvar;
    private JButton btnLimparCampos;
    private JButton btnCadastrarDestinatario;
    private JButton btnExcluirDestinatario;
    private JButton btnSair;
    private List<Destinatario> destinatarios;
    private JComboBox<String> comboBoxDestinatarios;
    private MensagemDao mensagemDao;
    
	private ConsultarMensagem consultarMensagem;

 
	public MensagemForm(ConsultarMensagem consultarMensagem, MensagemDao mensagemDao ) throws Throwable {
        this.consultarMensagem = consultarMensagem;
        this.mensagemDao = mensagemDao;
      
           
        setTitle("Cadastro de Mensagem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicialização dos componentes
        txtRemetente = new JTextField(20);
        txtSetor = new JTextField(20);
        txtMensagem = new JTextArea(15,50);
        txtMensagem.setLineWrap(true);
        txtMensagem.setWrapStyleWord(true);
        txtStatus1 = new JTextField("Pendente", 10);
        txtStatus1.setEditable(false);
        txtStatus2 = new JTextField("Pendente", 10);
        txtStatus2.setEditable(false);
        btnSalvar = new JButton("Salvar");
        btnLimparCampos = new JButton("Limpar Campos");
        btnSair = new JButton("Sair");
        btnCadastrarDestinatario = new JButton("Cadastrar Destinatário");
        btnExcluirDestinatario = new JButton("Excluir Destinatário");

     // Inicialização dos destinatários
        destinatarios = criarDestinatarios();
        List<String> nomesDestinatarios = new ArrayList<>();
        for (Destinatario destinatario : destinatarios) {
            nomesDestinatarios.add(destinatario.getNome());
        }
        comboBoxDestinatarios = new JComboBox<>(nomesDestinatarios.toArray(new String[0]));
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Remetente:"), gbc);
        gbc.gridx = 1;
        add(txtRemetente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Setor:"), gbc);
        gbc.gridx = 1;
        add(txtSetor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Destinatário:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboBoxDestinatarios, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.gridheight = 10; // Aumentar a altura para ocupar mais espaço
        gbc.fill = GridBagConstraints.BOTH; // Permitir que o JTextArea se expanda em ambas as direções
        add(new JScrollPane(txtMensagem), gbc);

        // Resetar para o próximo componente
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 13; // Ajustar a posição para abaixo da área de mensagem
        gbc.insets = new Insets(15, 5, 5, 5);
        add(btnSalvar, gbc);

        gbc.gridx = 1;
        add(btnLimparCampos, gbc);

        gbc.gridx = 2;
        add(btnSair, gbc);

        gbc.gridx = 3;
        add(btnCadastrarDestinatario, gbc);

        gbc.gridx = 4;
        add(btnExcluirDestinatario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14; // Ajustar a posição para abaixo dos botões
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        add(new JLabel("Status 1:"), gbc);

        gbc.gridx = 1;
        add(txtStatus1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 15; // Ajustar a posição para abaixo do status 1
        gbc.gridwidth = 2;
        add(new JLabel("Status 2:"), gbc);

        gbc.gridx = 1;
        add(txtStatus2, gbc);
        gbc.gridx = 1;
        add(txtStatus2, gbc);
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarMensagem();
            }
        });

        btnLimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sairDoFormulario();
            }
        });

        btnCadastrarDestinatario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               cadastrarDestinatario();
            
            }
			
        });

        btnExcluirDestinatario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            excluirDestinatario();
            }
        });
    }

	 private void cadastrarDestinatario() {
	        String nome = JOptionPane.showInputDialog(this, "Digite o nome do destinatário:");
			for (Destinatario destinatario : destinatarios) {
				if (destinatario.getNome().equals(nome)) {
					JOptionPane.showMessageDialog(this, "Já existe um destinatário com esse nome.");
					return;
				}
			}
	        if (nome == null || nome.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
	            return;
	        }

	        String nivelStr = JOptionPane.showInputDialog(this, "Digite o nível do destinatário (número inteiro):");
	        int nivel;
	        try {
	            nivel = Integer.parseInt(nivelStr);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Nível deve ser um número inteiro.");
	            return;
	        }

	        String email = JOptionPane.showInputDialog(this, "Digite o email do destinatário:");
	        if (email == null || email.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Email não pode ser vazio.");
	            return;
	        }

	        Destinatario novoDestinatario = new Destinatario(nome, nivel, email);
	        destinatarios.add(novoDestinatario);

	        atualizarComboBoxDestinatarios();

	        salvarDestinatariosEmArquivo();
	        JOptionPane.showMessageDialog(this, "Nome: " + nome + "\nNível: " + nivel + "\nEmail: " + email);
	        JOptionPane.showMessageDialog(this, "Destinatário cadastrado com sucesso.");
	        
	    }
   
	public void atualizarComboBoxDestinatarios() {
        comboBoxDestinatarios.removeAllItems();

        for (Destinatario destinatario : destinatarios) {
            comboBoxDestinatarios.addItem(destinatario.getNome());
        }
    }

    private List<Destinatario> criarDestinatarios() throws IOException {
        List<Destinatario> destinatarios = new ArrayList<>();
        File arquivo = new File("destinatarios.txt");

        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                int nivel = Integer.parseInt(dados[1]);
                String email = dados[2];
                Destinatario destinatario = new Destinatario(nome, nivel, email);
                destinatarios.add(destinatario);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Arquivo de destinatários não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Erro ao ler destinatários do arquivo!");
        }

        return destinatarios;
    }
    
    private void excluirDestinatario() {
        String nomeDestinatario = (String) comboBoxDestinatarios.getSelectedItem();
        if (nomeDestinatario == null || nomeDestinatario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um destinatário para excluir.");
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o destinatário selecionado?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            // Remove o destinatário da lista
            destinatarios.removeIf(dest -> dest.getNome().equals(nomeDestinatario));
            
            // Atualiza o combo box de destinatários
            atualizarComboBoxDestinatarios();
            
            // Salva a lista atualizada de destinatários no arquivo
            salvarDestinatariosEmArquivo();

            JOptionPane.showMessageDialog(this, "Destinatário excluído com sucesso.");
        }
    }
   

    private void salvarDestinatariosEmArquivo() {
        File arquivo = new File("destinatarios.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Destinatario destinatario : destinatarios) {
                String linha = destinatario.getNome() + ";" + destinatario.getNivel() + ";" + destinatario.getEmail();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar destinatários no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
		private void limparCampos() {
			txtRemetente.setText("");
			txtSetor.setText("");
			txtMensagem.setText("");

		}
    private void salvarMensagem() {
        String remetente = txtRemetente.getText().trim();
        String setor = txtSetor.getText().trim();
        String nomeDestinatario = (String) comboBoxDestinatarios.getSelectedItem();
        int nivel = destinatarios.stream().filter(dest -> dest.getNome().equals(nomeDestinatario)).findFirst().map(Destinatario::getNivel).orElse(0);
        String mensagemTexto = txtMensagem.getText().trim();
        String email = destinatarios.stream().filter(dest -> dest.getNome().equals(nomeDestinatario)).findFirst().map(Destinatario::getEmail).orElse(null);
        String status1 = "Pendente";
        String status2 = "Pendente";
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataEnvio = agora.format(formatter);

        if (remetente.isEmpty() || setor.isEmpty() || mensagemTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios. Preencha todos os campos antes de salvar.");
            return;
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setRemetente(remetente);
        mensagem.setSetor(setor);
        mensagem.setDestinatario(destinatarios.stream().filter(dest -> dest.getNome().equals(nomeDestinatario)).findFirst().orElse(null));
        mensagem.setNivel(nivel);
        mensagem.setDataEnvio(dataEnvio);
        mensagem.setMensagem(mensagemTexto);
        mensagem.setEmail(email);
        mensagem.setStatus1(status1);
        mensagem.setStatus2(status2);
        mensagem.setObservacao("Nenhuma observação");

        try {
            int idMensagem = mensagemDao.cadastrarMensagem(mensagem);
            mensagem.setMsgId(idMensagem);
            enviarEmail(mensagem);
            JOptionPane.showMessageDialog(this, "Mensagem cadastrada com sucesso! ID: " + idMensagem);
            limparCampos();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar mensagem: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(this, "Erro desconhecido ao salvar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void enviarEmail(Mensagem mensagem) {
        String conteudoEmail = "Olá,\n\n" +
                "Segue abaixo os detalhes da mensagem cadastrada:\n\n" +
                "ID da mensagem: " + mensagem.getMsgId() + "\n" +
                "Remetente: " + mensagem.getRemetente() + "\n" +
                "Setor: " + mensagem.getSetor() + "\n" +
                "Destinatário: " + mensagem.getDestinatario().getNome() + "\n" +
               	"Data de Envio: " + mensagem.getDataEnvio() + "\n" +
               	"Mensagem: \n" + mensagem.getMensagem() + "\n\n" +
               	"Status: " + mensagem.getStatus1() + "\n\n";
		
		try {
			EmailSender.enviarEmail(mensagem, conteudoEmail);
			JOptionPane.showMessageDialog(this, "E-mail enviado com sucesso para: " + mensagem.getEmail());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao enviar e-mail: " + e.getMessage());
			e.printStackTrace();
			}
		}
		
    
		
		private void sairDoFormulario() {
			dispose();
		}

		

}
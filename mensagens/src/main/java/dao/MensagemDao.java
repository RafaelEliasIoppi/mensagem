package dao;

import entidades.Destinatario;
import entidades.Mensagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import conector.Conexao;

public class MensagemDao {

    private Connection connection;

    public MensagemDao(Connection connection) {
        this.connection = connection;
    }

    public MensagemDao() throws Throwable {
        this.connection = Conexao.getConnection();
    }

	
    public Connection getConnection() {
        return this.connection;
    }

	
    public int cadastrarMensagem(Mensagem mensagem) {
        String sql = "INSERT INTO mensagem (remetente, setor, destinatario, nivel, data_envio, mensagem, email, status1, status2, observacoes)"
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, mensagem.getRemetente());
            stmt.setString(2, mensagem.getSetor());
            stmt.setString(3, mensagem.getDestinatario().getNome()); // Ajuste para salvar apenas o nome do destinatário
            stmt.setInt(4, mensagem.getDestinatario().getNivel());
            stmt.setString(5, mensagem.getDataEnvio());
            stmt.setString(6, mensagem.getMensagem());
            stmt.setString(7, mensagem.getDestinatario().getEmail());
            stmt.setString(8, mensagem.getStatus1());
            stmt.setString(9, mensagem.getStatus2());
            stmt.setString(10, mensagem.getObservacao());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Obtendo o ID gerado pelo banco de dados
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retorna o ID da mensagem cadastrada
                } else {
                    throw new SQLException("Falha ao obter o ID da mensagem cadastrada.");
                }
            } else {
                throw new SQLException("Nenhuma linha afetada ao cadastrar a mensagem.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar mensagem: " + e.getMessage());
            e.printStackTrace(); // Exibe a stack trace no console para debug
            return -1; // Retorna um valor indicativo de falha
        }
    }
    

   

   
    
    public Mensagem consultarMensagemPorId(int id) {
        String sql = "SELECT * FROM mensagem WHERE msg_id = ?";
        Mensagem mensagem = null;

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int msgId = rs.getInt("msg_id");
                String remetente = rs.getString("remetente");
                String setor = rs.getString("setor");
                String destinatarioNome = rs.getString("destinatario"); // Recupera o nome do destinatário do banco
                int nivel = rs.getInt("nivel");
                String dataEnvio = rs.getString("data_envio");
                String mensagemTexto = rs.getString("mensagem");
                String email = rs.getString("email");
                String status1 = rs.getString("status1");
                String status2 = rs.getString("status2");
                String observacao = rs.getString("observacao");

                // Cria um objeto Destinatario com o nome recuperado do banco
                Destinatario destinatario = new Destinatario(destinatarioNome);

                // Cria a instância de Mensagem usando o construtor apropriado
                mensagem = new Mensagem(msgId, remetente, setor, destinatario, nivel, dataEnvio, mensagemTexto, email, status1, status2, observacao);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar mensagem por ID: " + e.getMessage());
        }
        return mensagem;
    }

    public List<Mensagem> consultarMensagemPorRemetente(String remetente) {
        String sql = "SELECT * FROM mensagem WHERE remetente = ?";
        List<Mensagem> mensagens = new ArrayList<>();

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, remetente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int msgId = rs.getInt("msg_id");
                String setor = rs.getString("setor");
                String destinatarioNome = rs.getString("destinatario"); // Nome do destinatário como string
                Integer nivel = rs.getInt("nivel");
                String dataEnvio = rs.getString("data_envio");
                String mensagemTexto = rs.getString("mensagem");
                String email = rs.getString("email");
                String status1 = rs.getString("status1");
                String status2 = rs.getString("status2");
                String observacao = rs.getString("obersevacao");

                // Criar um objeto Destinatario com o nome recuperado do banco de dados
                Destinatario destinatario = new Destinatario(destinatarioNome);

                // Criar a mensagem com os dados recuperados
                Mensagem mensagem = new Mensagem(msgId, remetente, setor, destinatario,nivel, dataEnvio, mensagemTexto, email, status1, status2, observacao);
                mensagens.add(mensagem);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar mensagem por remetente: " + e.getMessage());
        }
        return mensagens;
    }

    public List<Mensagem> consultarTodasMensagens() {
        List<Mensagem> mensagens = new ArrayList<>();
        String sql = "SELECT * FROM mensagem";

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int msgId = rs.getInt("msg_id");
                String remetente = rs.getString("remetente");
                String setor = rs.getString("setor");
                String destinatarioNome = rs.getString("destinatario"); // Nome do destinatário como string
                Integer nivel = rs.getInt("nivel");
                String dataEnvio = rs.getString("data_envio");
                String mensagemTexto = rs.getString("mensagem");
                String email = rs.getString("email");
                String status1 = rs.getString("status1");
                String status2 = rs.getString("status2");
                String observacoes = rs.getString("observacoes");

                // Criar um objeto Destinatario com o nome recuperado do banco de dados
                Destinatario destinatario = new Destinatario(destinatarioNome);

                // Criar a mensagem com os dados recuperados
                Mensagem msg = new Mensagem(msgId, remetente, setor, destinatario,nivel, dataEnvio, mensagemTexto, email, status1, status2, observacoes);
                mensagens.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensagens;
    }
    
  
	public void excluirMensagem(int id) {
        String sql = "DELETE FROM mensagem WHERE msg_id = ?";

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Mensagem excluída com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Mensagem com o ID informado não encontrada.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir mensagem: " + e.getMessage());
            }
        }
	
	 public void atualizarStatus1(int mensagemId, String novoStatus) throws SQLException {
	        String sql = "UPDATE mensagem SET status1 = ? WHERE msg_id = ?";
	        try (Connection conn = this.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, novoStatus);
	            stmt.setInt(2, mensagemId);
	            stmt.executeUpdate();
	        }
	    }

	    public void atualizarStatus2(int mensagemId, String novoStatus) throws SQLException {
	        String sql = "UPDATE mensagem SET status2 = ? WHERE msg_id = ?";
	        try (Connection conn = this.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, novoStatus);
	            stmt.setInt(2, mensagemId);
	            stmt.executeUpdate();
	        }
	    }
	    
	    public void adicionarObservacao(int mensagemId, String observacao) throws SQLException {
	    	  String sql = "UPDATE mensagem SET observacoes = ? WHERE msg_id = ?";
	        
	        try (Connection conn = this.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	                     	          
	           
	            stmt.setString(1, observacao);
	            stmt.setInt(2, mensagemId);
	            
	            stmt.executeUpdate();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao adicionar observação: " + e.getMessage());
	        }
	    }
	    
    public int obterUltimoIdInserido(Connection connection) throws SQLException {
        String sql = "SELECT LAST_INSERT_ID()";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o último ID inserido.");
            }
        }
    }
}

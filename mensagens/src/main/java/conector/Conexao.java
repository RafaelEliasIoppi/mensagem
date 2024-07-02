package conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // URL de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/exmplobd";
    
    // Usuário do banco de dados
    private static final String USER = "root";
    
    // Senha do banco de dados
    private static final String PASSWORD = "1234";

    /**
     * Método para obter uma conexão com o banco de dados.
     * 
     * @return A conexão com o banco de dados.
     * @throws SQLException Se houver um erro ao conectar-se ao banco de dados.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Carregar o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro ao carregar o driver JDBC", e);
        }
        
        // Retornar a conexão
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Método para fechar uma conexão com o banco de dados.
     * 
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	public static Connection getConexao() {
		// TODO Auto-generated method stub
		return null;
	}
}
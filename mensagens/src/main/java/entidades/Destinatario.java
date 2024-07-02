package entidades;

public class Destinatario {
	private int id;
    private String nome;
    private int nivel;
    private String email;

    // Construtor com todos os campos
    public Destinatario(int id, String nome, int nivel, String email) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.email = email;
    }

    // Construtor sem o ID (para inserções, onde o ID é gerado pelo banco)
    public Destinatario(String nome, int nivel, String email) {
        this.nome = nome;
        this.nivel = nivel;
        this.email = email;
    }
    
    public Destinatario() {
    	
    }

	public Destinatario(String nome) {
		this.nome = nome;
	}

    // Getters e Setters para os campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Sobrescrita do método toString para facilitar a exibição dos dados
    @Override
    public String toString() {
        return "Destinatario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nivel=" + nivel +
                ", email='" + email + '\'' +
                '}';
    }
}
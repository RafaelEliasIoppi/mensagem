package entidades;

import java.util.List;
import java.util.ArrayList;
import entidades.Destinatario;

public class Mensagem {
	 private int msgId;
	    private String remetente;
	    private String setor;
	    private Destinatario destinatario;
	    private int nivel;
	    private String dataEnvio;
	    private String mensagem;
	    private String email;
	    private String status1;
	    private String status2;
	    private String observacao;

	    // Construtor com todos os campos
	    public Mensagem(int msgId, String remetente, String setor, Destinatario destinatario, int nivel, String dataEnvio,
	                    String mensagem, String email, String status1, String status2, String observacao) {
	        this.msgId = msgId;
	        this.remetente = remetente;
	        this.setor = setor;
	        this.destinatario = destinatario;
	        this.nivel = nivel;
	        this.dataEnvio = dataEnvio;
	        this.mensagem = mensagem;
	        this.email = email;
	        this.status1 = status1;
	        this.status2 = status2;
	        this.observacao = observacao;
	    }

	    // Construtor sem o ID (para inserção de nova mensagem)
	    public Mensagem(String remetente, String setor, Destinatario destinatario, int nivel, String dataEnvio,
	                    String mensagem, String email, String status1, String status2, String observacao) {
	        this.remetente = remetente;
	        this.setor = setor;
	        this.destinatario = destinatario;
	        this.nivel = nivel;
	        this.dataEnvio = dataEnvio;
	        this.mensagem = mensagem;
	        this.email = email;
	        this.status1 = status1;
	        this.status2 = status2;
	        this.observacao = observacao;
	    }
    
   public Mensagem () {
	   
   }

	public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

	public String getSetor() {
		return setor;
	}
	
	public void setSetor(String setor) {
		this.setor = setor;
	}
    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }
    
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
        this.nivel = nivel;
	}

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

	
}

package entity;

import java.util.List;

public class Contato {

	private int Id_contato;
	private String Nome;
	private String Fone;
	private String Email;
	
	public Contato() {
		super();
	}
	
	
	public Contato(int id_contato, String nome, String fone, String email) {
		super();
		Id_contato = id_contato;
		Nome = nome;
		Fone = fone;
		Email = email; 
	}

	public int getId_contato() {
		return Id_contato;
	}
	public void setId_contato(int i) {
		Id_contato = i;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getFone() {
		return Fone;
	}
	public void setFone(String fone) {
		Fone = fone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}

	public Contato encontrarContatos(int id, List<Contato> contatos) {
	    for (Contato contato : contatos) {
	        if (contato.getId_contato() == id) {
	            return contato;
	        }
	    }
	    return null;
	}
}

package br.com.wmw.vendafacil_frontend.domain;

public class Cliente {

	public long codigo;
	public String nome;
	
	public Cliente(long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Cliente() {
	}
	
	@Override
	public String toString() {
		return String.format("%s", nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return codigo == other.codigo;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}

package br.com.wmw.vendafacil_frontend.domain;

import java.util.Objects;

public class Cliente {

	private long codigo;
	private String nome;
	
	public Cliente(final long codigo, final String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Cliente() {
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, nome);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return codigo == other.codigo && Objects.equals(nome, other.nome);
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}
}

package br.com.biblioteca.livro;

public class Livro {
	
	private int id;
	private String tiulo;
	private String autor;
	private Formato formato;
	private Genero genero;
	private Idioma idioma;
	private int ano_de_publicacao;
	private String editora;
	
	public Livro() {
	}
	
	public Livro(String titulo, Genero genero) {
		this.tiulo = titulo;
		this.genero = genero;
	}
	public Livro(String titulo, String editora, Genero genero, int ano_de_publicacao, String autor, Formato formato, Idioma idioma) {
		this.tiulo = titulo;
		this.autor = autor;
		this.formato = formato;
		this.genero = genero;
		this.idioma = idioma;
		this.ano_de_publicacao = ano_de_publicacao;
		this.editora = editora;
	}
	public Livro(int id, String titulo, String editora, Genero genero, int ano_de_publicacao, String autor, Formato formato, Idioma idioma 
			) {
		this.id = id;
		this.tiulo = titulo;
		this.autor = autor;
		this.formato = formato;
		this.genero = genero;
		this.idioma = idioma;
		this.ano_de_publicacao = ano_de_publicacao;
		this.editora = editora;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return tiulo;
	}
	public void setTitulo(String tiulo) {
		this.tiulo = tiulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Formato getFormato() {
		return formato;
	}
	public void setFormato(Formato formato) {
		this.formato = formato;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	public Idioma getIdioma() {
		return idioma;
	}
	public void setIdioma(Idioma idioma) {
		this.idioma= idioma;
	}
	public int getAno_de_publicacao() {
		return ano_de_publicacao;
	}
	public void setAno_de_publicacao(int ano_de_publicacao) {
		this.ano_de_publicacao = ano_de_publicacao;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}

}

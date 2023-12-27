package br.com.biblioteca.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import br.com.biblioteca.exceptions.DigitoInvalido;
import br.com.biblioteca.exceptions.GeneroInvalido;
import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.livro.Formato;
import br.com.biblioteca.livro.Genero;
import br.com.biblioteca.livro.Idioma;
import br.com.biblioteca.livro.Livro;

public class Biblioteca {
	
	public void addBook(Livro livro) {
		String sql = "INSERT INTO livros (titulo, editora, genero, ano_de_publicacao, autor, formato, idioma)"
				+ " VALUES (?, ?, ?, ? ,?, ?, ?)";

		Connection connection = null;
		PreparedStatement pstm = null;

		try {
			connection = ConnectionFactory.createConnectionToMySql();
			pstm = connection.prepareStatement(sql);

			pstm.setString(1, livro.getTitulo());
			pstm.setString(2, livro.getEditora());
			pstm.setString(3, livro.getGenero().name());
			pstm.setInt(4, livro.getAno_de_publicacao());
			pstm.setString(5, livro.getAutor());
			pstm.setString(6, livro.getFormato().name());
			pstm.setString(7, livro.getIdioma().name());

			pstm.execute();

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (pstm != null) {
					pstm.close();
				}

			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public List<Livro> buscaLivro(String tituloDeBusca, Genero generoDeBusca) {
		String sql = "SELECT * FROM livros WHERE titulo LIKE ? OR genero LIKE ?";
		List<Livro> livros = new ArrayList<>();

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			connection = ConnectionFactory.createConnectionToMySql();
			pstm = connection.prepareStatement(sql);

			pstm.setString(1, "%" + tituloDeBusca + "%");
			pstm.setString(2, "%" + generoDeBusca.name() + "%");

			rset = pstm.executeQuery();
			while (rset.next()) {
				int id = rset.getInt("id");
				String titulo = rset.getString("titulo");
				String editora = rset.getString("editora");
				String generoLivro = rset.getString("genero");
				int ano = rset.getInt("ano_de_publicacao");
				String autor = rset.getString("autor");
				String formatoLivro = rset.getString("formato");
				String idiomaLivro = rset.getString("idioma");

				Genero generoEnum = Genero.valueOf(generoLivro);
				Formato formatoEnum = Formato.valueOf(formatoLivro);
				Idioma idiomaEnumLivro = Idioma.valueOf(idiomaLivro);

				Livro livro = new Livro(id,titulo, editora, generoEnum, ano, autor, formatoEnum, idiomaEnumLivro);

				livros.add(livro);

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (rset != null) {
					rset.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return livros;
	}
	
	
	public void editarLivro(Livro livro, String informacaoEditavel, int id) throws Exception  {
		
		String sql = "UPDATE livros SET " + informacaoEditavel + " = ? WHERE id = " + id;
		Connection connection = null;
		PreparedStatement pstm = null;
		
		try {
			connection = ConnectionFactory.createConnectionToMySql();
			pstm = connection.prepareStatement(sql);
			
			switch(informacaoEditavel) {
			case "titulo" :
				pstm.setString(1, livro.getTitulo());
				break;
			case "editora":
				pstm.setString(1, livro.getEditora());
				break;
			case "genero" :
				pstm.setString(1, livro.getGenero().toString());
				break;
			case "ano_de_publicacao" :
				pstm.setInt(1, livro.getAno_de_publicacao());
				break;
			case "autor":
				pstm.setString(1, livro.getAutor());
				break;
			case "formato":
				pstm.setString(1, livro.getFormato().toString());
				break;
			case "idioma":
				pstm.setString(1, livro.getIdioma().toString());
				break;
			}
			pstm.execute();
			
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public void excluirLivro(int id) throws Exception {
		String sql = "DELETE FROM livros WHERE id = ?";
		Connection connection = null;
		PreparedStatement pstm = null;
		
		try {
			connection = ConnectionFactory.createConnectionToMySql();
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, id);
			
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
				if(pstm != null) {
					pstm.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Livro> listaTodosOsLivros() {
		String sql = "SELECT * FROM livros";
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		List<Livro> livrosCadastrados = new ArrayList<>();
		
		try {
			connection = ConnectionFactory.createConnectionToMySql();
			pstm = connection.prepareStatement(sql);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				int id = rset.getInt("id");
				String titulo = rset.getString("titulo");
				String editora = rset.getString("editora");
				String generoLivro = rset.getString("genero");
				int ano = rset.getInt("ano_de_publicacao");
				String autor = rset.getString("autor");
				String formatoLivro = rset.getString("formato");
				String idiomaLivro = rset.getString("idioma");

				Genero generoEnum = Genero.valueOf(generoLivro);
				Formato formatoEnum = Formato.valueOf(formatoLivro);
				Idioma idiomaEnumLivro = Idioma.valueOf(idiomaLivro);
				
				Livro livro = new Livro(id, titulo, editora, generoEnum, ano, autor, formatoEnum, idiomaEnumLivro);
			
				livrosCadastrados.add(livro);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
				if(pstm != null) {
					pstm.close();
				}
				if(rset != null) {
					rset.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return livrosCadastrados;
	}

	public Genero selecionaGeneros(int escolha) {
		Genero genero = null;
		switch (escolha) {
		case 1:
			genero = Genero.ROMANCE;
			break;
		case 2:
			genero = Genero.FICÇÃO_CIENTÍFICA;
			break;
		case 3:
			genero = Genero.FANTASIA;
			break;
		case 4:
			genero = Genero.TERROR;
			break;
		case 5:
			genero = Genero.MISTERIO;
			break;
		case 6:
			genero = Genero.SUSPENSE;
			break;
		case 7:
			genero = Genero.DRAMA;
			break;
		case 8:
			genero = Genero.COMEDIA;
			break;
		default:
			genero = null;
			break;
		}
		return genero;
	}

	public Formato escolhaFormato(int escolha) {
		Formato formato = null;
		switch (escolha) {
		case 1:
			formato = Formato.FISICO;
			break;
		case 2:
			formato = Formato.DIGITAL;
			break;
		default:
			formato = null;
			break;
		}
		return formato;
	}

	public Idioma escolhaIdioma(int escolha) {
		Idioma idioma = null;
		switch (escolha) {
		case 1:
			idioma = Idioma.PORTUGUÊS;
			break;
		case 2:
			idioma = Idioma.INGLÊS;
			break;
		default:
			idioma = null;
		}
		return idioma;
	}

	public void encerraFormularioEntradaString(String comando) throws DigitoInvalido {
		if (comando.equals("0-")) {
			throw new DigitoInvalido();
		}
	}

	public void listaLivros(List<Livro> livrosEncontrados) {
		System.out.println("\nLivros Encontrados:\n");
		System.out.printf("%-12s %-40s %-20s %-15s %-20s %-15s %-15s %-15s\n", "[Id]", "[Título]", "[Autor]", "[Editora]",
				"[Ano de Publicação]", "[Idioma]", "[Gênero]", "[Formato]");
		for (Livro livrosEncontrado : livrosEncontrados) {
			System.out.printf(" %-12d %-40s %-20s %-15s %-20d %-15s %-15s %-15s\n", livrosEncontrado.getId(),livrosEncontrado.getTitulo(),
					livrosEncontrado.getAutor(), livrosEncontrado.getEditora(), livrosEncontrado.getAno_de_publicacao(),
					livrosEncontrado.getIdioma().toString(), livrosEncontrado.getGenero().toString(),
					livrosEncontrado.getFormato().toString());
		}
		System.out.println("");
	}
	
	public String EscolhaDeInformacaoEditavel(int EscolhaDeInformacaoEditavel) {
			String informacaoEditavel;
		switch(EscolhaDeInformacaoEditavel) {
		case 1 :
			informacaoEditavel = "titulo";
			break;
		case 2 :
			informacaoEditavel = "editora";
			break;
		case 3 :
			informacaoEditavel = "genero";
			break;
		case 4 :
			informacaoEditavel = "ano_de_publicacao";
			break;
		case 5 :
			informacaoEditavel = "autor";
			break;
		case 6 :
			informacaoEditavel = "formato";
			break;
		case 7 :
			informacaoEditavel = "idioma";
			break;
		default :
			informacaoEditavel = "Opção Invalida";
			break;
		}
		return informacaoEditavel;
	}
	
	public Idioma escolhaDeIdioma(int idiomaValido) {
		Idioma idiomaLivro = null;
		Scanner teclado = new Scanner(System.in);
		while (idiomaValido == 0) {
			if (idiomaValido == 0) {
				System.out.println("Qual Idioma do Livro:\n 1-(Português)\n 2-(Inglês)");
				int idioma = teclado.nextInt();

				idiomaLivro = escolhaIdioma(idioma);
				if (idiomaLivro == null) {
					idiomaValido = 0;
				} else {
					idiomaValido = 1;
				}
			}
		}
		return idiomaLivro;
	}
	public Genero escolhaDeGenero(int generoValido) {
		Genero generoLivro = null;
		Scanner teclado = new Scanner(System.in);
		while (generoValido == 0) {
			if (generoValido == 0) {
				System.out.println("Qual o genero do livro:\n 1-(Romance)\n 2-(Ficção-Científica)\n"
						+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
				int genero = teclado.nextInt();
				generoLivro = selecionaGeneros(genero);
			}
			if (generoLivro != null) {
				generoValido = 1;
			} else {
				System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
				generoValido = 0;
			}
		}
		return generoLivro;
	}
	
	@SuppressWarnings("resource")
	public Genero escolhaDeGeneroSemLoop(int generoValido) throws GeneroInvalido {
		Scanner teclado = new Scanner(System.in);
		Genero generoLivro = null;
		System.out.println("Qual o genero do livro:\n 1-(Romance)\n 2-(Ficção-Científica)\n"
				+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
		int genero = teclado.nextInt();
		// Metodo
		generoLivro = selecionaGeneros(genero);

		if (generoLivro == null) {
			System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
			generoValido = 0;
		throw new GeneroInvalido();
		} else {
			generoValido = 1;
		}
		return generoLivro;
	}
	
	public int escolhaDeAno(int anoValido) {
		int ano = 0;
		Scanner teclado = new Scanner(System.in);
		while (anoValido == 0) {
			if (anoValido == 0) {
				System.out.println("Digite o ano de publicação(1600 a Ano Atual)");
				 ano = teclado.nextInt();
				Calendar calendario = Calendar.getInstance();
				int anoAtual = calendario.get(Calendar.YEAR);
				if (ano <= anoAtual && ano >= 1600) {
					anoValido = 1;
				} else {
					System.out.println("[Ano Digitado é Invalido! Digite Novamente:]");
					anoValido = 0;
				}
			}
		}
		return ano;
	}
	
	public Formato escolhaDeFormato(int formatoValido) {
		Formato formatoLivro = null;
		Scanner teclado = new Scanner(System.in);
		while (formatoValido == 0) {
			if (formatoValido == 0) {
				System.out.println("Esolha Formato do Livro:\n 1-(Fisico)\n 2-(Digital)");
				int formato = teclado.nextInt();
				formatoLivro = escolhaFormato(formato);

				if (formatoLivro != null) {
					formatoValido = 1;
				} else {
					System.out.println("[Escolha da formato Invalida! Escolha Novamente:]");
					formatoValido = 0;
				}
			}
		}
		return formatoLivro;
	}
	
	public void verificaSeALivros(String titulo, Genero generoLivro) {
		List<Livro> livrosEncontrados = buscaLivro(titulo, generoLivro);
		if (!livrosEncontrados.isEmpty()) {
			listaLivros(livrosEncontrados);
			System.out.println("");
		} else {
			System.out.println("[Nenhum Livro Encontrado!]\n");
		}
	}
	public boolean verificaSeALivros(List<Livro> livrosEncontrados) {
		boolean listaVazia = false;
		if (!livrosEncontrados.isEmpty()) {
			listaLivros(livrosEncontrados);
			listaVazia = false;
			System.out.println("");
		} else {
			System.out.println("[Nenhum Livro Encontrado!]\n");
			listaVazia = true;
		}
		return listaVazia;
		
	}
	@SuppressWarnings("resource")
	public List<Livro> formularioDeBusca() {
		String titulo = "";
		Genero generoLivro = null;
		int generoValido = 0;
		List<Livro> livrosEncontrados = null;
		Scanner teclado = new Scanner(System.in);
		System.out.println("[AVISO!!]\nBusca sera cancelada caso digite (0-)"
				+ " nos campos de texto e qualquer caracter que não seja numero ou texto nos campos numericos\n");
		try {
			System.out.println("Digite O Titulo do Livro que procura:");
			titulo = teclado.nextLine();
			encerraFormularioEntradaString(titulo);

			System.out.println("Qual o genero do livro:\n 1-(Romance)\n 2-(Ficção-Científica)\n"
					+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
			int genero = teclado.nextInt();
			generoLivro = selecionaGeneros(genero);

			if (generoLivro == null) {
				System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
				generoValido = 0;
				throw new GeneroInvalido();
			} else {
				generoValido = 1;
			}
			// Metodo
			livrosEncontrados = buscaLivro(titulo, generoLivro);
			verificaSeALivros(livrosEncontrados);

		} catch (GeneroInvalido e) {
			// Metodo
			generoLivro = escolhaDeGenero(generoValido);
			// Metodo
			verificaSeALivros(titulo, generoLivro);
		} catch (DigitoInvalido | InputMismatchException e) {
			System.out.println("Busca Cancelada\n");
		}
		return livrosEncontrados;
	}
}

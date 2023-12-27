package br.com.biblioteca.aplicacao;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import br.com.biblioteca.exceptions.AnoInvalido;
import br.com.biblioteca.exceptions.DigitoInvalido;
import br.com.biblioteca.exceptions.FormatoInvalido;
import br.com.biblioteca.exceptions.GeneroInvalido;
import br.com.biblioteca.exceptions.IdiomaInvalido;
import br.com.biblioteca.livro.Formato;
import br.com.biblioteca.livro.Genero;
import br.com.biblioteca.livro.Idioma;
import br.com.biblioteca.livro.Livro;
import br.com.biblioteca.model.Biblioteca;

public class Aplicacao {

	public static void main(String[] args) throws Exception {
		Biblioteca biblioteca = new Biblioteca();

		int opcao = 0;
		Livro livro = null;
		while (true) {
			Scanner teclado = new Scanner(System.in);
			//Menu do programa
			System.out.println("Bem vindo a este gerenciador de biblioteca");

			System.out.println("1-Adicionar Livro");
			System.out.println("2-Buscar Livro");
			System.out.println("3-Editar Livro");
			System.out.println("4-Excluir Livro");
			System.out.println("5-Listar Livros");
			System.out.println("6-End Program");

			
			try {
				opcao = teclado.nextInt();
				teclado.nextLine();
				//Verificando se Entrada do usuario e valida e exibe mensagem de acordo
				if (opcao <= 0 || opcao > 6) {
					System.out.println("Opção Invalida! Digite Novamente");
					continue;
				}
			} catch (InputMismatchException exc) {
				System.out.println("Entrada Invalida! Digite Novamente:\n");
				continue;
			}
		
			if (opcao == 1) {
				String titulo = "";
				String autor = "";
				String editora = "";
				int idiomaValido = 0;
				int generoValido = 0;
				int anoValido = 0;
				int formatoValido = 0;
				int ano = 0;
				Idioma idiomaLivro = null;
				Genero generoLivro = null;
				Formato formatoLivro = null;

				System.out.println("[AVISO!!]\nCadastro sera cancelado caso digite (0-)"
						+ " nos campos de texto e qualquer caracter que não seja numero ou texto nos campos numericos\n");
				
				//Inicia o cadastro de um novo livro
				try {
					System.out.println("Digite o titulo do Livro");
					titulo = teclado.nextLine();
					biblioteca.encerraFormularioEntradaString(titulo);

					System.out.println("Digite nome do autor");
					autor = teclado.nextLine();
					biblioteca.encerraFormularioEntradaString(autor);

					System.out.println("Digite o nome da editora");
					editora = teclado.nextLine();
					biblioteca.encerraFormularioEntradaString(editora);

					System.out.println("Qual Idioma do Livro:\n 1-(Português)\n 2-(Inglês)");
					int idioma = teclado.nextInt();
					idiomaLivro = biblioteca.escolhaIdioma(idioma);

					if (idiomaLivro == null) {
						System.out.println("[Idioma Escolhido é Invalido! Escolha Novamente:]");
						idiomaValido = 0;
						throw new IdiomaInvalido();
					} else {
						idiomaValido = 1;
					}
					System.out.println("Qual o genero do livro:\n 1-(Romance)\n 2-(Ficção-Científica)\n"
							+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
					int genero = teclado.nextInt();
					generoLivro = biblioteca.selecionaGeneros(genero);

					if (generoLivro == null) {
						System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
						generoValido = 0;
						throw new GeneroInvalido();
					} else {
						generoValido = 1;
					}
					System.out.println("Digite o ano de publicação(1600 a Ano Atual)");
					ano = teclado.nextInt();
					Calendar calendario = Calendar.getInstance();
					int anoAtual = calendario.get(Calendar.YEAR);

					if (ano > anoAtual || ano < 1600) {
						System.out.println("[Ano Digitado é Invalido! Digite Novamente:]");
						anoValido = 0;
						throw new AnoInvalido();
					} else {
						anoValido = 1;
					}
					System.out.println("Esolha Formato do Livro:\n 1-(Fisico)\n 2-(Digital)");
					int formato = teclado.nextInt();

					formatoLivro = biblioteca.escolhaFormato(formato);

					if (biblioteca.escolhaFormato(formato) == null) {
						System.out.println("[Escolha da formato Invalida! Escolha Novamente:]");
						formatoValido = 0;
						throw new FormatoInvalido();
					} else {
						formatoValido = 1;
					}

					livro = new Livro(titulo, editora, generoLivro, ano, autor, formatoLivro, idiomaLivro);

					biblioteca.addBook(livro);
					System.out.println("Livro Adicionado Com Sucesso");

				} catch (GeneroInvalido | FormatoInvalido | AnoInvalido | IdiomaInvalido e) {
					idiomaLivro = biblioteca.escolhaDeIdioma(idiomaValido);
					generoLivro = biblioteca.escolhaDeGenero(generoValido);
					ano = biblioteca.escolhaDeAno(anoValido);
					formatoLivro = biblioteca.escolhaDeFormato(formatoValido);
					if (formatoLivro != null) {
						livro = new Livro(titulo, editora, generoLivro, ano, autor, formatoLivro, idiomaLivro);
						biblioteca.addBook(livro);
						System.out.println("Livro Adicionado Com Sucesso");
					}
				} catch (DigitoInvalido | InputMismatchException e) {
					System.out.println("Cadastro Cancelado\n");
					continue;
				}
			}
			if (opcao == 2) {
				biblioteca.formularioDeBusca();
			}
			if (opcao == 3) {
				int id = 0;
				String titulo = "";
				String autor = "";
				String editora = "";
				int idiomaValido = 1;
				int generoValido = 1;
				int anoValido = 1;
				int formatoValido = 1;
				int ano = 0;
				Idioma idiomaLivro = null;
				Genero generoLivro = null;
				Formato formatoLivro = null;
				int EscolhaDeInformacaoEditavel = 0;
				String informacaoEditavel = "";
				int opcaoInvalida = 1;
				List<Livro> livrosEncontrados = null;
				boolean listaVazia = false;

				try {
					System.out.println("Digite O Titulo do Livro que procura:");
					titulo = teclado.nextLine();
					biblioteca.encerraFormularioEntradaString(titulo);

					System.out.println("Qual o genero do livro:\n 1-(Romance)\n 2-(Ficção-Científica)\n"
							+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
					int genero = teclado.nextInt();
					// Metodo
					generoLivro = biblioteca.selecionaGeneros(genero);

					if (generoLivro == null) {
						System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
						generoValido = 0;
						throw new GeneroInvalido();
					} else {
						generoValido = 1;
					}
					
					livrosEncontrados = biblioteca.buscaLivro(titulo, generoLivro);
					listaVazia = biblioteca.verificaSeALivros(livrosEncontrados);
					
					if(listaVazia == true) {
						continue;
					}
				} catch (GeneroInvalido e) {
					// Metodo
					generoLivro = biblioteca.escolhaDeGenero(generoValido);
					livrosEncontrados = biblioteca.buscaLivro(titulo, generoLivro);
					listaVazia = biblioteca.verificaSeALivros(livrosEncontrados);
				
					if(listaVazia == true) {
						continue;
					}
				} catch (DigitoInvalido | InputMismatchException e) {
					System.out.println("Busca Cancelada\n");
					continue;
				}
				try {
					System.out.println("Qual Informação deseja Editar\n 1-(Titulo)\n 2-(Editora)\n 3-(Genero)\n "
							+ "4-(Ano)\n 5-(Autor)\n 6-(Formato)\n 7-(Idioma)");
					EscolhaDeInformacaoEditavel = teclado.nextInt();
					teclado.nextLine();
					informacaoEditavel = biblioteca.EscolhaDeInformacaoEditavel(EscolhaDeInformacaoEditavel);

					switch (informacaoEditavel) {
					case "titulo":
						System.out.println("Digite novo Valor de Titulo");
						titulo = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "editora":
						System.out.println("Digite novo Valor de Editora");
						editora = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "genero":
						System.out.println("Escolha novo genero: \n 1-(Romance)\n 2-(Ficção-Científica)\n"
								+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
						int genero = teclado.nextInt();
						generoLivro = biblioteca.selecionaGeneros(genero);

						if (generoLivro == null) {
							System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
							generoValido = 0;
							throw new GeneroInvalido();
						} else {
							generoValido = 1;
						}
						break;
					case "ano_de_publicacao":
						System.out.println("Digite o ano de publicação(1600 a Ano Atual)");
						ano = teclado.nextInt();
						Calendar calendario = Calendar.getInstance();
						int anoAtual = calendario.get(Calendar.YEAR);

						if (ano > anoAtual || ano < 1600) {
							System.out.println("[Ano Digitado é Invalido! Digite Novamente:]");
							anoValido = 0;
							throw new AnoInvalido();
						} else {
							anoValido = 1;
						}
						break;
					case "autor":
						System.out.println("Digite novo Valor de Autor");
						autor = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "formato":
						System.out.println("Esolha novo Formato do Livro:\n 1-(Fisico)\n 2-(Digital)");
						int formato = teclado.nextInt();

						formatoLivro = biblioteca.escolhaFormato(formato);

						if (biblioteca.escolhaFormato(formato) == null) {
							System.out.println("[Escolha da formato Invalida! Escolha Novamente:]");
							formatoValido = 0;
							throw new FormatoInvalido();
						} else {
							formatoValido = 1;
						}
						break;
					case "idioma":
						System.out.println("Escolha novo idioma do Livro:\n 1-(Português)\n 2-(Inglês)");
						int idioma = teclado.nextInt();
						idiomaLivro = biblioteca.escolhaIdioma(idioma);

						if (idiomaLivro == null) {
							System.out.println("[Idioma Escolhido é Invalido! Escolha Novamente:]");
							idiomaValido = 0;
							throw new IdiomaInvalido();
						} else {
							idiomaValido = 1;
						}
						break;
					default:
						System.out.println("Opção Invalida! Digite Novamente\n");
						opcaoInvalida = 0;
						throw new DigitoInvalido();
					}

					System.out.println("Confirme o Id do Livro pra altera as informações");
					id = teclado.nextInt();

					Livro livroASerAlterado = null;

					for (Livro livroEncontrado : livrosEncontrados) {
						if (livroEncontrado.getId() == id) {
							livroASerAlterado = livroEncontrado;
							break;
						}
					}
					if (livroASerAlterado != null) {
						switch (informacaoEditavel) {
						case "titulo":
							livroASerAlterado.setTitulo(titulo);
							System.out.println("Titulo Alterado com sucesso");
							break;
						case "editora":
							livroASerAlterado.setEditora(editora);
							System.out.println("Editora Alterada com sucesso");
							break;
						case "genero":
							livroASerAlterado.setGenero(generoLivro);
							System.out.println("Genero Alterado com sucesso");
							break;
						case "ano_de_publicacao":
							livroASerAlterado.setAno_de_publicacao(ano);
							System.out.println("Ano Alterado com sucesso");
							break;
						case "autor":
							livroASerAlterado.setAutor(autor);
							System.out.println("Autor Alterado com sucesso");
							break;
						case "formato":
							livroASerAlterado.setFormato(formatoLivro);
							System.out.println("Formato Alterado com sucesso");
							break;
						case "idioma":
							livroASerAlterado.setIdioma(idiomaLivro);
							System.out.println("Idioma Alterado com sucesso");
							break;
						}
					}
					biblioteca.editarLivro(livroASerAlterado, informacaoEditavel, id);

				} catch (DigitoInvalido | GeneroInvalido | FormatoInvalido | AnoInvalido | IdiomaInvalido e) {
					while (opcaoInvalida == 0) {
						if (opcaoInvalida == 0) {
							System.out.println("Qual Informação deseja Editar\n 1-(Titulo)\n 2-(Editora)\n 3-(Genero)\n "
							+ "4-(Ano)\n 5-(Autor)\n 6-(Formato)\n 7-(Idioma)");
							EscolhaDeInformacaoEditavel = teclado.nextInt();
							teclado.nextLine();
							informacaoEditavel = biblioteca.EscolhaDeInformacaoEditavel(EscolhaDeInformacaoEditavel);
							if (informacaoEditavel.equals("Opção Invalida")) {
								System.out.println("Opção Invalida!Digite Novamente\n");
								opcaoInvalida = 0;
							} else {
								opcaoInvalida = 1;
							}
						}
					}
					// Metodo
					idiomaLivro = biblioteca.escolhaDeIdioma(idiomaValido);
					// Metodo
					generoLivro = biblioteca.escolhaDeGenero(generoValido);
					// Metodo
					ano = biblioteca.escolhaDeAno(anoValido);
					// Metodo
					formatoLivro = biblioteca.escolhaDeFormato(formatoValido);

					switch (informacaoEditavel) {
					case "titulo":
						System.out.println("Digite novo Valor de Titulo");
						titulo = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "editora":
						System.out.println("Digite novo Valor de Editora");
						editora = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "genero":
						System.out.println("Escolha novo genero: \n 1-(Romance)\n 2-(Ficção-Científica)\n"
								+ " 3-(Fantasia)\n 4-(Terror)\n 5-(Mistério)\n 6-(Suspense)\n 7-(Drama)\n 8-(Comédia)");
						int genero = teclado.nextInt();
						generoLivro = biblioteca.selecionaGeneros(genero);

						if (generoLivro == null) {
							System.out.println("[Genero Escolhido é Invalido! Escolha novamente:]");
							generoValido = 0;
							throw new GeneroInvalido();
						} else {
							generoValido = 1;
						}
						break;
					case "ano_de_publicacao":
						System.out.println("Digite o ano de publicação(1600 a Ano Atual)");
						ano = teclado.nextInt();
						Calendar calendario = Calendar.getInstance();
						int anoAtual = calendario.get(Calendar.YEAR);

						if (ano > anoAtual || ano < 1600) {
							System.out.println("[Ano Digitado é Invalido! Digite Novamente:]");
							anoValido = 0;
							throw new AnoInvalido();
						} else {
							anoValido = 1;
						}
						break;
					case "autor":
						System.out.println("Digite novo Valor de Autor");
						autor = teclado.nextLine();
						biblioteca.encerraFormularioEntradaString(titulo);
						break;
					case "formato":
						System.out.println("Esolha novo Formato do Livro:\n 1-(Fisico)\n 2-(Digital)");
						int formato = teclado.nextInt();

						formatoLivro = biblioteca.escolhaFormato(formato);

						if (biblioteca.escolhaFormato(formato) == null) {
							System.out.println("[Escolha da formato Invalida! Escolha Novamente:]");
							formatoValido = 0;
							throw new FormatoInvalido();
						} else {
							formatoValido = 1;
						}
						break;
					case "idioma":
						System.out.println("Escolha novo idioma do Livro:\n 1-(Português)\n 2-(Inglês)");
						int idioma = teclado.nextInt();
						idiomaLivro = biblioteca.escolhaIdioma(idioma);

						if (idiomaLivro == null) {
							System.out.println("[Idioma Escolhido é Invalido! Escolha Novamente:]");
							idiomaValido = 0;
							throw new IdiomaInvalido();
						} else {
							idiomaValido = 1;
						}
						break;
					default:
						System.out.println("Opção Invalida! Digite Novamente\n");
						opcaoInvalida = 0;
						throw new DigitoInvalido();
					}

					System.out.println("Confirme o Id do Livro pra altera as informações");
					id = teclado.nextInt();

					Livro livroASerAlterado = null;

					for (Livro livroEncontrado : livrosEncontrados) {
						if (livroEncontrado.getId() == id) {
							livroASerAlterado = livroEncontrado;
							break;
						}
					}
					if (livroASerAlterado != null) {
						switch (informacaoEditavel) {
						case "titulo":
							livroASerAlterado.setTitulo(titulo);
							System.out.println("Titulo Alterado com sucesso");
							break;
						case "editora":
							livroASerAlterado.setEditora(editora);
							System.out.println("Editora Alterada com sucesso");
							break;
						case "genero":
							livroASerAlterado.setGenero(generoLivro);
							System.out.println("Genero Alterado com sucesso");
							break;
						case "ano_de_publicacao":
							livroASerAlterado.setAno_de_publicacao(ano);
							System.out.println("Ano Alterado com sucesso");
							break;
						case "autor":
							livroASerAlterado.setAutor(autor);
							System.out.println("Autor Alterado com sucesso");
							break;
						case "formato":
							livroASerAlterado.setFormato(formatoLivro);
							System.out.println("Formato Alterado com sucesso");
							break;
						case "idioma":
							livroASerAlterado.setIdioma(idiomaLivro);
							System.out.println("Idioma Alterado com sucesso");
							break;
						}
					}
					biblioteca.editarLivro(livroASerAlterado, informacaoEditavel, id);

				} catch (InputMismatchException e) {
					System.out.println("Cadastro Cancelado\n");
					continue;
				} catch (NullPointerException e) {
					System.out.println("Id incorreto");
					continue;
				}
			}
			if (opcao == 4) {
				int id = 0;
				List<Livro> livrosEncontrados = biblioteca.formularioDeBusca();
				try {
				System.out.println("Confirme o id do livro que deseja excluir");
				id = teclado.nextInt();
				

				for (Livro livroEncontrado : livrosEncontrados) {
					if (livroEncontrado.getId() == id) {
						biblioteca.excluirLivro(id);
						System.out.println("Livro Foi Excluido com sucesso");
						break;
					}
				}
				
				}catch(NullPointerException e) {
					System.out.println("Id Incorreto");
					continue;
				}
			}
			if(opcao == 5) {
				biblioteca.listaLivros(biblioteca.listaTodosOsLivros());
			}
			
			if(opcao == 6) {
				break;
			}
		}
	}
}

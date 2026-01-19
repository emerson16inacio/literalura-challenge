package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosAutor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.DadosResultado;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import java.util.InputMismatchException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository repositorio;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository repositorio, AutorRepository autorRepositorio) {
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    --------------------------------
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                    0 - Sair
                    --------------------------------
                    Escolha o número da sua opção:
                    """;

            System.out.println(menu);

            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); // Consome a quebra de linha pendente
            } catch (InputMismatchException e) {
                System.out.println("\n*********************************************************");
                System.out.println("  Opção Inválida! Por favor, digite apenas números inteiros.");
                System.out.println("*********************************************************\n");
                leitura.nextLine(); // IMPORTANTE: Limpa o buffer do scanner para não entrar em loop infinito
                opcao = -1; // Define um valor inválido para reiniciar o loop
                continue; // Volta para o início do while (mostra o menu de novo)
            }

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        var dados = conversor.obterDados(json, DadosResultado.class);

        if (dados.livros() != null && !dados.livros().isEmpty()) {
            DadosLivro dadosLivro = dados.livros().get(0);
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            // PASSO 1: Verificar se o livro já existe no banco
            if (repositorio.existsByTitulo(dadosLivro.titulo())) {
                System.out.println("***********************************************");
                System.out.println("  ALERTA: Este livro já está cadastrado no banco!  ");
                System.out.println("***********************************************");
                // Encerra este método aqui, sem tentar salvar de novo
                return;
            }

            // PASSO 2: Se não existe, segue a lógica normal de salvar autor e livro
            Autor autor = autorRepositorio.findByNome(dadosAutor.nome())
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor(dadosAutor);
                        return autorRepositorio.save(novoAutor);
                    });

            Livro livro = new Livro(dadosLivro, autor);
            repositorio.save(livro);
            System.out.println("Livro salvo com sucesso!\n" + livro);

        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = repositorio.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepositorio.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autores = autorRepositorio.obterAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo no ano " + ano);
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idioma = leitura.nextLine();
        List<Livro> livros = repositorio.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        } else {
            livros.forEach(System.out::println);
        }
    }
}
package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idioma;
    private Double numeroDownloads;

    // Relacionamento: Muitos Livros pertencem a um Autor
    @ManyToOne
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        // A API retorna uma lista de idiomas, pegamos o primeiro ou null
        this.idioma = !dadosLivro.idiomas().isEmpty() ? dadosLivro.idiomas().get(0) : null;
        this.numeroDownloads = dadosLivro.numeroDownloads();
        this.autor = autor;
    }

    // Getters, Setters e toString
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Double getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDownloads(Double numeroDownloads) { this.numeroDownloads = numeroDownloads; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return  "----- LIVRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNome() : "Desconhecido") + "\n" +
                // ADICIONADO: Exibe nascimento e falecimento pegando do objeto Autor
                "Ano Nascimento Autor: " + (autor != null ? autor.getAnoNascimento() : "N/A") + "\n" +
                "Ano Falecimento Autor: " + (autor != null ? autor.getAnoFalecimento() : "N/A") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Downloads: " + numeroDownloads + "\n" +
                "-----------------";
    }
}
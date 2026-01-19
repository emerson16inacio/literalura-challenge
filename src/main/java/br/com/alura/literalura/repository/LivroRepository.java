package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Derived Query: O Spring cria o SQL automaticamente pelo nome do método
    List<Livro> findByIdioma(String idioma);

    boolean existsByTitulo(String titulo);
}
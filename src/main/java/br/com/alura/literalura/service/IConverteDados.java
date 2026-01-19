package br.com.alura.literalura.service;

public interface IConverteDados {
    // Atenção: O método é genérico <T> T
    <T> T obterDados(String json, Class<T> classe);
}
package br.com.reatividade.Aula.Alura.model.entity;

import java.util.List;

public record Traducao(List<Translation> translations) {

    public String textTransleted(){
        return this.translations.getFirst().text();
    }
}

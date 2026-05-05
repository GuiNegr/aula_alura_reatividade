package br.com.reatividade.Aula.Alura.model.enums;

public enum LANGUAGE {
    AF("AF"),
    SQ("SQ"),
    AR("AR"),
    HY("HY"),
    BA("BA"),
    EN("EN"),
    EN_US("EN-US"),
    KO("KO"),
    JA("JA"),
    PT_BR("PT-BR"),
    PT_PT("PT-PT");


    private String nome;

    LANGUAGE(String language){
        this.nome = language;
    }

    public String getLanguage() {
        return this.nome;
    }
}

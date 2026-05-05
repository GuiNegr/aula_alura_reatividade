package br.com.reatividade.Aula.Alura.model.entity.dto;

import br.com.reatividade.Aula.Alura.model.entity.Evento;
import br.com.reatividade.Aula.Alura.model.enums.TipoEvento;

import java.time.LocalDate;

public record EventoDto
        (Long id,
                        TipoEvento tipo,
                        String nome,
                        LocalDate data,
                        String descricao) {

    public static EventoDto toDTO(Evento evento){
        return new
                EventoDto
                (evento.getId(),evento.getTipo(),evento.getNome(),evento.getData(), evento.getDescricao());
    }

    public Evento toEnttity(){
        Evento evento = new Evento();
        evento.setData(this.data);
        evento.setDescricao(this.descricao);
        evento.setNome(this.nome);
        evento.setTipo(this.tipo);
        return evento;
    }
}

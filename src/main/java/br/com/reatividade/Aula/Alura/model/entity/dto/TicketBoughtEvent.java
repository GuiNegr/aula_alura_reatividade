package br.com.reatividade.Aula.Alura.model.entity.dto;


public record TicketBoughtEvent(BuyLogDTO data) implements TicketEvent {

}

package br.com.reatividade.Aula.Alura.model.entity.dto;


public sealed interface TicketEvent permits TicketBoughtEvent, TicketUpdatedEvent {

}

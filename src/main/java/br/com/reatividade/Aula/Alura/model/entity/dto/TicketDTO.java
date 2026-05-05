package br.com.reatividade.Aula.Alura.model.entity.dto;

import br.com.reatividade.Aula.Alura.model.entity.Tickets;

import java.time.LocalDate;

public record TicketDTO (
        Long id,
        Long id_eventos,
        LocalDate created_date,
        LocalDate update_date,
        int qtd_remain
) {
    public static TicketDTO toDTO(Tickets tickets){
        return new TicketDTO(tickets.getId(),tickets.getIdEventos(),tickets.getCreated_date(),tickets.getUpdated_date(),tickets.getQtd_remain());
    }
    public Tickets toEntity(){
        Tickets tickets = new Tickets();
        tickets.setId(this.id);
        tickets.setCreated_date(this.created_date);
        tickets.setIdEventos(this.id_eventos);
        tickets.setUpdated_date(this.update_date);
        tickets.setQtd_remain(this.qtd_remain);
        return tickets;
    }

}

package br.com.reatividade.Aula.Alura.model.entity.dto;

import br.com.reatividade.Aula.Alura.model.entity.BuyLog;

import java.time.LocalDateTime;

public record BuyLogDTO(
        Long id,
        Long id_eventos,
        Long id_tickets,
        LocalDateTime created_date,
        LocalDateTime update_date
) {
    public static BuyLogDTO toDto(BuyLog buyLog){
        return new BuyLogDTO(buyLog.getId(),buyLog.getIdEventos(),buyLog.getIdTickets(),buyLog.getCreated_date(),buyLog.getUpdated_date());
    }


    public BuyLog toEntity(){
        BuyLog buyLog = new BuyLog();
        buyLog.setId(this.id);
        buyLog.setIdEventos(this.id);
        buyLog.setIdTickets(this.id_tickets);
        buyLog.setCreated_date(this.created_date);
        buyLog.setUpdated_date(this.update_date);
        return buyLog;
    }

}

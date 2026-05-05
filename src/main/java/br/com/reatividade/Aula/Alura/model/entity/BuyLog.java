package br.com.reatividade.Aula.Alura.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("buylog")
public class BuyLog {

    @Id
    private Long id;

    @Column("id_eventos")
    private Long idEventos;

    @Column("id_tickets")
    private Long idTickets;

    @CreatedDate
    private LocalDateTime created_date;

    @LastModifiedDate
    private LocalDateTime updated_date;


}

package br.com.reatividade.Aula.Alura.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("tickets")
@Setter
@Getter
@NoArgsConstructor
public class Tickets {

    @Id
    private Long id;


    @Column("id_eventos")
    private Long idEventos;

    @CreatedDate
    private LocalDate created_date;

    @LastModifiedDate
    private LocalDate updated_date;

    private int qtd_remain;

    public Tickets(int qtd_remain, Long idEventos,LocalDate date) {
        this.qtd_remain = qtd_remain;
        this.idEventos = idEventos;
        this.created_date = date;
    }
}

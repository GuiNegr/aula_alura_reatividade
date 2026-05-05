package br.com.reatividade.Aula.Alura.model.service;

import br.com.reatividade.Aula.Alura.model.entity.BuyLog;
import br.com.reatividade.Aula.Alura.model.entity.Tickets;
import br.com.reatividade.Aula.Alura.model.entity.dto.BuyLogDTO;
import br.com.reatividade.Aula.Alura.model.entity.dto.TicketDTO;
import br.com.reatividade.Aula.Alura.model.repository.BuyLogRepository;
import br.com.reatividade.Aula.Alura.model.repository.EventoRepository;
import br.com.reatividade.Aula.Alura.model.repository.TicketsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class TicketsService {

    @Autowired
    private BuyLogRepository buyLogRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public Mono<BuyLogDTO> comprarTicket(Long idEvento){
        return eventoRepository.findById(idEvento)
                .switchIfEmpty(Mono.error(new RuntimeException("Evento não encontrado")))
                .flatMap(evento ->
                        ticketsRepository.findByIdEventos(evento.getId())
                                .flatMap(ticket -> {
                                    ticket.setQtd_remain(ticket.getQtd_remain() - 1);
                                    ticket.setUpdated_date(LocalDate.now());
                                    return ticketsRepository.save(ticket);
                                })
                                .switchIfEmpty(
                                        ticketsRepository.save(
                                                new Tickets( 99, evento.getId(),LocalDate.now())
                                        )
                                )
                )
                .flatMap(ticket -> {
                    BuyLog buyLog = new BuyLog();
                    buyLog.setIdEventos(idEvento);
                    buyLog.setIdTickets(ticket.getId());
                    buyLog.setCreated_date(LocalDate.from(LocalDateTime.now()).atStartOfDay());
                    return buyLogRepository.save(buyLog);
                }).map(BuyLogDTO::toDto);
    }

    public Flux<TicketDTO> returnTickets(){
        return ticketsRepository.findAll().map(TicketDTO::toDTO);
    }
}

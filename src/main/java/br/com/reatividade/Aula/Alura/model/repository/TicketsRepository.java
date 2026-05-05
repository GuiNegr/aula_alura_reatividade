package br.com.reatividade.Aula.Alura.model.repository;

import br.com.reatividade.Aula.Alura.model.entity.Tickets;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TicketsRepository extends ReactiveCrudRepository<Tickets,Long> {

    Mono<Tickets> findByIdEventos(Long id);
}

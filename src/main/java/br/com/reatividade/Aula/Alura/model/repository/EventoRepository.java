package br.com.reatividade.Aula.Alura.model.repository;

import br.com.reatividade.Aula.Alura.model.entity.Evento;
import br.com.reatividade.Aula.Alura.model.enums.TipoEvento;
import io.netty.util.AsyncMapping;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento,Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);
}

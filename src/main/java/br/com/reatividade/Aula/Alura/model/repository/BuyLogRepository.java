package br.com.reatividade.Aula.Alura.model.repository;

import br.com.reatividade.Aula.Alura.model.entity.BuyLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BuyLogRepository extends ReactiveCrudRepository<BuyLog,Long> {
}

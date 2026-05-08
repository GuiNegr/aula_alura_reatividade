package br.com.reatividade.Aula.Alura.model.service;

import br.com.reatividade.Aula.Alura.model.entity.Tickets;
import br.com.reatividade.Aula.Alura.model.enums.TipoEvento;
import br.com.reatividade.Aula.Alura.model.repository.EventoRepository;
import br.com.reatividade.Aula.Alura.model.entity.dto.EventoDto;
import br.com.reatividade.Aula.Alura.model.repository.TicketsRepository;
import br.com.reatividade.Aula.Alura.model.utils.TranslateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@Slf4j
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TranslateUtils utils;


    public Flux<EventoDto> gettAll(){
        //estudar sobre esse map e como funciona
        return repository.findAll()
                .map(EventoDto::toDTO);
    }

    public Mono<EventoDto> getByID(Long id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDTO);
    }

    public Mono<EventoDto> create(EventoDto eventoDto) {
        return repository.save(eventoDto.toEnttity())
                .flatMap(eventoSalvo -> {

                    Tickets ticket = new Tickets();
                    ticket.setQtd_remain(100);
                    ticket.setIdEventos(eventoSalvo.getId());
                    EventoService.log.info("Created Ticket!");
                    EventoService.log.info("Created Event!");
                    ticket.setCreated_date(LocalDate.from(LocalDateTime.now()));
                    ticket.setUpdated_date(LocalDate.from(LocalDateTime.now()));

                    return ticketsRepository.save(ticket)
                            .thenReturn(eventoSalvo);
                })

                .map(EventoDto::toDTO);
    }

    public Mono<Void> exclude(Long id) {
        return repository.findById(id).flatMap(repository::delete);
    }

    public Mono<EventoDto> update(EventoDto eventoDto,Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))).flatMap(evento -> {
                    if(eventoDto.nome() != null && !eventoDto.nome().isBlank()){
                        evento.setNome(eventoDto.nome());
                    };
                    if(eventoDto.data() != null){
                        evento.setData(eventoDto.data());
                    };
                    if(eventoDto.tipo() != null){
                        evento.setTipo(eventoDto.tipo());
                    }
                    if(eventoDto.descricao() != null && !eventoDto.descricao().isBlank()){
                        evento.setDescricao(eventoDto.descricao());
                    }
                    EventoService.log.info("Update has sucessful!");
                return repository.save(evento);
        }
        ).map(EventoDto::toDTO);

    }

    public Mono<String> translateEvento(Long id, String idioma){
        EventoService.log.info("Search for"+idioma +" and "+id+ " id");
         return repository.findById(id)
                 .doOnNext(evento -> {
                     EventoService.log.info(evento == null ? "evento não encontrado": "Evento encontrado!"+ evento.getNome()) ;
                 })
                .map(EventoDto::toDTO)
                .flatMap(dto -> utils.obterTraducao(dto, idioma));
    }

    public Flux<EventoDto> getByTipe(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase(Locale.ROOT));
        return repository.findByTipo(tipoEvento).map(EventoDto::toDTO);

    }
}

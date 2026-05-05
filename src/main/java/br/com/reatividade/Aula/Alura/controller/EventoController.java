package br.com.reatividade.Aula.Alura.controller;

import br.com.reatividade.Aula.Alura.model.entity.dto.EventoDto;
import br.com.reatividade.Aula.Alura.model.service.EventoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
@Slf4j
public class EventoController {

    private EventoService serviceEvento;
    private final Sinks.Many<EventoDto> eventoSink;

    public EventoController(EventoService serviceEvento) {
        this.serviceEvento = serviceEvento;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    public Flux<EventoDto> getAllObjects(){
        EventoController.log.info("Getting all information");
        return serviceEvento.gettAll();
    }

    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo){
            EventoController.log.info("search using  type");
            return Flux.merge(serviceEvento.getByTipe(tipo),eventoSink.asFlux()).delayElements(Duration.ofSeconds(4));
    }


    @GetMapping("/{id}")
    public Mono<EventoDto> getOneObject(@PathVariable Long id){
        EventoController.log.info("Getting a single object");
        return serviceEvento.getByID(id);
    }

    @PostMapping
    public Mono<EventoDto> create(@RequestBody EventoDto eventoDto){
        EventoController.log.info("Create a new evento");
        return serviceEvento.create(eventoDto).doOnSuccess(eventoSink::tryEmitNext);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> exclude(@PathVariable Long id){
        EventoController.log.info("Deleting a evento");
        return serviceEvento.exclude(id);
    }

    @PatchMapping("/{id}")
    public Mono<EventoDto> update(@RequestBody EventoDto eventoDto, @PathVariable Long id){
        EventoController.log.info("Updating a evento");
        return serviceEvento.update(eventoDto,id);
    }

    @GetMapping(value = "/traduzir/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> searchForTranslate(@PathVariable Long id, @RequestHeader String idioma){
        EventoController.log.info("Search for translate!");
        return serviceEvento.translateEvento(id,idioma);
    }
}

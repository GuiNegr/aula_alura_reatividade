    package br.com.reatividade.Aula.Alura.controller;


    import br.com.reatividade.Aula.Alura.model.entity.dto.TicketBoughtEvent;
    import br.com.reatividade.Aula.Alura.model.entity.dto.TicketEvent;
    import br.com.reatividade.Aula.Alura.model.entity.dto.TicketUpdatedEvent;
    import br.com.reatividade.Aula.Alura.model.entity.dto.BuyLogDTO;
    import br.com.reatividade.Aula.Alura.model.service.TicketsService;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.MediaType;
    import org.springframework.web.bind.annotation.*;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Mono;
    import reactor.core.publisher.Sinks;


    @RestController
    @RequestMapping("/tickets")
    @Slf4j
    public class TicketsController {

        private final Sinks.Many<TicketEvent> eventoSink;
       TicketsService ticketsService;

        public TicketsController(TicketsService ticketsService) {
            this.ticketsService = ticketsService;
            this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
        }

        @PostMapping("/{id}")
        public Mono<BuyLogDTO> buyATicket(@PathVariable  Long id){
            TicketsController.log.info("Buying a ticket!");

            return ticketsService.comprarTicket(id).doOnSuccess(buyLog ->
                    eventoSink.tryEmitNext(new TicketBoughtEvent(buyLog))
            );
        }

        @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<TicketEvent> checkTickets(){
            TicketsController.log.info("Cheking informations about tickets....");
            return Flux.merge(
                    ticketsService.returnTickets().map(TicketUpdatedEvent::new),
                    eventoSink.asFlux()
            );
        }




    }

package br.com.reatividade.Aula.Alura;

import br.com.reatividade.Aula.Alura.integration.AbstractIntegrationTest;
import br.com.reatividade.Aula.Alura.model.entity.dto.EventoDto;
import br.com.reatividade.Aula.Alura.model.enums.TipoEvento;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//subindo os testes do spring boot, em qualquer portas....
//DIFERENÇAS ENTRE USAR MOCK E usar um h2 em memory...
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers

class AulaAluraApplicationTests extends AbstractIntegrationTest {




	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testeCadastroEvento(){
		EventoDto eventoDto = new EventoDto(null, TipoEvento.SHOW,"Show do kendrick Lamar", LocalDate.now(),"OS RIVAIS VÃO DANÇAR!");
		webTestClient.post().uri("/eventos")
				.bodyValue(eventoDto)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(EventoDto.class)
				.value(response ->{
					assertNotNull(response.id());
					assertEquals(eventoDto.tipo(),response.tipo());
					assertEquals(eventoDto.descricao(),response.descricao());
				});


	}


	@Test
	void buscarEvento(){
		EventoDto eventoDto = new EventoDto(5L, TipoEvento.WORKSHOP,"Workshop de Fotografia", LocalDate.parse("2024-11-30"),"Um workshop prático para aprimorar suas habilidades");

		webTestClient.get().uri("/eventos")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBodyList(EventoDto.class)
				.value(response ->{
					assertEquals(response.get(4).id(), eventoDto.id());
				});
	}

}

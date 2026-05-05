package br.com.reatividade.Aula.Alura.model.utils;

import br.com.reatividade.Aula.Alura.model.entity.Traducao;
import br.com.reatividade.Aula.Alura.model.entity.dto.EventoDto;
import br.com.reatividade.Aula.Alura.model.enums.LANGUAGE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


@Component
public class TranslateUtils {

    @Value("${spring.deepl.api_kei}")
    private  String apikey;

    public  Mono<String> obterTraducao(EventoDto evento, String idioma){
        WebClient webclient = WebClient.builder().baseUrl("https://api-free.deepl.com/v2/translate")
                .build();

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();


        req.add("text",evento.descricao());
        req.add("target_lang", LANGUAGE.valueOf(idioma).getLanguage());

        String apiDeep = "DeepL-Auth-Key " + apikey;

        return webclient.post()
                .header("Authorization", "DeepL-Auth-Key " + apikey)
                .body(BodyInserters.fromFormData(req))
                .retrieve().bodyToMono(Traducao.class)
                .map(Traducao::textTransleted).onErrorMap(error ->
                new Exception(error.getLocalizedMessage())
        );
    }


}

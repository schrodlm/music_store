package cz.cvut.tjv.music_store_client.client;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;

@Component
public class ProductClient {
    private WebTarget productsEndpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget activeProductsEndpoint;

    public ProductClient(@Value("${server.url}") String serverUrl) {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        productsEndpoint = client.target(serverUrl + "/products");
        singleTemplateEndpoint = productsEndpoint.path("/{id}");
    }

    public void setActivePost(long id) {
        activeProductsEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);
    }

    public ProductDto create(ProductDto post) {
        return productsEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(post, MediaType.APPLICATION_JSON_TYPE), ProductDto.class);
    }

    public Optional<ProductDto> readOne() {
        var response = activeProductsEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return Optional.empty();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(ProductDto.class));
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public Collection<ProductDto> readAll()
    {
        return Arrays.asList(productsEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get(ProductDto[].class));
    }
}

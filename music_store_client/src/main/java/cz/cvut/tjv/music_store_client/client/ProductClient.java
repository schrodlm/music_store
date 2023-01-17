package cz.cvut.tjv.music_store_client.client;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import jakarta.ws.rs.BadRequestException;
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
    private WebTarget activeProductEndpoint;

    private WebTarget favouriteProductsEndpoint;


    public ProductClient(@Value("${server.url}") String serverUrl) {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        productsEndpoint = client.target(serverUrl + "/products");
        singleTemplateEndpoint = productsEndpoint.path("/{id}");
        favouriteProductsEndpoint = productsEndpoint.path("/{userId}/liked");


    }

    public void setActiveProduct(long id) {
        activeProductEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);
    }

    public ProductDto create(ProductDto productDto) {
        return productsEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(productDto, MediaType.APPLICATION_JSON_TYPE), ProductDto.class);
    }

    public Optional<ProductDto> readOne() {
        var response = activeProductEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
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

    public Collection<ProductDto> readAllFavourites(long loggedUserId)
    {
        activeProductEndpoint = favouriteProductsEndpoint.resolveTemplate("userId",loggedUserId);
        return Arrays.asList(activeProductEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get(ProductDto[].class));
    }


    public void updateOne(ProductDto productDto)
    {
        var response = activeProductEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(productDto, MediaType.APPLICATION_JSON_TYPE));

        if(response.getStatus() > 200)
            throw new BadRequestException(response.getStatusInfo().getReasonPhrase());

    }

    public void delete()
    {
        var response = activeProductEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();

        if(response.getStatus() > 200)
            throw new BadRequestException(response.getStatusInfo().getReasonPhrase());
    }

}

package cz.cvut.tjv.music_store_client.client;

import cz.cvut.tjv.music_store_client.dto.OrderDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;

@Component
public class OrderClient {

    private WebTarget orderEndpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget activeOrderEndpoint;

    public OrderClient(@Value("${server.url}") String serverUrl){
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        orderEndpoint = client.target(serverUrl + "/orders");
        singleTemplateEndpoint = orderEndpoint.path("/{id}");

    }

    public void setActiveOrder(long id) {activeOrderEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);}

    public Optional<OrderDto> readOne()
    {
        var response = activeOrderEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();

        if(response.getStatus() == 404)
            return Optional.empty();

        if(response.getStatus() == 200)
            return Optional.of(response.readEntity(OrderDto.class));

        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }
    public Collection<OrderDto> readAll()
    {
        return Arrays.asList(orderEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get(OrderDto[].class));
    }

    public Collection<OrderDto> readUserOrders(long userId)
    {
        return Arrays.asList(orderEndpoint.path("/user/" + userId).request(MediaType.APPLICATION_JSON_TYPE).get(OrderDto[].class));

    }

    public void delete()
    {
        var response = activeOrderEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();
    }

    public OrderDto create(OrderDto orderDto)
    {
        return orderEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(orderDto, MediaType.APPLICATION_JSON_TYPE), OrderDto.class);
    }
}

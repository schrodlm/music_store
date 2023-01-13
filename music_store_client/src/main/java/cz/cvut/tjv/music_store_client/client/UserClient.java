package cz.cvut.tjv.music_store_client.client;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;

@Component
public class UserClient {

    private WebTarget userEndpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget singleTemplateEndpoint2;

    private WebTarget activeUserEndpoint;

    public UserClient(@Value("${server.url}") String serverUrl)
    {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        userEndpoint = client.target(serverUrl + "/users");
        singleTemplateEndpoint = userEndpoint.path("/{id}");
        singleTemplateEndpoint2 = userEndpoint.path("/username/{username}");
    }

    public void setActiveUser(long id) { activeUserEndpoint = singleTemplateEndpoint.resolveTemplate("id",id);}
    public void setActiveUser(String username){activeUserEndpoint = singleTemplateEndpoint2.resolveTemplate("username", username);}

    public UserDto create(UserDto userDto)
    {
        return userEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userDto, MediaType.APPLICATION_JSON_TYPE), UserDto.class);
    }

    public void delete() {
        var response = activeUserEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();
    }
    public Optional<UserDto> readOne() {
        var response = activeUserEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if(response.getStatus() == 404)
            return Optional.empty();

        if(response.getStatus() == 200)
            return Optional.of(response.readEntity(UserDto.class));

        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }




}

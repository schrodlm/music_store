package cz.cvut.tjv.music_store_client;

import jakarta.activation.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootApplication
public class MusicStoreClientApplication {

	public static void main(String[] args) {

			var app = new SpringApplication(MusicStoreClientApplication.class);
			app.run(args);
	}


	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}




}

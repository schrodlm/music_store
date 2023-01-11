package cz.cvut.tjv.music_store_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicStoreClientApplication {

	public static void main(String[] args) {

			var app = new SpringApplication(MusicStoreClientApplication.class);
			app.run(args);
	}

}

package mx.egd.sat.descargopagoanalizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;

@SpringBootApplication
@Slf4j
public class DescargopagoanalizerApplication implements CommandLineRunner {

	@Autowired
	private PagosAnalizer pagosAnalizer;

	public static void main(String[] args) {
		SpringApplication.run(DescargopagoanalizerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args != null && args.length == 1) {
			pagosAnalizer.analize(args[0]);
		} else {
			log.error("Error en los argumentos.");
		}
	}

}

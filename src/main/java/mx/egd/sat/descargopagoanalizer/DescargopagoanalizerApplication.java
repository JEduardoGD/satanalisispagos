package mx.egd.sat.descargopagoanalizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;

@SpringBootApplication
public class DescargopagoanalizerApplication implements CommandLineRunner {

	@Autowired
	private PagosAnalizer pagosAnalizer;

	public static void main(String[] args) {
		SpringApplication.run(DescargopagoanalizerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(args.length);
		if (args != null && args.length == 1) {
			pagosAnalizer.analize(args[0]);
		} else {
			System.out.println("Error en los argumentos.");
		}
	}

}

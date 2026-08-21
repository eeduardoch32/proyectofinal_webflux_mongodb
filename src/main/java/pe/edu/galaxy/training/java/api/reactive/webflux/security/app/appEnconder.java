package pe.edu.galaxy.training.java.api.reactive.webflux.security.app;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.configuration.SecurityBeanConfig;

public class appEnconder {

	public static void main(String[] args) {
		
		System.out.println(new SecurityBeanConfig().passwordEncoder().encode("123456"));
		
		
		// $2a$08$70Z1NasE5vuApE/lvJmzk.SfPnhaO5a8e/Mm9E/Y9VVwB4RuY.Dna
		// $2a$08$U18M1VchOrpHgbb9tSWG1eotUpH1wfQ0XHoomzhmOKRmYNXikp3Bq

		
	}

}

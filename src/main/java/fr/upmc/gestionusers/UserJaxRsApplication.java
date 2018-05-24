package fr.upmc.gestionusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import fr.upmc.gestionusers.repository.UserRepository;
import fr.upmc.gestionusers.security.JwtFilter;

@SpringBootApplication
public class UserJaxRsApplication extends SpringBootServletInitializer implements CommandLineRunner{
	@Autowired
	UserRepository userRepository;
	
	
	
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/secure/*");

		return registrationBean;
	}
	public static void main(String[] args) {
		new UserJaxRsApplication().configure(new SpringApplicationBuilder(UserJaxRsApplication.class)).run(args);
	}
	
	@Override
	public void run(String... agr0) {
//		userRepository.save(new AppUser("david", "david"));
//		userRepository.save(new AppUser("aziz", "aziz"));
		
	}
}

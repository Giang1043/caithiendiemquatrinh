package vn.lightforknight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	// Cấu hình user mẫu trong bộ nhớ
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails admin = User.withUsername("Giang").password(encoder.encode("123")) // Mã hóa mật khẩu
				.roles("ADMIN") // Phân quyền ADMIN
				.build();

		UserDetails user = User.withUsername("user").password(encoder.encode("123")) // Mã hóa mật khẩu
				.roles("USER") // Phân quyền USER
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	// Cấu hình Password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu cần)
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/", "/hello").permitAll() // Cho phép truy cập công khai
	                .requestMatchers("/customers/**").authenticated() // Yêu cầu quyền USER
	            )
	            .formLogin(Customizer.withDefaults()) // Cấu hình đăng nhập mặc định
	            .build();
	}
}

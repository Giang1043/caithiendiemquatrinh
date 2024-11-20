package vn.lightforknight.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vn.lightforknight.models.Customer;

@RestController
@EnableMethodSecurity
public class CustomerController {
	final private List<Customer> customers = List.of(
			Customer.builder().id("001").name("Cao Cự Giang").email("tigiang2004@gmail.com").build(),
			Customer.builder().id("002").name("Giang Cao").email("abc123@gmail.com").build());

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello is Guest!");
	}

	@GetMapping("/customers/all")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Customer>> getCustomerList() {
		return ResponseEntity.ok(this.customers);
	}

	// Endpoint lấy thông tin một khách hàng theo ID, chỉ dành cho USER
	@GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        List<Customer> customers = this.customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .toList();
        return ResponseEntity.ok(customers.get(0)); 
    }
}

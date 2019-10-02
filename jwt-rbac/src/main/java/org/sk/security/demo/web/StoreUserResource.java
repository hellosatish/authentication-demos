package org.sk.security.demo.web;

import java.util.List;

import org.sk.security.demo.dto.ProductResponseDTO;
import org.sk.security.demo.dto.UserResponseDTO;
import org.sk.security.demo.services.ProductService;
import org.sk.security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class StoreUserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/{user-id}")
	public ResponseEntity<UserResponseDTO> getUserDetails( @PathVariable("user-id") int userId) {
	return	userService.findUser(userId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	}
	
	
	@GetMapping("/product")
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
		return ResponseEntity.ok().body(productService.getAllProducts());
	}
	
	@GetMapping("/product/{product-id}")
	public ResponseEntity<ProductResponseDTO> getProductDetails( @PathVariable("product-id") int productId) {
	return	productService.findProduct(productId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	}
	
	
	@GetMapping("/store/{store-id}")
	public ResponseEntity<List<ProductResponseDTO>> getProductForStore( @PathVariable("store-id") int storeId) {
	return	productService.getAllProductsForStore(storeId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	}

}

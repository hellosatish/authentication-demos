package org.sk.security.demo.web;

import java.util.List;

import org.sk.security.demo.dto.ProductDTO;
import org.sk.security.demo.dto.ProductResponseDTO;
import org.sk.security.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product")
public class ProductManagementResource {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
		return ResponseEntity.ok().body(productService.getAllProducts());
	}
	
	@GetMapping("/{product-id}")
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
	
	@PutMapping("/{product-id}")
	public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductDTO requestData, @PathVariable("product-id") int productId){
		return productService.udpateProduct(requestData, productId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@PostMapping
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductDTO requestData) {
		return productService.createProduct(requestData).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@DeleteMapping("/{store-id}")
	public ResponseEntity<Integer> deleteProduct(@PathVariable("product-id") int productId){
	return 	productService.deleteProduct(productId).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 

}

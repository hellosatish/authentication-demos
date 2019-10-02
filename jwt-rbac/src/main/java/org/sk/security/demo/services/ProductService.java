package org.sk.security.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sk.security.demo.db.entities.Product;
import org.sk.security.demo.db.repositories.ProductRepository;
import org.sk.security.demo.dto.ProductDTO;
import org.sk.security.demo.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	public Optional<ProductResponseDTO> createProduct(ProductDTO requestData) {
		Product prdct = productRepo.save(requestData.getEntity(0));
		return Optional.of(prdct.getDto());
	}

	public Optional<ProductResponseDTO> udpateProduct(ProductDTO requestData, int userId) {
		Optional<Product> productInDb = productRepo.findById(userId);
		return productInDb.map(x -> {
			Product usr = productRepo.save(requestData.getEntity(userId));
			return Optional.of(usr.getDto());
		}).orElseGet(Optional::empty);
	}
	
	
	public Optional<List<ProductResponseDTO>> getAllProductsForStore(int storeId) {
		Optional<List<Product>> productsForStore = productRepo.findByStoreId(storeId);
		if(productsForStore.isPresent()) {
			return Optional.of(productsForStore.get().stream().map(Product::getDto).collect(Collectors.toList()));
		}else {
			return Optional.empty();
		}
	}

	public Optional<Integer> deleteProduct(int userId) {
		Optional<Product> productInDb = productRepo.findById(userId);
		return productInDb.map(x -> {
			productRepo.deleteById(userId);
			return Optional.of(userId);
		}).orElseGet(Optional::empty);
	}

	public Optional<ProductResponseDTO> findProduct(int productID){
		Optional<Product> productInDb = productRepo.findById(productID);
		return productInDb.map(x -> {
			return Optional.of(x.getDto());
		}).orElseGet(Optional::empty);
	}
	
	public List<ProductResponseDTO> getAllProducts() {
		return productRepo.findAll().stream().map(Product::getDto).collect(Collectors.toList());
	}
}

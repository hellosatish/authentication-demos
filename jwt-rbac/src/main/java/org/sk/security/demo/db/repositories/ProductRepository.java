package org.sk.security.demo.db.repositories;

import java.util.List;
import java.util.Optional;

import org.sk.security.demo.db.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Optional<List<Product>> findByStoreId(int storeId);

}

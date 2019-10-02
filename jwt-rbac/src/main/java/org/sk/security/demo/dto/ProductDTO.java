package org.sk.security.demo.dto;

import org.sk.security.demo.db.entities.EntityConverter;
import org.sk.security.demo.db.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements EntityConverter<Product> {
	private int productId;
	private String productName;
	private int price;
	private boolean available;
	private int storeId;

	@Override
	public Product getEntity(int id) {
		return new Product(id, this.productName, this.price, this.available, this.storeId);
	}
}

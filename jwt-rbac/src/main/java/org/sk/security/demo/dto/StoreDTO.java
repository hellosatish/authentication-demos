package org.sk.security.demo.dto;

import org.sk.security.demo.db.entities.EntityConverter;
import org.sk.security.demo.db.entities.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO  implements EntityConverter<Store> {
	protected String storeName;
	protected String address;
	protected boolean active;
	
	@Override
	public Store getEntity(int id) {
		return new Store(id, this.storeName, this.address, this.active);
	}
}

package org.sk.security.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sk.security.demo.db.entities.Store;
import org.sk.security.demo.db.repositories.StoreRepository;
import org.sk.security.demo.dto.StoreDTO;
import org.sk.security.demo.dto.StoreResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepo;

	public Optional<StoreResponseDTO> createStore(StoreDTO requestData) {
		Store store = storeRepo.save(requestData.getEntity(0));
		return Optional.of(store.getDto());
	}

	public Optional<StoreResponseDTO> udpateStore(StoreDTO requestData, int userId) {
		Optional<Store> userInDb = storeRepo.findById(userId);
		return userInDb.map(x -> {
			Store store = storeRepo.save(requestData.getEntity(userId));
			return Optional.of(store.getDto());
		}).orElseGet(Optional::empty);
	}

	public Optional<Integer> deleteStore(int userId) {
		Optional<Store> userInDb = storeRepo.findById(userId);
		return userInDb.map(x -> {
			storeRepo.deleteById(userId);
			return Optional.of(userId);
		}).orElseGet(Optional::empty);
	}

	public Optional<StoreResponseDTO> findStore(int storeId){
		Optional<Store> userInDb = storeRepo.findById(storeId);
		return userInDb.map(x -> {
			return Optional.of(x.getDto());
		}).orElseGet(Optional::empty);
	}
	
	
	public List<StoreResponseDTO> getAllStores() {
		return storeRepo.findAll().stream().map(Store::getDto).collect(Collectors.toList());
	}
}

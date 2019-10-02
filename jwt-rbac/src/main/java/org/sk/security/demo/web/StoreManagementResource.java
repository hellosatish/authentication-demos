package org.sk.security.demo.web;

import java.util.List;

import org.sk.security.demo.dto.StoreDTO;
import org.sk.security.demo.dto.StoreResponseDTO;
import org.sk.security.demo.dto.UserDTO;
import org.sk.security.demo.dto.UserResponseDTO;
import org.sk.security.demo.services.StoreService;
import org.sk.security.demo.services.UserService;
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
@RequestMapping(path = "/mgmt")
public class StoreManagementResource {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/stores")
	public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
		return ResponseEntity.ok().body(storeService.getAllStores());
	}
	
	@GetMapping("/stores/{store-id}")
	public ResponseEntity<StoreResponseDTO> getStoreDetails( @PathVariable("store-id") int storeId) {
	return	storeService.findStore(storeId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	}
	
	@PutMapping("/stores/{store-id}")
	public ResponseEntity<StoreResponseDTO> updateStore(@RequestBody StoreDTO requestData, @PathVariable("store-id") int storeId){
		return storeService.udpateStore(requestData, storeId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@PostMapping("/stores")
	public ResponseEntity<StoreResponseDTO> createStore(@RequestBody StoreDTO requestData) {
		return storeService.createStore(requestData).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@DeleteMapping("/stores/{store-id}")
	public ResponseEntity<Integer> deleteStore(@PathVariable("store-id") int storeId){
	return 	storeService.deleteStore(storeId).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 

	/* User related operation */
	
	@GetMapping("/user")
	public ResponseEntity<List<UserResponseDTO>> getAllUser() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}
	
	@GetMapping("/user/{user-id}")
	public ResponseEntity<UserResponseDTO> getUserDetails( @PathVariable("user-id") int userId) {
	return	userService.findUser(userId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	}
	
	@PutMapping("/user/{user-id}")
	public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserDTO requestData, @PathVariable("user-id") int userId){
		return userService.udpateUser(requestData, userId)
		.map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@PostMapping("/user")
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO requestData) {
		return userService.createUser(requestData).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
	
	@DeleteMapping("/user/{user-id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable("user-id") int userId){
	return 	userService.deleteUser(userId).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});
	} 
}

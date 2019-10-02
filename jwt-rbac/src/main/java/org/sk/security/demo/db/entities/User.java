package org.sk.security.demo.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sk.security.demo.dto.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements DtoConvertor< UserResponseDTO>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String firstName;
	private String lastName;
	private String loginId;
	private String password;
	private boolean active;
	private String role;
	
	
	@Override
	public UserResponseDTO getDto() {
		UserResponseDTO d = new UserResponseDTO();
		d.setActive(this.active);
		d.setFirstName(this.firstName);
		d.setLastName(this.lastName);
		d.setLoginId(this.loginId);
		d.setPassword(this.password);
		d.setRole(this.role);
		d.setUserId(this.userId);
		return d;
	}
	
}

package net.codejava;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false, unique = true)
	@NotBlank(message = "E-mail address must not be empty")
	@Email(message = "User must have valid email address")
	private String email;
	
	@Column(length = 20, nullable = false)
	private String firstName;
	
	@Column(length = 20, nullable = false)
	private String lastName;
	
	@Column(length = 10, nullable = false)
	private String password;

	
	public User email(String email) {
		this.email = email;
		return this;
	}

	public User firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public User lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public User password(String password) {
		this.password = password;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		User other = (User) obj;
//		return Objects.equals(id, other.id);
//	}
//
	public User id(Long id) {
		this.id = id;
		return this;
	}
}

package net.codejava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserApiControllerTests {
	@Mock
	private UserService userService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private UserApiController userApiController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddUser() {
		User user = new User();
		User persistedUser = new User();
		UserDTO userDto = new UserDTO();
		URI uri = URI.create("/users/4");

		when(userService.add(user)).thenReturn(persistedUser);
		when(modelMapper.map(persistedUser, UserDTO.class)).thenReturn(userDto);

		ResponseEntity<?> response = userApiController.add(user);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(uri, response.getHeaders().getLocation());
		assertEquals(userDto, response.getBody());

		verify(userService, times(1)).add(user);
		verify(modelMapper, times(1)).map(persistedUser, UserDTO.class);
	}

	@Test
	public void testGetUser_WithValidId() throws UserNotFoundException {
		Long id = 40L;
		User user = new User();
		UserDTO userDto = new UserDTO();

		//when(userService.get(id)).thenReturn(user);
		//when(modelMapper.map(user, UserDTO.class)).thenReturn(userDto);

		ResponseEntity<?> response = userApiController.get(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		//assertEquals(userDto, response.getBody());

		verify(userService, times(1)).get(id);
		//verify(modelMapper, times(1)).map(user, UserDTO.class);
	}

	@Test
	public void testGetUser_WithInvalidId() throws UserNotFoundException {
		Long id = 4L;

		when(userService.get(id)).thenThrow(new UserNotFoundException());

		ResponseEntity<?> response = userApiController.get(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(userService, times(1)).get(id);
	}

	@Test
	public void testListUsers_WithEmptyList() {
		List<User> listUsers = new ArrayList<>();

		when(userService.list()).thenReturn(listUsers);

		ResponseEntity<?> response = userApiController.list();

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		verify(userService, times(1)).list();
	}

	@Test
	public void testListUsers_WithNonEmptyList() {
		List<User> listUsers = new ArrayList<>();
		listUsers.add(new User());
		List<UserDTO> listUserDto = new ArrayList<>();
		listUserDto.add(new UserDTO());

		when(userService.list()).thenReturn(listUsers);
		when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

		ResponseEntity<?> response = userApiController.list();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(listUserDto, response.getBody());

		verify(userService, times(1)).list();
		verify(modelMapper, times(listUsers.size())).map(any(User.class), eq(UserDTO.class));
	}

	@Test
	public void testUpdateUser_WithValidId() throws UserNotFoundException {
		Long id = 1L;
		User user = new User();
		User updatedUser = new User();
		UserDTO updatedUserDto = new UserDTO();

		when(userService.update(user)).thenReturn(updatedUser);
		when(modelMapper.map(updatedUser, UserDTO.class)).thenReturn(updatedUserDto);

		ResponseEntity<?> response = userApiController.update(id, user);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedUserDto, response.getBody());

		verify(userService, times(1)).update(user);
		verify(modelMapper, times(1)).map(updatedUser, UserDTO.class);
	}

	@Test
	public void testUpdateUser_WithInvalidId() throws UserNotFoundException {
		Long id = 1L;
		User user = new User();

		when(userService.update(user)).thenThrow(new UserNotFoundException());

		ResponseEntity<?> response = userApiController.update(id, user);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(userService, times(1)).update(user);
	}

	@Test
	public void testdeleteUser_WithValidId() throws UserNotFoundException {
		Long id = 1L;

		ResponseEntity<?> response = userApiController.delete(id);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		verify(userService, times(1)).delete(id);
	}

	@Test
	public void testDeleteUser_WithInvalidId() throws UserNotFoundException {
		Long id = 1L;

		when(userApiController.delete(id)).thenThrow(new UserNotFoundException());

		ResponseEntity<?> response = userApiController.delete(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(userService, times(1)).delete(id);
	}
}
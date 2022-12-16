package com.event.organizer.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.UserRepository;

@ContextConfiguration(classes = { UserRepository.class, AppUserService.class, AppUser.class })
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = { UserRepository.class,
		AppUserService.class, AppUser.class })
class AccountTests {

	@Test
	public void givenValidInput_whenEditAccount_updatesSuccessfully() {
		// Arrange
		AppUser editedUser = new AppUser();
		editedUser.setEmail("test@example.com");

		// Set up mocked user repository to return the edited user when findByEmail is
		// called
		UserRepository mockedUserRepository = mock(UserRepository.class);
		when(mockedUserRepository.findByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

		// Set up class under test and inject mocked user repository
		AppUserService appUserService = mock(AppUserService.class);

		// Act
		editedUser.setName("testName");
		appUserService.editAccount(editedUser);

		AppUser newEditedUser = mockedUserRepository.findByEmail(editedUser.getEmail()).get();

		// Assert
		Assertions.assertEquals("testName", newEditedUser.getName());
	}

	@Test
	public void givenNullInput_whenEditAccount_throwsException() {
		// Arrange
		AppUser editedUser = null;

		// Set up class under test
		UserRepository userRepository = mock(UserRepository.class);
		AppUserService appUserService = new AppUserService(userRepository, null);

		// Act
		UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
				() -> appUserService.editAccount(editedUser));

		Assertions.assertTrue(thrown.getMessage().contentEquals("User is not found."));
	}

	@Test
	public void givenNonExistingEmail_whenEditAccount_throwsException() {
		// Arrange
		AppUser editedUser = new AppUser();
		editedUser.setEmail("test@example.com");

		// Set up mocked user repository to return empty Optional when findByEmail is
		// called
		UserRepository mockedUserRepository = mock(UserRepository.class);
		when(mockedUserRepository.findByEmail(editedUser.getEmail())).thenReturn(Optional.empty());

		// Set up class under test and inject mocked user repository
		AppUserService appUserService = new AppUserService(mockedUserRepository, null);

		// Act
		UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
				() -> appUserService.editAccount(editedUser));

		Assertions.assertTrue(thrown.getMessage().contentEquals("User with email test@example.com not found"));
	}
}

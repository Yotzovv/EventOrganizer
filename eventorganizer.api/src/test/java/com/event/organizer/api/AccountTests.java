package com.event.organizer.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.event.organizer.api.appuser.AppUserRoleService;
import com.event.organizer.api.security.config.AdminConfig;
import org.apache.catalina.User;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.repository.ImageRepository;

@ContextConfiguration(classes = {UserRepository.class, AppUserService.class, AppUser.class})
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        		classes = {UserRepository.class, AppUserService.class, AppUser.class})
class AccountTests {

	@Mock
	private AppUserRoleService appUserRoleService;

	@Test
	public void givenValidInput_whenEditAccount_updatesSuccessfully() {
		// Arrange
		AppUser editedUser = new AppUser();
		editedUser.setEmail("test@example.com");

		// Set up mocked user repository to return the edited user when findByEmail is called
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
		ImageRepository imageRepository = mock(ImageRepository.class);
		AppUserService appUserService = new AppUserService(userRepository, imageRepository ,null, appUserRoleService);

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

		// Set up mocked user repository to return empty Optional when findByEmail is called
		UserRepository mockedUserRepository = mock(UserRepository.class);
		when(mockedUserRepository.findByEmail(editedUser.getEmail())).thenReturn(Optional.empty());

		ImageRepository imageRepository = mock(ImageRepository.class);

		// Set up class under test and inject mocked user repository	
		AppUserService appUserService = new AppUserService(mockedUserRepository, imageRepository, null, appUserRoleService);

		// Act
		UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
				() -> appUserService.editAccount(editedUser));

		Assertions.assertTrue(thrown.getMessage().contentEquals("User with email test@example.com not found"));
	}


	@Test
	public void GivenValidData_WhenBlockingUser_ThenSuccess() {
		UserRepository userRepository = mock(UserRepository.class);
		ImageRepository imageRepository = mock(ImageRepository.class);

		AppUserService userService = new AppUserService(userRepository, imageRepository, null, appUserRoleService);

		// Set up mock user data
		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser userToBlock = new AppUser();
		userToBlock.setEmail("block@example.com");
		userToBlock.setBlockedUsers(new ArrayList<AppUser>());

		when(userRepository.findByEmail("current@example.com")).thenReturn(Optional.of(currentUser));
		when(userRepository.findByEmail("block@example.com")).thenReturn(Optional.of(userToBlock));

		// Invoke the method under test
		userService.blockUser("current@example.com", "block@example.com");

		// Verify the results
		List<AppUser> blockedUsers = currentUser.getBlockedUsers();
		Assertions.assertEquals(1, blockedUsers.size());
		Assertions.assertEquals("block@example.com", blockedUsers.get(0).getEmail());

		List<AppUser> usersBlocking = userToBlock.getBlockedUsers();
		Assertions.assertEquals(1, usersBlocking.size());
		Assertions.assertEquals("current@example.com", usersBlocking.get(0).getEmail());
	}
}


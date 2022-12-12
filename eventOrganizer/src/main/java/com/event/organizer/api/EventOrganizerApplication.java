package com.event.organizer.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventOrganizerApplicationTests {

	@Test
	void contextLoads() {
	}

	@test
	public void testEditAccount_withValidInput_updatesAccount() {
		// Arrange
		AppUser editedUser = new AppUser();
		editedUser.setEmail("test@example.com");

		// Set up mocked user repository to return the edited user when findByEmail is called
		UserRepository mockedUserRepository = mock(UserRepository.class);
		when(mockedUserRepository.findByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

		// Set up class under test and inject mocked user repository
		EditAccountService editAccountService = new EditAccountService();
		editAccountService.setUserRepository(mockedUserRepository);

		// Act
		editAccountService.EditAccount(editedUser);

		// Assert
		verify(mockedUserRepository).save(editedUser);
	}

	@test(expected = UsernameNotFoundException.class)
	public void testEditAccount_withNullInput_throwsException() {
		// Arrange
		AppUser editedUser = null;

		// Set up class under test
		EditAccountService editAccountService = new EditAccountService();

		// Act
		editAccountService.EditAccount(editedUser);
	}

	@test(expected = UsernameNotFoundException.class)
	public void testEditAccount_withNonexistentEmail_throwsException() {
		// Arrange
		AppUser editedUser = new AppUser();
		editedUser.setEmail("test@example.com");

		// Set up mocked user repository to return empty Optional when findByEmail is called
		UserRepository mockedUserRepository = mock(UserRepository.class);
		when(mockedUserRepository.findByEmail(editedUser.getEmail())).thenReturn(Optional.empty());

		// Set up class under test and inject mocked user repository
		EditAccountService editAccountService = new EditAccountService();
		editAccountService.setUserRepository(mockedUserRepository);

		// Act
		editAccountService.EditAccount(editedUser);
	}
}

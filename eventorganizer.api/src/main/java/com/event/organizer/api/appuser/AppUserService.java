package com.event.organizer.api.appuser;

import com.event.organizer.api.model.Image;
import com.event.organizer.api.model.dto.AccountRolesRequestDto;
import com.event.organizer.api.model.dto.AccountStatusRequestDto;
import com.event.organizer.api.repository.ImageRepository;
import com.event.organizer.api.security.config.AdminConfig;
import com.event.organizer.api.security.config.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    private static final String USER_CREATED = "User %s is created";

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final PasswordEncoder passwordEncoder;

    private final AppUserRoleService appUserRoleService;

    public void editAccount(AppUser editedUser) throws UsernameNotFoundException {
        if (editedUser == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        //TODO: blocked by ev3
        //if(editedUser != loggedInUser) {
        //    throw new Exception("Permission denied.")
        //}
        var currentUser = userRepository.findByEmail(editedUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, editedUser.getEmail())));

        userRepository.save(currentUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("User exists");
        }

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser.setEnabled(true);

        userRepository.save(appUser);

        return String.format(USER_CREATED, appUser.getUsername());
    }

    public AppUser saveAppUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public void blockUser(String currentUserName, String userToBlockEmail) {
        AppUser currentUser = findValidatedUser(currentUserName);
        AppUser userToBlock = findValidatedUser(userToBlockEmail);

        List<AppUser> blockedUsersList = currentUser.getBlockedUsers();
        blockedUsersList.add(userToBlock);

        List<AppUser> userToBlockBlockList = userToBlock.getBlockedUsers();
        userToBlockBlockList.add(currentUser);

        currentUser.setBlockedUsers(blockedUsersList);
        userToBlock.setBlockedUsers(userToBlockBlockList);

        userRepository.save(currentUser);
        userRepository.save(userToBlock);
    }

    public void unblockUser(String currentUserName, String userToBlockEmail) {
        AppUser currentUser = findValidatedUser(currentUserName);
        AppUser userToUnBlock = findValidatedUser(userToBlockEmail);

        List<AppUser> blockedUsersList = currentUser.getBlockedUsers();

        AppUser blockedUserMatch = blockedUsersList.stream()
        .filter(a -> Objects.equals(a.getEmail(), userToBlockEmail))
        .findFirst()
        .orElse(null);
    
        blockedUsersList.remove(blockedUserMatch);

        List<AppUser> userToBlockBlockList = userToUnBlock.getBlockedUsers();
        AppUser userToBlockMatch = userToBlockBlockList.stream()
        .filter(a -> Objects.equals(a.getEmail(), currentUser.getEmail()))
        .findFirst()
        .orElse(null);

        userToBlockBlockList.remove(userToBlockMatch);

        currentUser.setBlockedUsers(blockedUsersList);
        userToUnBlock.setBlockedUsers(userToBlockBlockList);

        userRepository.save(currentUser);
        userRepository.save(userToUnBlock);
    }

    public AppUser changeAccountRole(AccountRolesRequestDto accountRolesRequestDto, String currentUserEmail) {
        AppUser currentUser = findValidatedUser(currentUserEmail);
        AppUser editedUser = findValidatedUser(accountRolesRequestDto.getEmail());

        editUserRoles(currentUser, editedUser, accountRolesRequestDto.getRoles());

        return userRepository.save(editedUser);
    }

    public AppUser changeAccountStatus(String currentUserEmail, AccountStatusRequestDto accountStatusRequestDto) {
        AppUser currentUser = findValidatedUser(currentUserEmail);
        AppUser editedUser = findValidatedUser(accountStatusRequestDto.getEmail());
        editAccountStatus(currentUser, editedUser, accountStatusRequestDto.isEnabled());
        return userRepository.save(editedUser);
    }

    public void uploadProfilePicture(byte[] imageUrl, String currentUserEmail) {
        AppUser user = findValidatedUser(currentUserEmail);

        Image profilePicture = new Image();

        
        profilePicture.setUrl(imageUrl);
        profilePicture.setCreatedDate(LocalDateTime.now());

        imageRepository.save(profilePicture);

        user.setProfilePicture(profilePicture);

        userRepository.save(user);
    }

    private void editAccountStatus(AppUser currentUser, AppUser appUser, boolean enabled) {
        if (!AdminConfig.isSuperUserAdmin(currentUser.getEmail())) {
            validateCannotChangeStatusToOtherAdmin(appUser, enabled);
        }
        appUser.setEnabled(enabled);
    }

    private void editUserRoles(AppUser currentUser, AppUser appUser, List<String> roles) {
        // only superuser admin can downgrade other admins
        // other admins should not be able to downgrade other admins

        Set<AppUserRole> userRoleSet = roles.stream()
                .filter(AppUserRole.ROLE_TYPES::contains)
                .map(appUserRoleService::getRole)
                .collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(userRoleSet)) {
            throw new IllegalStateException("Roles cannot be empty");
        }


        if (!AdminConfig.isSuperUserAdmin(currentUser.getEmail())) {
            validateCannotDowngradeOtherAdmins(appUser, userRoleSet);
        }
        appUser.setRoles(userRoleSet);
    }

    public AppUser findValidatedUser(String email) {
        Optional<AppUser> editedUser = userRepository.findByEmail(email);
        validateUserExists(editedUser);
        return editedUser.get();
    }

    private void validateUserExists(Optional<AppUser> user) {
        if (!user.isPresent()) {
            throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
        }
    }

    private void validateCannotDowngradeOtherAdmins(AppUser appUser, Set<AppUserRole> appUserRoles) {
        if (appUser.getRoles().contains(AppUserRole.ADMIN) && !appUserRoles.contains(AppUserRole.ADMIN)) {
            throw new IllegalStateException("Admin user cannot be downgraded");
        }
    }

    private void validateCannotChangeStatusToOtherAdmin(AppUser appUser, boolean enabled){
        if (appUser.getRoles().contains(AppUserRole.ADMIN) && !enabled ) {
            throw new IllegalStateException("Admin user cannot be disabled");
        }
    }

}

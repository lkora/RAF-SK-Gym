package raf.sk.gym.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raf.sk.gym.userservice.exception.UserIsAdminException;
import raf.sk.gym.userservice.model.Client;
import raf.sk.gym.userservice.model.Manager;
import raf.sk.gym.userservice.model.User;
import raf.sk.gym.userservice.repository.ClientRepository;
import raf.sk.gym.userservice.repository.ManagerRepository;
import raf.sk.gym.userservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, ClientRepository clientRepository,
                       ManagerRepository managerRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.encoder = encoder;
    }

    public void createAndStoreClient(String username, String password, String email, LocalDate birthDate,
                                     String firstname, String lastname) {
        log.info("Creating and storing client with username {}", username);
        User user = createUser(username, password, email, birthDate, firstname, lastname, "client");
        Long memberCardNumber = generateCardNumber();
        Client client = new Client(user.getId(), user, memberCardNumber, 0L);
        clientRepository.save(client);
        log.info("Client with username {} created and stored successfully", username);
    }

    private Long generateCardNumber() {
        return userRepository.count() + 1;
    }

    public void createAndStoreManager(String username, String password, String email, LocalDate birthDate,
                                      String firstName, String lastName, String gymnasiumName,
                                      LocalDate dateOfEmployment) {
        log.info("Creating and storing manager with username: {}", username);
        User user = createUser(username, password, email, birthDate, firstName, lastName, "manager");
        Manager manager = new Manager(user.getId(), user, gymnasiumName, dateOfEmployment);
        managerRepository.save(manager);
        log.info("Manager with username {} created and stored successfully", username);
    }

    private User createUser(String username, String password, String email, LocalDate birthDate, String firstName,
                            String lastName, String userType) {
        log.debug("Creating user with username {}", username);
        User user = new User(null, username, encoder.encode(password), email, birthDate, firstName, lastName, userType, false, false);
        User savedUser = userRepository.save(user);
        log.debug("Created and saved user with username {} and id {}", username, savedUser.getId());
        return savedUser;
    }

    public void ban(String username) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(user -> {
                    if (user.getUserType()
                            .equalsIgnoreCase("admin")) {
                        throw new UserIsAdminException("Cannot ban another administrator");
                    }
                    user.setIsBanned(true);
                    userRepository.save(user);
                }, () -> {
                    throw new UsernameNotFoundException("Username not found");
                });
    }

    public void unban(String username) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(user -> {
                    user.setIsBanned(false);
                    userRepository.save(user);
                }, () -> {
                    throw new UsernameNotFoundException("Username not found");
                });
    }

    public void editProfile(String username, String password, LocalDate dateOfBirth, String firstName,
                            String lastName) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(user -> {
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setBirthDate(dateOfBirth);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    userRepository.save(user);
                }, () -> {
                    throw new UsernameNotFoundException("Username not found");
                });
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<Manager> findManager(Long id) {
        return managerRepository.findById(id);
    }
    public Optional<Client> findClient(Long id) {
        return clientRepository.findByClientId(id);
    }


    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}

package raf.sk.gym.userservice.service;

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
import java.util.Optional;

@Service
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
        User user = new User((Long) null, username, encoder.encode(password), email, birthDate, firstname, lastname,
                "client", false);
        Long memberCardNumber = generateCardNumber();
        User savedUser = userRepository.save(user);
        System.out.println(memberCardNumber);
        System.out.println(savedUser);
        System.out.println(clientRepository.existsById(savedUser.getId()));
        Client client = new Client(savedUser.getId(), savedUser, memberCardNumber, 0L);
        System.out.println(clientRepository.save(client));
        clientRepository.findByClientId(savedUser.getId())
                .ifPresent(System.out::println);
    }

    private Long generateCardNumber() {
        return userRepository.count() + 1;
    }

    public void createAndStoreManager(String userName, String password, String email, LocalDate birthDate,
                                      String firstName, String lastName, String gymnasiumName,
                                      LocalDate dateOfEmployment) {
        User user = new User(null, userName, encoder.encode(password), email, birthDate, firstName, lastName,
                "manager", false);
        User savedUser = userRepository.save(user);
        Manager manager = new Manager(savedUser.getId(), savedUser, gymnasiumName, dateOfEmployment);
        managerRepository.save(manager);
    }

    public void ban(String username) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(user -> {
                    if (user.getUserType().equalsIgnoreCase("admin")) {
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

    public void editProfile(String username, String password, LocalDate dateOfBirth, String firstName, String lastName) {
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

}

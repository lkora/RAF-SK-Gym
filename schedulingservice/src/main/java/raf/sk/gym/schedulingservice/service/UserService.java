package raf.sk.gym.schedulingservice.service;

import org.springframework.stereotype.Service;
import raf.sk.gym.schedulingservice.client.UserClient;
import raf.sk.gym.schedulingservice.dto.kafka.UserDto;

@Service
public class UserService {

    private final UserClient client;

    public UserService(UserClient client) {this.client = client;}

    public UserDto findUser(String jwt) {
        return client.findUser(jwt);
    }

}

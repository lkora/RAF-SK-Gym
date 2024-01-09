package raf.sk.gym.userservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.userservice.model.UserInfo;

public interface UserInfoRepository extends ListCrudRepository<UserInfo, Long> {
}

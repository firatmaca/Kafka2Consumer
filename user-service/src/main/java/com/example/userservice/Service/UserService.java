package com.example.userservice.Service;

import com.example.userservice.Config.KafkaProducer;
import com.example.userservice.Config.UserCreatedTopicProperties;
import com.example.userservice.Dto.AddressResponseDto;
import com.example.userservice.Dto.UserCreateRequest;
import com.example.userservice.Dto.UserCreatedPayload;
import com.example.userservice.Dto.UserResponse;
import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final KafkaProducer kafkaProducer;

    private final UserCreatedTopicProperties userCreatedTopicProperties;
    private RestTemplate restTemplate = new RestTemplate();
    public User create(UserCreateRequest userCreateRequest) {
        User user = getUser(userCreateRequest);
        User savedUser = userRepository.save(user);

        UserCreatedPayload payload =getUserCreatedPayload(savedUser, userCreateRequest.getAddressText());

        Map<String, Object> headers = new HashMap<>();
        headers.put(TOPIC, userCreatedTopicProperties.getTopicName());
        headers.put(KEY, savedUser.getId().toString());

        kafkaProducer.sendMessage(new GenericMessage(payload, headers));
        return savedUser;
    }
    public UserResponse getUserById(Long userId) {

        // 52245 portu user addresportu random generete edildiği için değişmektedir.
        String url = String.format("http://localhost:52245/api/address/%s", userId);
        ResponseEntity<AddressResponseDto> address = restTemplate.getForEntity(url, AddressResponseDto.class);

        Optional<User> userOptional = userRepository.findById(address.getBody().getUserId());
        if (!userOptional.isPresent()) {
            return null;
        }
        return getUserResponseWithAddress(userOptional.get(), address.getBody());
    }
    private  UserResponse getUserResponseWithAddress(User user, AddressResponseDto address){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(address);
        userResponse.setId(user.getId());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
    }
    private  User getUser(UserCreateRequest userCreateRequest){
        User user = new User();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setEmail(userCreateRequest.getEmail());
        return user;
    }
    private  UserCreatedPayload getUserCreatedPayload(User user, String addressText){
        UserCreatedPayload userCreatedPayload = new UserCreatedPayload();
        userCreatedPayload.setId(user.getId());
        userCreatedPayload.setFirstName(user.getFirstName());
        userCreatedPayload.setLastName(user.getLastName());
        userCreatedPayload.setEmail(user.getEmail());
        userCreatedPayload.setAddressText(addressText);
        userCreatedPayload.setCreatedAt(user.getCreatedAt());
        userCreatedPayload.setUpdatedAt(user.getUpdatedAt());
        userCreatedPayload.setStatus(user.getStatus());
        return userCreatedPayload;
    }

}

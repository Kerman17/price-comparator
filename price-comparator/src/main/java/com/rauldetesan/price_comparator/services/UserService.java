package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertDTO;
import com.rauldetesan.price_comparator.dtos.UserDTOS.UserResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();

        return entityListToDtoList(userList);
    }

    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " does not exist"));

        return entityToDTO(user);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public UserResponseDTO entityToDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        List<PriceAlertDTO> priceAlertDTOS = user.getPriceAlerts().stream().map(alert -> {
            PriceAlertDTO priceAlertDTO = new PriceAlertDTO();
            priceAlertDTO.setId(alert.getId());
            priceAlertDTO.setTargetPrice(alert.getTargetPrice());
            priceAlertDTO.setUserId(alert.getUser().getId());
            priceAlertDTO.setProductName(alert.getProductName());

            return priceAlertDTO;
        }).toList();

        dto.setPriceAlerts(priceAlertDTOS);
        dto.setNotifications(user.getNotifications());


        return dto;
    }

    public List<UserResponseDTO> entityListToDtoList(List<User> users){
        List<UserResponseDTO> dtoList = new ArrayList<>();

        for(User user : users){
            UserResponseDTO dto = entityToDTO(user);

            dtoList.add(dto);
        }

        return dtoList;
    }
}

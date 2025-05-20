package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertDTO;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertResponseDTO;
import com.rauldetesan.price_comparator.dtos.UserDTOS.UserResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.PriceAlertRepository;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceAlertService {

    private final PriceAlertRepository priceAlertRepository;
    private final UserRepository userRepository;

    @Autowired
    public PriceAlertService(PriceAlertRepository priceAlertRepository, UserRepository userRepository) {
        this.priceAlertRepository = priceAlertRepository;
        this.userRepository = userRepository;
    }

    public List<PriceAlertResponseDTO> getAllPriceAlerts(){
        List<PriceAlert> priceAlerts = priceAlertRepository.findAll();

        return entityListToDtoList(priceAlerts);
    }

    public void addPriceAlert(PriceAlertDTO dto){

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + dto.getUserId() + " not found"));


        PriceAlert priceAlert = new PriceAlert();

        priceAlert.setId(dto.getId());
        priceAlert.setTargetPrice(dto.getTargetPrice());
        priceAlert.setProductName(dto.getProductName());
        priceAlert.setUser(user);

        priceAlertRepository.save(priceAlert);
    }

    public PriceAlertResponseDTO entityToDTO(PriceAlert priceAlert){
        PriceAlertResponseDTO dto = new PriceAlertResponseDTO();

        dto.setTargetPrice(priceAlert.getTargetPrice());
        dto.setUserId(priceAlert.getUser().getId());
        dto.setProductName(priceAlert.getProductName());
        dto.setId(priceAlert.getId());

        return dto;
    }

    public List<PriceAlertResponseDTO> entityListToDtoList(List<PriceAlert> priceAlerts){
        List<PriceAlertResponseDTO> dtoList = new ArrayList<>();

        for(PriceAlert priceAlert : priceAlerts){
            PriceAlertResponseDTO dto = entityToDTO(priceAlert);

            dtoList.add(dto);
        }

        return dtoList;
    }
}

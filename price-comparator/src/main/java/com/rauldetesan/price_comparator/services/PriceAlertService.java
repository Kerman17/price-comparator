package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.PriceAlertRepository;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<PriceAlert> getAllPriceAlerts(){
        return priceAlertRepository.findAll();
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
}

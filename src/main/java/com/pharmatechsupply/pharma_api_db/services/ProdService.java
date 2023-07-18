package com.pharmatechsupply.pharma_api_db.services;

import com.pharmatechsupply.pharma_api_db.entities.Product;
import com.pharmatechsupply.pharma_api_db.repositories.ProdRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
@Service
public class ProdService {

    private final ProdRepository supplyRepository;

    public ProdService(ProdRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Transactional
    public Iterable<Product> findAllSupplies() {
        return supplyRepository.findAll();
    }

    public Product getSupplyById(Integer id) {
        Optional<Product> optionalSupply = supplyRepository.findById(id);

        if (optionalSupply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supply not found with id: " + id);
        }
//        gets the supply out of the optional container and returns it to the controller
        return optionalSupply.get();
    }

    public Product addSupply(Product supply) {
        return supplyRepository.save(supply);
    }

    public Product updateSupply(Integer id, Product updates) {
        Product supplyToUpdate = getSupplyById(id);

        if (updates.getURL() != null) {
            supplyToUpdate.setURL(updates.getURL());
        }

        if (!updates.getName().isEmpty()) {
            supplyToUpdate.setName(updates.getName());
        }

        if (updates.getDescription() != null) {
            supplyToUpdate.setDescription(updates.getDescription());
        }

        if (updates.getPrice() != null) {
            supplyToUpdate.setPrice(updates.getPrice());
        }

        if (updates.getQuantity() != null) {
            supplyToUpdate.setQuantity(updates.getQuantity());
        }

        return supplyRepository.save(supplyToUpdate);
    }

    public HashMap<String, Object> deleteSupply(Integer id) {
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Product> optionalSupply = supplyRepository.findById(id);

        if (optionalSupply.isEmpty()) {
//            if the supply does not exist, this is what will be returned
            responseMap.put("wasDeleted", false);
            responseMap.put("supplyInfo", null);
            responseMap.put("Message", "Supply not found with id: " + id);
            return responseMap;
        }
        supplyRepository.deleteById(id);
        responseMap.put("wasDeleted", true);
        responseMap.put("supplyInfo", optionalSupply.get());

        return responseMap;
    }

}


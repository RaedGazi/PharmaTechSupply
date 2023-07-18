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

    private final ProdRepository prodRepository;

    public ProdService(ProdRepository prodRepository) {
        this.prodRepository = prodRepository;
    }

    @Transactional
    public Iterable<Product> findAllProducts() {
        return prodRepository.findAll();
    }

    public Product getProdById(Integer id) {
        Optional<Product> optionalProd = prodRepository.findById(id);

        if (optionalProd.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prod not found with id: " + id);
        }
//        gets the prod out of the optional container and returns it to the controller
        return optionalProd.get();
    }

    public Product addProd(Product prod) {
        return prodRepository.save(prod);
    }

    public Product updateProd(Integer id, Product updates) {
        Product prodToUpdate = getProdById(id);

        if (updates.getURL() != null) {
            prodToUpdate.setURL(updates.getURL());
        }

        if (!updates.getName().isEmpty()) {
            prodToUpdate.setName(updates.getName());
        }

        if (updates.getDescription() != null) {
            prodToUpdate.setDescription(updates.getDescription());
        }

        if (updates.getPrice() != null) {
            prodToUpdate.setPrice(updates.getPrice());
        }

        if (updates.getQuantity() != null) {
            prodToUpdate.setQuantity(updates.getQuantity());
        }

        return prodRepository.save(prodToUpdate);
    }

    public HashMap<String, Object> deleteProd(Integer id) {
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Product> optionalProd = prodRepository.findById(id);

        if (optionalProd.isEmpty()) {
//            if the prod does not exist, this is what will be returned
            responseMap.put("wasDeleted", false);
            responseMap.put("prodInfo", null);
            responseMap.put("Message", "Prod not found with id: " + id);
            return responseMap;
        }
        prodRepository.deleteById(id);
        responseMap.put("wasDeleted", true);
        responseMap.put("prodInfo", optionalProd.get());

        return responseMap;
    }

}


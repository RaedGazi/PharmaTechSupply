package com.pharmatechsupply.pharma_api_db.repositories;

import com.pharmatechsupply.pharma_api_db.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProdRepository extends CrudRepository<Product,Integer> {
}


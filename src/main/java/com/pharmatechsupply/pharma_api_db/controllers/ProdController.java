package com.pharmatechsupply.pharma_api_db.controllers;

import com.pharmatechsupply.pharma_api_db.entities.Product;
import com.pharmatechsupply.pharma_api_db.services.ProdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api/supplies")
@CrossOrigin(origins = "*")
public class ProdController {
    private final ProdService prodService;

    public ProdController(ProdService prodService) {
        this.prodService = prodService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAllProducts(){
        return ResponseEntity.ok(prodService.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProdById(@PathVariable Integer id){
        return ResponseEntity.ok(prodService.getProdById(id));
    }

    @PostMapping
    public ResponseEntity<Product> addProd(@RequestBody Product prod){
        Product savedProd = prodService.addProd(prod);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/supplies/{id}")
                .buildAndExpand(savedProd.getId())
                .toUri();

        return ResponseEntity.created(location).body(prod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product>  updatedProd(@PathVariable Integer id, @RequestBody Product updates){
        return ResponseEntity.ok(prodService.updateProd(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteProd(@PathVariable Integer id){

        HashMap<String, Object> responseMap = prodService.deleteProd(id);

        if(responseMap.get("prodInfo") == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }
}
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
    private final ProdService supplyService;

    public ProdController(ProdService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAllSupplys(){
        return ResponseEntity.ok(supplyService.findAllSupplies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSupplyById(@PathVariable Integer id){
        return ResponseEntity.ok(supplyService.getSupplyById(id));
    }

    @PostMapping
    public ResponseEntity<Product> addSupply(@RequestBody Product supply){
        Product savedSupply = supplyService.addSupply(supply);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/supplies/{id}")
                .buildAndExpand(savedSupply.getId())
                .toUri();

        return ResponseEntity.created(location).body(supply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product>  updatedSupply(@PathVariable Integer id, @RequestBody Product updates){
        return ResponseEntity.ok(supplyService.updateSupply(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteSupply(@PathVariable Integer id){

        HashMap<String, Object> responseMap = supplyService.deleteSupply(id);

        if(responseMap.get("supplyInfo") == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import org.aittam.lsdcalc.contracts.ConvertFromPenceResponse;
import org.aittam.lsdcalc.contracts.ConvertToPenceRequest;
import org.aittam.lsdcalc.contracts.ConvertToPenceResponse;
import org.aittam.lsdcalc.contracts.ErrorResponse;
import org.aittam.lsdcalc.contracts.FactorOperationRequest;
import org.aittam.lsdcalc.contracts.OperationRequest;
import org.aittam.lsdcalc.contracts.OperationResponse;
import org.aittam.lsdcalc.contracts.ReminderOperationResponse;
import org.aittam.lsdcalc.utils.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mlanzoni
 */
@RestController
@RequestMapping("/api/public/v1/calc")
public class CalcRestController {
    
    private final Logger logger = LoggerFactory.getLogger(CalcRestController.class);
    
    private ConverterService cService;
    
    public CalcRestController(ConverterService cService) {
        this.cService = cService;
    }
    
    @GetMapping("/convert")
    ResponseEntity convertFromPence(@RequestParam(required = true, value = "pence") long pence) {
        ConvertFromPenceResponse res = new ConvertFromPenceResponse();
        res.value = cService.convertFromPence(pence);
        return new ResponseEntity<ConvertFromPenceResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/convert")
    ResponseEntity convertToPence(@RequestBody ConvertToPenceRequest req) {
        ConvertToPenceResponse res = new ConvertToPenceResponse();
        res.value = cService.convertToPence(req.value);
        return new ResponseEntity<ConvertToPenceResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    ResponseEntity add(@RequestBody OperationRequest req) {
        long term1 = cService.convertToPence(req.term1);
        long term2 = cService.convertToPence(req.term2);
        long sum = term1 + term2;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(sum);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/subtract")
    ResponseEntity subtract(@RequestBody OperationRequest req) {
        long term1 = cService.convertToPence(req.term1);
        long term2 = cService.convertToPence(req.term2);
        long sub = term1 - term2;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(sub);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/multiply")
    ResponseEntity multiply(@RequestBody FactorOperationRequest req) {
        long term = cService.convertToPence(req.term);
        long prod = term * req.factor;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(prod);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/divide")
    ResponseEntity divide(@RequestBody FactorOperationRequest req) {
        if (req.factor == 0)
            throw new BadRequestException("Division by ZERO not allowed");
                    
        long term = cService.convertToPence(req.term);
        long quot = Math.floorDiv(term, req.factor);
        long rem = Math.floorMod(term, req.factor);
        
        ReminderOperationResponse res = new ReminderOperationResponse();
        res.result = cService.convertFromPence(quot);
        res.reminder = cService.convertFromPence(rem);
        
        return new ResponseEntity<ReminderOperationResponse>(res, HttpStatus.OK);
    }
    
}

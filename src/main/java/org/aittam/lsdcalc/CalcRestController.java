/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    
    @Operation(summary = "Convert numeric value expressed in pennies to £sd format.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "2962 = 12p 6s 10d<br/>" + 
                            "2952 = 12p 6s<br/>" + 
                            "2890 = 12p 10d<br/>" + 
                            "  82 = 6s 10d<br/>" + 
                            "2880 = 12p<br/>" + 
                            "  72 = 6s<br/>" + 
                            "  10 = 10d<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = ConvertFromPenceResponse.class)) 
                            }),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
    @GetMapping("/convert")
    ResponseEntity convertFromPence(@RequestParam(required = true, value = "pence") long pence) {
        ConvertFromPenceResponse res = new ConvertFromPenceResponse();
        res.value = cService.convertFromPence(pence);
        return new ResponseEntity<ConvertFromPenceResponse>(res, HttpStatus.OK);
    }
    
    @Operation(summary = "Convert a string value in £sd format to pennies.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "12p 6s 10d      = 2962<br/>" + 
                            "12p 6s          = 2952<br/>" + 
                            "12p    10d      = 2890<br/>" + 
                            "6s 10d          = 82<br/>" + 
                            "12p             = 2880<br/>" + 
                            "6s              = 72<br/>" + 
                            "10d             = 10<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = ConvertToPenceResponse.class)) 
                            }),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
    @PostMapping("/convert")
    ResponseEntity convertToPence(@RequestBody ConvertToPenceRequest req) {
        ConvertToPenceResponse res = new ConvertToPenceResponse();
        res.value = cService.convertToPence(req.value);
        return new ResponseEntity<ConvertToPenceResponse>(res, HttpStatus.OK);
    }
    
    @Operation(summary = "Sum two numbers expressed in £sd format.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "5p 17s 8d + 3p 4s 10d = 9p 2s 6d<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = OperationResponse.class)) 
                            }),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
    @PostMapping("/add")
    ResponseEntity add(@RequestBody OperationRequest req) {
        long term1 = cService.convertToPence(req.term1);
        long term2 = cService.convertToPence(req.term2);
        long sum = term1 + term2;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(sum);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @Operation(summary = "Subtract two numbers expressed in £sd format.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "5p 17s 8d - 3p 4s 10d = 2p 12s 10d<br/>" +
                            "3p 4s 10d - 5p 17s 8d = - 2p 12s 10d<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = OperationResponse.class)) 
                            }),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
    @PostMapping("/subtract")
    ResponseEntity subtract(@RequestBody OperationRequest req) {
        long term1 = cService.convertToPence(req.term1);
        long term2 = cService.convertToPence(req.term2);
        long sub = term1 - term2;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(sub);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @Operation(summary = "Multiply a number expressed in £sd format by a another number.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "5p 17s 8d * 2  =   11p 15 s 4d<br/>" +
                            "5p 17s 8d * -2 = - 11p 15 s 4d<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = OperationResponse.class)) 
                            }),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
    @PostMapping("/multiply")
    ResponseEntity multiply(@RequestBody FactorOperationRequest req) {
        long term = cService.convertToPence(req.term);
        long prod = term * req.factor;
        
        OperationResponse res = new OperationResponse();
        res.result = cService.convertFromPence(prod);
        
        return new ResponseEntity<OperationResponse>(res, HttpStatus.OK);
    }
    
    @Operation(summary = "Divide a number expressed in £sd format by a another number. The operation will also return the reminder.", 
               description = "<strong>Examples:</strong><br/>" + 
                            "5p 17s 8d  / 0  = BAD REQUEST<br/>" +
                            "5p 17s 8d  / 3  = 1p 19s 2d (2p)<br/>" +
                            "18p 16s 1d / 15 = 1p 5s 0d (1s 1d)<br/>")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesful conversion", 
                    content = { @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = ReminderOperationResponse.class)) 
                            }),
        @ApiResponse(responseCode = "400", description = "Division by ZERO.", content = @Content),
        @ApiResponse(responseCode = "500", description = "The conversion encountered an unrecoverable error.", content = @Content)
    })
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

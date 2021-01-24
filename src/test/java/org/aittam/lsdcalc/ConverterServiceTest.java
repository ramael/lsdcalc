/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 *
 * @author mlanzoni
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ConverterServiceTest {
    /*
        12p 6s 10d      = 2962
        12p 6s          = 2952
        12p    10d      = 2890
            6s 10d      = 82
        12p             = 2880
            6s          = 72    
               10d      = 10
    */
    
    @Autowired
    private ConverterService cService;
    
    @Test
    void convertFromPence_0_0d() throws Exception {
        // Arrange
       
        // Act
        String res = cService.convertFromPence(0);
        
        // Assert
        assertEquals(res, "0d");
    }
    
    @Test
    void convertFromPence_2962_12p6s10d() throws Exception {
        String res = cService.convertFromPence(2962);
        assertEquals(res, "12p 6s 10d");
    }
    
    @Test
    void convertFromPence_2962_12p6s10d_neg() throws Exception {
        String res = cService.convertFromPence(-2962);
        assertEquals(res, "- 12p 6s 10d");
    }
    
    @Test
    void convertFromPence_2952_12p6s0d() throws Exception {
        String res = cService.convertFromPence(2952);
        assertEquals(res, "12p 6s 0d");
    }
    
    @Test
    void convertFromPence_2890_12p0s10d() throws Exception {
        String res = cService.convertFromPence(2890);
        assertEquals(res, "12p 0s 10d");
    }
    
    @Test
    void convertFromPence_82_0p6s10d() throws Exception {
        String res = cService.convertFromPence(82);
        assertEquals(res, "0p 6s 10d");
    }
    
    @Test
    void convertFromPence_2880_12p0s0d() throws Exception {
        String res = cService.convertFromPence(2880);
        assertEquals(res, "12p 0s 0d");
    }
    
    @Test
    void convertFromPence_72_0p6s0d() throws Exception {
        String res = cService.convertFromPence(72);
        assertEquals(res, "0p 6s 0d");
    }
    
    @Test
    void convertFromPence_10_0p0s10d() throws Exception {
        String res = cService.convertFromPence(10);
        assertEquals(res, "0p 0s 10d");
    }
    
    @Test
    void convertToPence__0() throws Exception {
        // Arrange
        
        // Act
        long res = cService.convertToPence("");
        
        // Assert
        assertEquals(res, 0);
    }
    
    @Test
    void convertToPence_throw_IllegalArgumentException() throws Exception {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cService.convertToPence("12z 6y 10z");
        });
    }
    
    @Test
    void convertToPence_12p6s10d_2962() throws Exception {
        long res = cService.convertToPence("12p 6s 10d");
        assertEquals(res, 2962);
    }    
    
    @Test
    void convertToPence_12p6s10d_2962_neg() throws Exception {
        long res = cService.convertToPence("- 12p 6s 10d");
        assertEquals(res, -2962);
    }    
    
    @Test
    void convertToPence_12p6s0d_2952() throws Exception {
        long res = cService.convertToPence("12p 6s 0d");
        assertEquals(res, 2952);
    }    
    
    @Test
    void convertToPence_12p0s10d_2890() throws Exception {
        long res = cService.convertToPence("12p 0s 10d");
        assertEquals(res, 2890);
    }    
    
    @Test
    void convertToPence_0p6s10d_82() throws Exception {
        long res = cService.convertToPence("0p 6s 10d");
        assertEquals(res, 82);
    }    
    
    @Test
    void convertToPence_12p0s0d_2880() throws Exception {
        long res = cService.convertToPence("12p 0s 0d");
        assertEquals(res, 2880);
    }    
    
    @Test
    void convertToPence_0p6s0d_72() throws Exception {
        long res = cService.convertToPence("0p 6s 0d");
        assertEquals(res, 72);
    }    
    
    @Test
    void convertToPence_0p0s10d_10() throws Exception {
        long res = cService.convertToPence("0p 0s 10d");
        assertEquals(res, 10);
    }    
}

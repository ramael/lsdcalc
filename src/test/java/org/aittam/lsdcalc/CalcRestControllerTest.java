/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aittam.lsdcalc.contracts.ConvertToPenceRequest;
import org.aittam.lsdcalc.contracts.FactorOperationRequest;
import org.aittam.lsdcalc.contracts.OperationRequest;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author mlanzoni
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CalcRestControllerTest {
    /*
        5p 17s 8d + 3p 4s 10d   = 9p 2s 6d
        5p 17s 8d - 3p 4s 10d   = 2p 12s 10d
        3p 4s 10d - 5p 17s 8d   = - 2p 12s 10d
        5p 17s 8d * 2           = 11p 15 s 4d
        5p 17s 8d * -2          = - 11p 15 s 4d
        5p 17s 8d / 0           = null
        5p 17s 8d / 3           = 1p 19s 2d (2p)
        18p 16s 1d / 15         = 1p 5s 0d (1s 1d)
    */
    
    @Autowired
    private MockMvc mockMvc;
    
    private ObjectMapper om = new ObjectMapper();
        
    @Test
    void add_5p17s8d_3p4s10d() throws Exception {
        OperationRequest req = new OperationRequest() {{
            term1 = "5p 17s 8d";
            term2 = "3p 4s 10d";
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/add")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("9p 2s 6d"))
                .andDo(print());
    }
    
    @Test
    void subtract_5p17s8d_3p4s10d() throws Exception {
        OperationRequest req = new OperationRequest() {{
            term1 = "5p 17s 8d";
            term2 = "3p 4s 10d";
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/subtract")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("2p 12s 10d"))
                .andDo(print());
    }
    
    @Test
    void subtract_3p4s10d_5p17s8d() throws Exception {
        OperationRequest req = new OperationRequest() {{
            term1 = "3p 4s 10d";
            term2 = "5p 17s 8d";
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/subtract")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("- 2p 12s 10d"))
                .andDo(print());
    }
    
    @Test
    void multiply_5p17s8d_2() throws Exception {
        FactorOperationRequest req = new FactorOperationRequest() {{
            term = "5p 17s 8d";
            factor = 2;
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/multiply")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("11p 15s 4d"))
                .andDo(print());
    }
    
    @Test
    void multiply_5p17s8d_2_neg() throws Exception {
        FactorOperationRequest req = new FactorOperationRequest() {{
            term = "5p 17s 8d";
            factor = -2;
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/multiply")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("- 11p 15s 4d"))
                .andDo(print());
    }
    
    @Test
    void divide_5p17s8d_0() throws Exception {
        FactorOperationRequest req = new FactorOperationRequest() {{
            term = "5p 17s 8d";
            factor = 0;
        }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/divide")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @Test
    void divide_5p17s8d_3() throws Exception {
        FactorOperationRequest req = new FactorOperationRequest() {{
            term = "5p 17s 8d";
            factor = 3;
        }};        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/divide")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("1p 19s 2d"))
                .andExpect(jsonPath("$.reminder").value("0p 0s 2d"))
                .andDo(print());
    }

    @Test
    void divide_18p16s1d_15() throws Exception {
        FactorOperationRequest req = new FactorOperationRequest() {{
            term = "18p 16s 1d";
            factor = 15;
        }};        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/divide")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.result").value("1p 5s 0d"))
                .andExpect(jsonPath("$.reminder").value("0p 1s 1d"))
                .andDo(print());
    }
}

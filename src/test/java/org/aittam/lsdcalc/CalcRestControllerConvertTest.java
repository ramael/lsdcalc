/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aittam.lsdcalc.contracts.ConvertToPenceRequest;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author mlanzoni
 */
@WebMvcTest(CalcRestController.class)
public class CalcRestControllerConvertTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConverterService cService;
    
    @Test
    void convertFromPence() throws Exception {
        when(cService.convertFromPence(0)).thenReturn("0d");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/public/v1/calc/convert?pence={pence}", (long)0)
                                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.value").value("0d"))
                .andDo(print());
    }
    
    @Test
    void convertToPence() throws Exception {
        when(cService.convertToPence("")).thenReturn((long)0);

        ObjectMapper om = new ObjectMapper();
        ConvertToPenceRequest req = new ConvertToPenceRequest() {{ value = ""; }};
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/v1/calc/convert")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .characterEncoding("UTF-8")
                                            .content(om.writeValueAsString(req))
                )
                .andExpect(jsonPath("$.value").value((long) 0))
                .andDo(print());
    }
}

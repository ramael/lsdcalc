/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author mlanzoni
 */
public class FactorOperationRequest implements Serializable {
    
    @JsonProperty("term")
    public String term;
    
    @JsonProperty("factor")
    public int factor;
}

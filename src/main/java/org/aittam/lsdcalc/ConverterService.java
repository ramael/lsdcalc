/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 *
 * @author mlanzoni
 */
@Service
public class ConverterService {
    private static final String LSD_REGEX = "^((?<pounds>\\d*?)p)?\\s*((?<shillings>\\d*?)s)?\\s*((?<pence>\\d*?)d)?$";
    
    public String convertFromPence(long pence) {
        if (pence == 0)
            return "0d";
        
        String res = "";
	
	// allow for negative values
	if (pence < 0) {
            res += "- ";
            pence *= -1;
	}
	
	// pounds
	long pounds = Math.floorDiv(pence, 240);
	pence = Math.floorMod(pence, 240);
	res += pounds + "p ";
	
	// shillings
	long shillings = Math.floorDiv(pence, 12);
        pence = Math.floorMod(pence, 12);
        res += shillings + "s ";
	
	// pence
        res += pence + "d";
	
	return res;	
    }
    
    public long convertToPence(String value) {
        if (value == null || value.isEmpty())
            return 0;
        
        int k = 1;
        value = value.trim();
        if (value.startsWith("-")) {
            k = -1;
            value = value.replace("-", "").trim();
        }
        
        final Pattern pattern = Pattern.compile(LSD_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unsupported LSD format: " + value);
        }
            
        String sPounds = matcher.group("pounds");       
        String sShillings = matcher.group("shillings");
        String sPence = matcher.group("pence");
        
        long pounds = (sPounds == null ? 0 : Long.parseLong(sPounds));
        long shillings = (sShillings == null ? 0 : Long.parseLong(sShillings));
        long pence = (sPence == null ? 0 : Long.parseLong(sPence));
        
        return k * (pounds * 240 + shillings * 12 + pence);
        
    }
    
}

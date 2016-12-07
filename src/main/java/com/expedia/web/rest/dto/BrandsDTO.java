package com.expedia.web.rest.dto;

import java.util.HashMap;

/**
 * Created by rguleria on 02/11/16.
 */
public class BrandsDTO {

    HashMap<String,HashMap<Integer,String>> brandMap;

    public BrandsDTO(){
        this.brandMap = new HashMap<>();
    }

    public BrandsDTO(HashMap<String, HashMap<Integer, String>> brandMap) {
        this.brandMap = brandMap;
    }

    public HashMap<String, HashMap<Integer, String>> getBrandMap() {
        return brandMap;
    }

    public void setBrandMap(HashMap<String, HashMap<Integer, String>> brandMap) {
        this.brandMap = brandMap;
    }
}

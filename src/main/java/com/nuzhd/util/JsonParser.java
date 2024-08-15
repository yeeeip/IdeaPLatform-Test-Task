package com.nuzhd.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonParser {

    public JsonNode parseJson(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootObj = mapper.readTree(new File(fileName)).get("tickets");
        return rootObj;
    }


}

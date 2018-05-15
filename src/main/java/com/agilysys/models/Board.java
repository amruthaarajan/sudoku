package com.agilysys.models;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amruthaa on 5/14/18.
 */

@Document(collection = "board")

@Data
public class Board {

    @Id
    String id;

    List<List<Integer>> data;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getId() {
        return id;
    }

    public List<List<Integer>> getData() {
        return data;
    }
}

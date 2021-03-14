package com.logotet.m.data.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter {
        private Gson gson = new Gson();


        @TypeConverter
        public List<String> jsonToList(String dataHours) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                return gson.fromJson(dataHours, listType);
        }

        @TypeConverter
        public String listToJson(List<String> hours) {
                return gson.toJson(hours);
        }
}

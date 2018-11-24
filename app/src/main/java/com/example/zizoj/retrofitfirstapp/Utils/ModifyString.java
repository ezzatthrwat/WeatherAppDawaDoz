package com.example.zizoj.retrofitfirstapp.Utils;

public class ModifyString {

    public static String showOnlyDate(String Time ){

        StringBuilder sb = new StringBuilder();
        sb.append(Time);
        return  sb.toString().substring(0,11);


    }

}

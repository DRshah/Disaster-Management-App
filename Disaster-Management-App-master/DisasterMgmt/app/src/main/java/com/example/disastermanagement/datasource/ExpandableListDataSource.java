package com.example.disastermanagement.datasource;

import android.content.Context;

import com.example.disastermanagement.R;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Darsh_Shah on 24-03-2018.
 */

public class ExpandableListDataSource {
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();
        List<String> product_list = Arrays.asList(context.getResources().getStringArray(R.array.product_group));
        List<String> vegetables = Arrays.asList(context.getResources().getStringArray(R.array.Vegetables));


        expandableListData.put(product_list.get(0), vegetables);


        return expandableListData;
    }
}

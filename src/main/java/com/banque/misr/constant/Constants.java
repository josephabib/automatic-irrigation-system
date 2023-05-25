package com.banque.misr.constant;

import com.banque.misr.models.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static HashMap<Plot, String> plotCronJobs = new HashMap<>();

    public static List<Long> threadCronJobs = new ArrayList<>();
}

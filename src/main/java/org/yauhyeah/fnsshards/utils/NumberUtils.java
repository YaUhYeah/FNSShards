package org.yauhyeah.fnsshards.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberUtils {
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



    public static int fastRandom(int min, int max){
        List<Integer> range = IntStream.range(min, max).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);
        return range.get((int) Math.floor(Math.random()*12));
    }

    public static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        return df.format(value);
    }


}

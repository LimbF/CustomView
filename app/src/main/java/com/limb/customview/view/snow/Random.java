package com.limb.customview.view.snow;

/**
 * Created by limb on 2016/5/5.
 */
public class Random {

    private static final java.util.Random RANDOM = new java.util.Random();

    public float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);//返回两者较小的一个。
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;//返回的是比最大的小，比最小的大的数。
    }

    public float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;//Random.nextFloat()生成[0~1)的数.
    }

    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);//随机生成比[0~upper)的数值。
    }

    public int getColor() {
        return RANDOM.nextInt(255);//随机生成[0~255)整数。

    }
}
package com.example.anuradha.regression.models;

/**
 * Created by anuradha on 8/20/17.
 */

public interface Regressor {

    String name();

    float recognize(final float[] pixels);
}

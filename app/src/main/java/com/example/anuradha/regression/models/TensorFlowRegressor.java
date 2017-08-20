package com.example.anuradha.regression.models;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.List;
//made by google, used as the window between android and tensorflow native C++
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by anuradha on 8/20/17.
 */

public class TensorFlowRegressor implements Regressor {


    // Only returns if at least this confidence
    //must be a classification percetnage greater than this
    private static final float THRESHOLD = 0.1f;

    private TensorFlowInferenceInterface tfHelper;

    private String name;
    private String inputName;
    private String outputName;
    private int inputSize;
    private boolean feedKeepProb;

    private List<String> labels;
    private float[] output;
    private String[] outputNames;

    @Override
    public String name() {
        return null;
    }

    @Override
    public float recognize(float[] pixels) {
        //using the interface
        //give it the input name, raw pixels from the drawing,
        //input size
        tfHelper.feed(inputName, pixels,1,inputSize);

        //get the possible outputs
        tfHelper.run(outputNames);

        //get the output
        tfHelper.fetch(outputName, output);

        // Find the best classification
        //for each output prediction
        //if its above the threshold for accuracy we predefined
        //write it out to the view
        float res = 0;
        for (int i = 0; i < output.length; ++i) {
             res = output[i];
            System.out.println(" Regression out put"+output[i]);

        }

        return res;

    }

    //given a model, its label file, and its metadata
    //fill out a classifier object with all the necessary
    //metadata including output prediction
    public static TensorFlowRegressor create(AssetManager assetManager, String name,
                                              String modelPath, int inputSize, String inputName, String outputName,
                                              boolean feedKeepProb) throws IOException {
        //intialize a classifier
        TensorFlowRegressor c = new TensorFlowRegressor();

        //store its name, input and output labels
        c.name = name;

        c.inputName = inputName;
        c.outputName = outputName;


        //set its model path and where the raw asset files are
        c.tfHelper = new TensorFlowInferenceInterface(assetManager, modelPath);
        int numClasses = 10;

        //how big is the input?
        c.inputSize = inputSize;

        // Pre-allocate buffer.
        c.outputNames = new String[] { outputName };

        c.outputName = outputName;
        c.output = new float[1];

        c.feedKeepProb = feedKeepProb;

        return c;
    }
}

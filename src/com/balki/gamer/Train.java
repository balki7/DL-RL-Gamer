package com.balki.gamer;

import java.io.IOException;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * 
 * @author Balki
 * @since 17/12/2018
 *
 */
public class Train {

	public static void main(String[] args) throws IOException {
		// 1. Step: Load features and labels from resources
		Nd4j.setDataType(DataBuffer.Type.DOUBLE);
		INDArray features = Nd4j.createFromNpyFile(new ClassPathResource("features.npy").getFile());
		INDArray labels = Nd4j.createFromNpyFile(new ClassPathResource("labels.npy").getFile());

		// 2. Step: Create a DL4J DataSet and split into training and test data.
		DataSet allData = new DataSet(features, labels);
		SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.9);
		DataSet trainingData = testAndTrain.getTrain();
		DataSet testData = testAndTrain.getTest();

		// 3. Step: Define parameters and configure a convolutional neural network.
		int size = 8;
		int featurePlanes = 4;
		int boardSize = 8 * 8;
		int randomSeed = 1337;

		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(randomSeed).learningRate(.1)
				.weightInit(WeightInit.XAVIER).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
				.updater(Updater.ADAGRAD).list()
				.layer(0,
						new ConvolutionLayer.Builder(3, 3).nIn(featurePlanes).stride(1, 1).nOut(50)
						.activation(Activation.RELU).build())
				.layer(1,
						new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2).stride(1, 1)
						.build())
				.layer(2, new ConvolutionLayer.Builder(3, 3).stride(1, 1).nOut(20).activation(Activation.RELU).build())
				.layer(3,
						new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2).stride(1, 1)
						.build())
				.layer(4, new DenseLayer.Builder().activation(Activation.RELU).nOut(500).build())
				.layer(5,
						new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).nOut(boardSize)
						.activation(Activation.SOFTMAX).build())
				.setInputType(InputType.convolutional(size, size, featurePlanes)).backprop(true).pretrain(false)
				.build();

		// 4. Step: Initialize a MultiLayerNetwork and fit it to training data.
		MultiLayerNetwork model = new MultiLayerNetwork(conf);
		model.init();
		model.setListeners(new ScoreIterationListener(10));
		model.fit(trainingData);

		// 5. Step: Evaluate the model on the test set
		Evaluation eval = new Evaluation(boardSize);
		INDArray output = model.output(testData.getFeatureMatrix());
		eval.eval(testData.getLabels(), output);
		System.out.println(eval.stats());
	}

}

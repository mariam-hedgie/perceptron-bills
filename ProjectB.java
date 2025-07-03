import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

/**
* Author: Mariam Husain
* JHED: mhusai10
* Date: 01/09/2025
* Reads a training file of data, trains a model
* to determine the weights of the features. 
* Tests the model on another file of data 
* and writes the weights and predictions.
* Evaluates how accurate the model is.
*/

public class ProjectB {
   public static void main(String[] args) throws IOException {
    
        // count the number of rows in the training file and reads the data
      int trainingRows = countRows("training.txt");
      double[][] trainingData = readData("training.txt", trainingRows);
      int[] trainingClassifications = 
         readClassification("training.txt", trainingRows);

        // creates a 1D array for weights
      double[] weights = new double[4]; 

        // train perceptron model on training file
      weights = trainPerceptron(trainingData, trainingClassifications, 1);

        // output weights to a new file
      outputWeights("weights.txt", weights);

        // count the number of rows in the validate file and reads the data
      int validateRows = countRows("validate.txt");
      double[][] validationData = readData("validate.txt", validateRows);
      int[] validateClassification = 
         readClassification("validate.txt", validateRows);

        // predict classifications and output them to a new file
      int[] predictions = 
         makePredictions(validationData, weights, validateClassification);
      writePredictions("predict.txt", validationData, predictions);
        
        // print statistics
      int numCorrect = countCorrect(predictions, validateClassification);
      System.out.println("correct predictions: " + numCorrect);
      System.out.println("incorrect predictions: " 
         + (predictions.length - numCorrect));      
      double percentCorrect = (double) numCorrect / predictions.length * 100;
      System.out.println("percent correct: " + percentCorrect);

   }

   /**
   * counts the number of rows (records) in the file.
   * @param filename
   * @return num of rows
   */
   public static int countRows(String filename) throws IOException {
         
        
      Scanner sc = new Scanner(new File(filename));
      int count = 0;
      while (sc.hasNextLine()) {
            // remove unwanted spaces and count line if it's not empty
         String row = sc.nextLine().trim();
         if (!row.isEmpty()) {
            count++;
         }
      }
      sc.close();
      return count;
   }
   
   /**
   * reads data (first 4 columns) from a file into a 2D array.
   * @param filename 
   * @param numRows
   * @return data from file
   */
   public static double[][] 
      readData(String filename, int numRows) throws IOException {
 
        
      Scanner sc = new Scanner(new File(filename));
      double[][] data = new double[numRows][4]; 
      int row = 0;
        
      while (sc.hasNextLine()) {
            // splits every line into separate values and adds them to data.
            
         String line = sc.nextLine().trim();
         if (!line.isEmpty()) {
            String[] values = line.split("\\s+");
            for (int i = 0; i < 4; i++) { 
                  // reads first 4 values (columns)
                  
               data[row][i] = Double.parseDouble(values[i]);
            }
            row++;  
         }
      }
      sc.close();
      return data;
   }

   /**
   * reads the last column (classification of records) from a file.
   * it converts 0 to 1 and 1 to -1
   * and returns a 1D array of the classification data
   * @param filename 
   * @param numRows
   * @return array of classifications
   */
   public static int[] 
      readClassification(String filename, int numRows) throws IOException {
        
      Scanner sc = new Scanner(new File(filename));
      int[] classificationData = new int[numRows];
      int row = 0;
        
      while (sc.hasNextLine()) {
            // reads every line and splits it into values
            
         String line = sc.nextLine().trim();
         if (!line.isEmpty()) {
            String[] values = line.split("\\s+");
                
                
            if (Integer.parseInt(values[4]) == 0) {
               classificationData[row] = 1;
            }
            else {
               classificationData[row] = -1;
            } 
         }
         row++;
      }
      sc.close();
      return classificationData;
   }
   
   /**
   * adds two arrays together.
   * @param a
   * @param b
   * @return sum of both arrays
   */
   public static double[] arrayAdd(double[] a, double[] b) {
        
      double[] sum = new double[a.length];
      for (int i = 0; i < a.length; i++) {
         sum[i] = a[i] + b[i];
      }
      return sum;
   }
   
   /**
   * calculates the dot product (pairwise sum) of 2 1D arrays.
   * @param a
   * @param b
   * @return dot product of 2 arrays
   */
   public static double dotProduct(double[] a, double[] b) {
        
      double dotProd = 0.0;
      for (int i = 0; i < a.length; i++) {
         dotProd += a[i] * b[i];
      }
      return dotProd;
   }
   
   /**
   * calculates the product of a scalar and a 1D array.
   * @param scalar
   * @param a
   * @return scalar products of a constant and an array
   */
   public static double[] scalarProduct(double scalar, double[] a) {
      
      double[] scalarProduct =  new double[a.length];
      for (int i = 0; i < a.length; i++) {
         scalarProduct[i] = scalar * a[i];
      }
      return scalarProduct;
   }
   
   /**
   * trains the model for a given number of iterations.
   * uses training to train a 1D array of weights.
   * @param x
   * @param y
   * @param numIterations
   * @return weights array after model is trained. 
   */ 
   public static double[] 
      trainPerceptron(double[][] x, int[] y, int numIterations) {
      
      double[] weights = new double[x[0].length];
      for (int i = 0; i < numIterations; i++) {
         for (int j = 0; j < x.length; j++) {
            double classification;
            if (dotProduct(weights, x[j]) >= 0) {
               classification = 1;
            }
            else {
               classification = -1;
            }
            if (classification != y[j]) {
               weights = arrayAdd(weights, scalarProduct(y[j], x[j])); 
            }   
         }
      }
      return weights;
   }
    
   /**
   * outputs all the weights into a new file.
   * @param filename
   * @param weights
   */
   public static void 
      outputWeights(String filename, double[] weights) throws IOException {
        
      PrintWriter writer = new PrintWriter(new FileWriter(filename));
      for (int i = 0; i < weights.length; i++) {
         writer.printf("%.5f", weights[i]);
         writer.println();
      }
      writer.close();
   }

   /**
   * makes predictions for the classifications based on the trained weights.
   * @param validationFeatures
   * @param weights
   * @param actualResults
   * @return 1D array of predictions
   */
   public static int[] 
      makePredictions(double[][] validationFeatures, 
         double[] weights, int[] actualResults) {
        
      int[] predictions = new int[validationFeatures.length];
      for (int i = 0; i < validationFeatures.length; i++) {
         predictions[i] = 
            (dotProduct(validationFeatures[i], weights)) >= 0 ? 1 : -1;
      }
      return predictions;
   }
   
   /**
   * writes predictions to a new file.
   * @param filename
   * @param features
   * @param predictions
   */
   public static void 
      writePredictions(String filename, double[][] features, 
        int[] predictions) throws IOException {
        
      PrintWriter writer = new PrintWriter(new FileWriter(filename));
      for (int i = 0; i < features.length; i++) {
         for (int j = 0; j < features[i].length; j++) {
            writer.printf("%.5f ", features[i][j]);
         }
         writer.printf("%d\n", predictions[i]);
      }
      writer.close();
   }

   /**
   * counts number of correct predictions (with respect to validate file).
   * @param predictions
   * @param actual
   * @return number of correct predictions
   */
   public static int countCorrect(int[] predictions, int[] actual) {
        
      int numCorrect = 0;
      for (int i = 0; i < predictions.length; i++) {
         if (predictions[i] == actual[i]) {
            numCorrect++;
         }
      }
      return numCorrect;
   }


}
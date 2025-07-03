# Perceptron Classifier
This project is from my college Java class

## 📌 Overview

This project implements a simple **Perceptron** binary classifier in Java to distinguish between **authentic** and **forged banknotes**. It uses a linearly separable model trained on image-derived statistical features (variance, skewness, kurtosis, entropy) to predict currency authenticity.

---

## 📁 Files

- `ProjectB.java` — main Java source file
- `training.txt` — input data for training (with labels)
- `validate.txt` — input data for testing (with labels)
- `weights.txt` — generated during training (one line per iteration of weights)
- `predict.txt` — generated after prediction (same format as input, with predicted labels)

---

## How to Run

### 🧵 Step-by-step:

1. **Place all files** (`ProjectB.java`, `training.txt`, `validate.txt`) in the same folder.
2. Open terminal and navigate to that folder.
3. Compile the Java file:
   ```bash
   javac ProjectB.java
   ```
4. Run the program:
   ``` java ProjectB
   ```

The program will: <br>
	•	Train the perceptron on training.txt <br>
	•	Generate weights.txt with weight updates <br>
	•	Classify records in validate.txt <br>
	•	Write results to predict.txt <br>
	•	Print accuracy to the console <br>

---

## 🎯 Sample Output
With the provided training and validation files:
```
Correct predictions: 264  
Incorrect predictions: 60  
Percent correct: 81.48%
```





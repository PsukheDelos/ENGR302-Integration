package com.example.colin.cats;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * The main class indicating how to use the algorithm properly
 *
 */
public class Algorithm {

	public static void processImages(Bitmap left, Bitmap middle, Bitmap right, Context c){

        WhiteBalance balancer = new WhiteBalance();
		Bitmap balancedLeft = balancer.balance(left, middle);
		Bitmap balancedRight = balancer.balance(right, middle);

		HashMap<Double, Integer> nitrateColours = new HashMap<>();
		HashMap<Double, Integer> nitriteColours = new HashMap<>();

		nitriteColours.put(1., R.integer.Nitrite1);
		nitriteColours.put(3., R.integer.Nitrite3);
		nitriteColours.put(1.5, R.integer.Nitrite1_5);
		nitriteColours.put(0.3, R.integer.Nitrite0_3);
		nitriteColours.put(0., R.integer.Nitrite0);
		nitriteColours.put(0.15, R.integer.Nitrite0_15);

        nitrateColours.put(10., R.integer.Nitrate10);
        nitrateColours.put(0., R.integer.Nitrate0);
        nitrateColours.put(1., R.integer.Nitrate1);
        nitrateColours.put(2., R.integer.Nitrate2);
        nitrateColours.put(50., R.integer.Nitrate50);
        nitrateColours.put(20., R.integer.Nitrate20);
        nitrateColours.put(5., R.integer.Nitrate5);

        ColorRecog nitrateRecog = new ColorRecog(nitrateColours);
        ColorRecog nitriteRecog = new ColorRecog(nitriteColours);

		//Do the analysis
		Map<Double, Double> nitrateAnalysis = nitrateRecog.processImage(balancedRight);
		Map<Double, Double> nitriteAnalysis = nitriteRecog.processImage(balancedLeft);

		Map.Entry<Double, Double> bestNitriteClass = null;
		Map.Entry<Double, Double> bestNitrateClass = null;
		Map.Entry<Double, Double> secBestNitriteClass = null;
		Map.Entry<Double, Double> secBestNitrateClass = null;

		for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
			if(bestNitrateClass == null){
				bestNitrateClass = nitrateClass;
				continue;
			}
			else if(secBestNitrateClass == null){
				secBestNitrateClass = nitrateClass;
				continue;
			}

			if(nitrateClass.getValue() > bestNitrateClass.getValue()){
				bestNitrateClass = nitrateClass;
			}
			else if(nitrateClass.getValue() > secBestNitrateClass.getValue()){
				secBestNitrateClass = nitrateClass;
			}
		}

		for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()){
			Toast.makeText(c, ""+nitriteClass.getKey() + "="+ nitriteClass.getValue(), Toast.LENGTH_LONG).show();
			if(bestNitriteClass == null){
				bestNitriteClass = nitriteClass;
				continue;
			}
			else if(secBestNitriteClass == null){
				secBestNitriteClass = nitriteClass;
				continue;
			}

			if(nitriteClass.getValue() > bestNitriteClass.getValue()){
				bestNitriteClass = nitriteClass;
			}
			else if(nitriteClass.getValue() > secBestNitriteClass.getValue()){
				secBestNitriteClass = nitriteClass;
			}
		}

		double bestNitrateProb = bestNitrateClass.getValue() / (bestNitrateClass.getValue() + secBestNitrateClass.getValue());
		double bestNitriteProb = bestNitriteClass.getValue() / (bestNitriteClass.getValue() + secBestNitriteClass.getValue());


		Toast.makeText(c, "Nitrate Level: " + (bestNitrateClass.getValue() * bestNitrateProb + secBestNitrateClass.getValue() * (1 - bestNitrateProb)), Toast.LENGTH_LONG).show();
		Toast.makeText(c, "Nitrite Level: " + (bestNitriteClass.getValue() * bestNitriteProb + secBestNitriteClass.getValue() * (1 - bestNitriteProb)), Toast.LENGTH_LONG).show();
		//Print it out nicely
		/*double nitrateSum = 0;
		for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
			nitrateSum += 1 / nitrateClass.getValue();
		}

		double nitriteSum = 0;
		for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()){
			nitriteSum += 1 / nitriteClass.getValue();
		}

		double expected = 0;
		Log.d("Test: ", "Nitrate Analysis");
		for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
			Log.d("Test: ", nitrateClass.getKey() + " -> " + (1 / nitrateClass.getValue()) / nitrateSum);
			expected += (1 / nitrateClass.getValue()) / nitrateSum * nitrateClass.getKey();
		}
		Log.d("Test: ", "Expected Value: " + expected);

		Log.d("Test: ","");

		expected = 0;
		Log.d("Test: ", "Nitrite Analysis");
		for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()){
			Log.d("Test: ", nitriteClass.getKey() + " -> " + (1 / nitriteClass.getValue()) / nitriteSum);
			expected += (1 / nitriteClass.getValue()) / nitriteSum * nitriteClass.getKey();
		}
		Log.d("Test: ", "Expected Value: " + expected);*/
	}
}

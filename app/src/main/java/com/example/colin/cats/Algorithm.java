package com.example.colin.cats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
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

//		System.err.println("left: " + new HSBImage(balancedLeft).medianColor().toString());
//		System.err.println("right: " + new HSBImage(balancedRight).medianColor().toString());


		HashMap<Double, Integer> nitrateColours = new HashMap<>();
		HashMap<Double, Integer> nitriteColours = new HashMap<>();

		System.err.println(Color.parseColor("#2222FF"));

		nitriteColours.put(1., c.getResources().getColor(R.color.Nitrite1));
		nitriteColours.put(3., c.getResources().getColor(R.color.Nitrite3));
		nitriteColours.put(1.5, c.getResources().getColor(R.color.Nitrite1_5));
		nitriteColours.put(0.3, c.getResources().getColor(R.color.Nitrite0_3));
		nitriteColours.put(0., c.getResources().getColor(R.color.Nitrite0));
		nitriteColours.put(0.15, c.getResources().getColor(R.color.Nitrite0_15));

        nitrateColours.put(10., c.getResources().getColor(R.color.Nitrate10));
        nitrateColours.put(0., c.getResources().getColor(R.color.Nitrate0));
        nitrateColours.put(1., c.getResources().getColor(R.color.Nitrate1));
        nitrateColours.put(2., c.getResources().getColor(R.color.Nitrate2));
        nitrateColours.put(50., c.getResources().getColor(R.color.Nitrate50));
        nitrateColours.put(20., c.getResources().getColor(R.color.Nitrate20));
        nitrateColours.put(5., c.getResources().getColor(R.color.Nitrate5));

        ColorRecog nitrateRecog = new ColorRecog(nitrateColours);
        ColorRecog nitriteRecog = new ColorRecog(nitriteColours);

		//Do the analysis
		Map<Double, Double> nitrateAnalysis = nitrateRecog.processImage(balancedRight);
		Map<Double, Double> nitriteAnalysis = nitriteRecog.processImage(balancedLeft);

//		float[] cats = new float[3];
//		Color.colorToHSV(0xFFFFFF, cats);
//		System.err.println(Arrays.toString(cats));

		//Print it out nicely
		double nitrateSum = 0;
		Double bestNitrateClass = null;
		for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
			System.err.println("nitrate: " + nitrateClass.getKey() + ": " + nitrateClass.getValue());
			//Toast.makeText(c, "Nitrate: " + nitrateClass.getKey() + " " + nitrateClass.getValue(), Toast.LENGTH_LONG).show();
			if(bestNitrateClass == null || nitrateAnalysis.get(bestNitrateClass) > nitrateClass.getValue()){
				bestNitrateClass = nitrateClass.getKey();
			}
		}


		Double bestNitriteClass = null;
		for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()){
			System.err.println("nitrite: " + nitriteClass.getKey() + ": " + nitriteClass.getValue());
			//Toast.makeText(c, "Nitrite" + nitriteClass.getKey() + " " + nitriteClass.getValue(), Toast.LENGTH_LONG).show();
			if(bestNitriteClass == null || nitriteAnalysis.get(bestNitriteClass) > nitriteClass.getValue()){
				bestNitriteClass = nitriteClass.getKey();
			}
		}

		Toast.makeText(c, "Nitrate Class: " + bestNitrateClass, Toast.LENGTH_LONG).show();
		Toast.makeText(c, "Nitrite Class: " + bestNitriteClass, Toast.LENGTH_LONG).show();

		// double expected = 0;
		// Log.d("Test: ", "Nitrate Analysis");
		// for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
		// 	Log.d("Test: ", nitrateClass.getKey() + " -> " + (1 / nitrateClass.getValue()) / nitrateSum);
		// 	expected += (1 / nitrateClass.getValue()) / nitrateSum * nitrateClass.getKey();
		// }
		// Log.d("Test: ", "Expected Value: " + expected);
		//
		// Log.d("Test: ","");
		//
		// expected = 0;
		// Log.d("Test: ", "Nitrite Analysis");
		// for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()){
		// 	Log.d("Test: ", nitriteClass.getKey() + " -> " + (1 / nitriteClass.getValue()) / nitriteSum);
		// 	expected += (1 / nitriteClass.getValue()) / nitriteSum * nitriteClass.getKey();
		// }
		// Log.d("Test: ", "Expected Value: " + expected);*/
	}
}

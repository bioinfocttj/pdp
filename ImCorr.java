/*
Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
import java.util.Hashtable;
import java.util.Vector;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;

import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;

abstract class ImCorr implements Picker {
	
	static Vector[] resultstable = new Vector[3];
	static Vector<Double> xtab=new Vector<Double>();
	static Vector<Double> ytab=new Vector<Double>();
	static Vector<Double> slice=new Vector<Double>();
	
	ImCorr(){
	}
	
	static void picking() {
		ImagePlus im = WindowManager.getCurrentImage();
		pick(im, 1);
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		resultstable[0].removeAllElements();
		resultstable[1].removeAllElements();
		resultstable[2].removeAllElements();
		IJ.run("Clear Results");
	}
	
	static double[][] sliceSelection(){
		
		ImagePlus im=WindowManager.getCurrentImage();
		String stackName = im.getTitle();
		int nbslice=im.getStackSize();
		for (int a=1;a<=nbslice;a++){
			im.setSlice(a);
			pick(im, a);
		}
		//cast the vector in an array so as to send it to the cropper
		//resultConverter();
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String cropMode = hashAttributes.get("crop");
		boolean cropperMode = Boolean.parseBoolean(cropMode);
		double[][]array= resultConverter();
		if (cropperMode) {
			for (int a=1;a<=nbslice;a++){
				im.setSlice(a);
				IJ.run(im, "Duplicate...", stackName);
				ImagePlus dupli = WindowManager.getCurrentImage();
				Cropper cropper = new Cropper(dupli, array, a);
				cropper.crop(a, stackName);
				dupli.close();
			}
			IJ.run(im, "Images to Stack", "name=stack title=[DUP] use");
		}
		return array;
	}

	public static void pick(ImagePlus image,int currentslice){
		
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String rMin = hashAttributes.get("radiusMin");
		String rMax = hashAttributes.get("radiusMax");
		String rInc = hashAttributes.get("radiusInc");
		String noiseT = hashAttributes.get("noiseTolerance");
		String noise = "noise=" + noiseT+" output=[Point Selection]";
		
		int w=image.getWidth(); //image width
		int h=image.getHeight(); //image heigh
		int radiusMin=Integer.parseInt(rMin); //radius min of the draw circule
		int radiusMax=Integer.parseInt(rMax); //radius max of the draw circule
		int radiusInc=Integer.parseInt(rInc); //radius incrementation
		ResultsTable table; //result table
		
		//creation of an image which contains a circle with different diameters
		image.setSlice(currentslice);
		for (int radius=radiusMin;radius<=radiusMax;radius=radius+radiusInc){
			ImagePlus imp = IJ.createImage("test2", "8-bit White", w, h, 1);
			imp.setRoi(new OvalRoi((w/2)-radius, (h/2)-radius, radius*2, radius*2));
			IJ.run(imp, "Draw", "");
			ImagePlus result = FFTMath.doMath(image,imp);
			//IJ.run(result, "Invert LUT", "");
			//result.show();
			WindowManager.setTempCurrentImage(result);
			IJ.run(result,"Enhance Contrast", "saturated=0 normalize");
			IJ.run(result,"Find Maxima...", noise);
			IJ.run("Set Measurements...", "  min centroid stack redirect=None decimal=3");
			IJ.run("Measure");
			result.close();
		}
		
		table = Analyzer.getResultsTable();
		sort(table,image);
	}
	
	static  void sort(ResultsTable table,ImagePlus image)
	{	
		int counter=table.getCounter();
		int []list=new int [counter];
		int []pass=new int[counter];
		int lenlist=0;
		int nb = 0;
		int iterator = counter-1;
		
		for (int j=iterator;j>=0;j--){
			int yetpass = 0;
			int maxval=j;
			int cpt=0;
			double xj=table.getValue("X",j);
			double yj=table.getValue("Y",j);
			double maxj=table.getValue("Max",j);
			
			for (int iter=0; iter<nb; iter++){
				if( j == pass[iter]){
					yetpass=1;
				}
			}
			
			if(yetpass == 0){
				for (int k=iterator;k>=0;k--){
					double xk=table.getValue("X",k);
					double yk=table.getValue("Y",k);
					double maxk=table.getValue("Max",k);
						if ((xk<=xj+40 && xk>=xj-40) && (yk<=yj+40 && yk>=yj-40) ){
						int cont=0;
						for (int p=0;p<nb;p++){
							if (pass[p]==k){cont+=1;}
						}
						if (cont==0){
							pass[nb]=k;
							nb ++;
						}
					if (maxj<maxk){maxval=k;}
					else if(maxj>maxk){maxval=j;}
					else{if (j != k){maxval=j;}}
					}
				}
				for (int q=0;q<lenlist;q++){
					if (maxval == list[q]){cpt+=1;}
				}
				if (cpt==0){
					list[lenlist]=maxval;
					lenlist++;
				}
			}
			iterator --;
		}
		results(list,table,lenlist,image);
	}
	
	static Object results(int []list,ResultsTable table, int lenlist,ImagePlus image)
	{
		
		int []xpoints=new int[lenlist];
		int []ypoints=new int[lenlist];
		
		for (int l=0;l<lenlist;l++){
			int line2=list[l];
			double x=table.getValue("X",line2);
			double y=table.getValue("Y",line2);
			xtab.add(x);
			ytab.add(y);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[l] = xx;
			ypoints[l] = yy;
			image.setRoi(new PointRoi(xpoints,ypoints,lenlist));
		}

		IJ.run("Clear Results");
		IJ.run("Measure");

		ResultsTable finalresults = Analyzer.getResultsTable();//table with y,y and slice
		int counter=finalresults.getCounter();

		for(int i=0;i<counter;i++){
			double temp = finalresults.getValue("Slice", i);
			slice.add(temp);
		}
		
		resultstable[0]= xtab;
		resultstable[1]= ytab;
		resultstable[2] = slice;
		//printResultTable(resultstable);
		IJ.run("Clear Results");
		return resultstable;
	}

	static double[][] resultConverter(){
		int arrayLength=xtab.size();
		Object[] tempX = new String[arrayLength];
		Object[] tempY = new String[arrayLength];
		Object[] tempZ = new String[arrayLength];
		tempX = xtab.toArray();
		tempY = ytab.toArray();
		tempZ = slice.toArray();
		double[] xArray = new double[arrayLength];
		double[] yArray = new double[arrayLength];
		double[] zArray = new double[arrayLength];
		for (int i=0; i < arrayLength; i++){
			String temp = String.valueOf(tempX[i]);
			xArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempY[i]);
			yArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempZ[i]);
			zArray[i] = Double.parseDouble(temp);
			//xArray[i] = Double.parseDouble(tempX[i]);
			//yArray[i] = Double.parseDouble(tempY[i]);
			//zArray[i] = Double.parseDouble(tempZ[i]);
		}
		double[][] coordinates = new double[3][arrayLength];
		coordinates[0]=xArray;
		coordinates[1]=yArray;
		coordinates[2]=zArray;
		return coordinates;
	}
	
	// methode pour printer le tableau resultstable
	
	/*static void printResultTable(Vector[] resulttable){
		int zero = resulttable[0].size();
		int un = resulttable[0].size();
		int deux = resulttable[0].size();
		String longueurs = "0,"+zero+",1,"+un+",2,"+deux;
		//IJ.showMessage(longueurs);
		for (int i=0;i<resulttable[1].size();i++){
			for (int j=0;j<resulttable.length;j++){
				System.out.println("lala");
				System.out.print(resulttable[j]);
				System.out.println("\n");
			}
		}
	}*/
}

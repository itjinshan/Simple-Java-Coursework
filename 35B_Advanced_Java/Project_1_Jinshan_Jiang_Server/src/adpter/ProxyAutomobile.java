package adpter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

import scale.*;
import server.AutoServer;
import server.BuildCarModelOptions;
import model.*;
import util.*;
public abstract class ProxyAutomobile {
	//Variable a1 can be used for handling all operations on Automobile 
	//as needed by the interfaces.
	private static LinkedHashMap<String, Automobile> car = new LinkedHashMap<String, Automobile>();
	private ArrayList<String> allCarsMake = null;
	
	public void insertAuto(Automobile newAuto)
	{
		car.put(newAuto.getBrand(), newAuto);
	}
	
	public Automobile findAuto(String brand)
	{
		return car.get(brand);
	}
	
	public void setChoice(String brand, String OptSetName, String OptionName)
	{
		Automobile a1;
		a1 = this.findAuto(brand);
		a1.setOptionChoice(OptSetName, OptionName);
	}
	
	public void BuildAuto(PrintWriter outString,ObjectInputStream in) throws ClassNotFoundException, IOException {
		//readBuild rb = new readBuild();
		AutoServer rb = new BuildCarModelOptions();
		//buildFromClient(PrintWriter outString,ObjectInputStream in)
		//Automobile tempAuto = rb.buildFromClient(outString, in,a);
		rb.recieve(outString, in, car);
		//this.insertAuto(tempAuto);
	}
	
	public void buildAuto(String filename)//(String filename, String fileType)
	{
		Automobile a1;
		ReadSource rs = new ReadSource();
		a1 = rs.read(filename);
		this.insertAuto(a1);
		/**fileType = fileType.toLowerCase();
		if(fileType == "property file")
		{
			ObjectInputStream in;
			ReadPropertyFile rpf = new ReadPropertyFile();
			try {
				rpf.readProFile(filename,(Properties) in.readObject(), car);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.insertAuto(a1);
		}
		else if(fileType == "regular file")
		{
			ReadSource rs = new ReadSource();
			a1 = rs.read(filename);
			this.insertAuto(a1);
		}*/
	}
	
	// **************************************
	// ** Update **
	// **************************************
	/**
	 * i. Update Option Set Name
	 * ii. Update Option with a New Price
	 */
	public void updateOptionSetName(String brand, String modelname, String optionSetname,
											String newName)
	{
		Automobile a1;
		a1 = this.findAuto(brand);
		a1.updateOptionSet(optionSetname, newName);
		System.out.println("*******************");
		System.out.println("~ Option Set updated!");
		System.out.println("*******************");
	}
	
	public void updateOptionPrice(String brand, String modelname, String optionSetname, 
			String option, float newprice)
	{
		Automobile a1;
		a1 = this.findAuto(brand);
		a1.updateOption(a1.getOptionSet(a1.findOptionSet(optionSetname)), option, newprice);
		System.out.println("*******************");
		System.out.println("~ Option Updated!");
		System.out.println("*******************");
	}
	
	public void updateT(int operation1, int operation2, String [] newData1, String [] newData2)
	{
		EditOptions opt = new EditOptions();
		AssistDrive ad1 = new AssistDrive(operation1, newData1, opt);
		AssistDrive ad2 = new AssistDrive(operation2, newData2, opt);
		ad1.start();
		ad2.start();
		/**EditOptions eopt1 = new EditOptions(operation1, newData1);
		EditOptions eopt2 = new EditOptions(operation2, newData2);
		eopt1.start();
		eopt2.start();*/
	}
	
	public ArrayList<String> giveAllCars()
	{
		allCarsMake = new ArrayList<String>();
		for (String key: ProxyAutomobile.car.keySet()) {

		    //System.out.println("key : " + key);
		    System.out.println("value : " + ProxyAutomobile.car.get(key).getBrand());
		    
			allCarsMake.add(ProxyAutomobile.car.get(key).getBrand());
		}
		return allCarsMake;
	}


	public void printAuto(String brand, String modelName)
	{
		Automobile a1;
		a1 = this.findAuto(brand);
		if(modelName.equals(a1.getName()))
			a1.print();
		else
			System.out.println("Automobile Not Found!!");
	}
}
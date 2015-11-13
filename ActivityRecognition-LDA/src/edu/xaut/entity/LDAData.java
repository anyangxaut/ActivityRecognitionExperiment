package edu.xaut.entity;
/**
 * 需要进行LDA转换的数据信息
 * @author anyang
 *
 */
public class LDAData {

	private double sensor1Data = 0;
	private double sensor2Data = 0;
	private double sensor3Data = 0;
	
	public LDAData(double sensor1Data, double sensor2Data, double sensor3Data) {
		super();
		this.sensor1Data = sensor1Data;
		this.sensor2Data = sensor2Data;
		this.sensor3Data = sensor3Data;
	}

	public double getSensor1Data() {
		return sensor1Data;
	}

	public void setSensor1Data(double sensor1Data) {
		this.sensor1Data = sensor1Data;
	}

	public double getSensor2Data() {
		return sensor2Data;
	}

	public void setSensor2Data(double sensor2Data) {
		this.sensor2Data = sensor2Data;
	}

	public double getSensor3Data() {
		return sensor3Data;
	}

	public void setSensor3Data(double sensor3Data) {
		this.sensor3Data = sensor3Data;
	}

}

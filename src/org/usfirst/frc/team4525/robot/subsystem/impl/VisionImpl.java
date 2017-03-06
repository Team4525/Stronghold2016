package org.usfirst.frc.team4525.robot.subsystem.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;

import org.usfirst.frc.team4525.robot.subsystem.SubSystem;
import org.usfirst.frc.team4525.robot.subsystem.VisionAiming;
import org.usfirst.frc.team4525.robot.util.DashUtil;

import com.frc4525.comms.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionImpl implements VisionAiming {

	private DashUtil dash = DashUtil.getInstance();
	private CommServer server = new CommServer();
	private Target target = null;
	private boolean running = false;
	
	@Override
	public void init() {
		server.start();
		Thread visionThread = new Thread(new TargetThread());
		visionThread.start();
	}
	
	@Override
	public void test() {
		
	}
	
	@Override
	public Target getTarget() {
		return target;
	}
	
	
	private class TargetThread implements Runnable{
		@Override
		public void run() {
			running = true;
			while(running){
				target =  mapData(server);
				publishToNetworkTables(target);
			}
		}
	}
	
	
	
	private Target mapData(CommServer server){
		Hashtable<String, Double> ht = server.getData();
		DashUtil.getInstance().log(ht); // update dash with values too
		Target target = null;
		if(ht.get("UPDATED") != null && ht.get("UPDATED") !=  0){
			target = new Target();
			target.setUpdated(ht.get("UPDATED"));
			target.setCenterX(ht.get("centerX"));
			target.setCenterY(ht.get("centerY"));
			target.setWidth(ht.get("width"));
			target.setLowerRightX(ht.get("lowerX"));
			target.setLowerRightY(ht.get("lowerY"));
			target.setUpperLeftX(ht.get("upperX"));
			target.setUpperLeftY(ht.get("upperY"));
			target.setImageHeight(ht.get("imageHeight"));
			target.setImageWidth(ht.get("imageWidth"));
			
		}else{
			target = null;
		}
		return target;
	
		
	}
	
	private void publishToNetworkTables(Target target){
		NetworkTable nt = NetworkTable.getTable("vision");
		if(target != null){
			nt.putNumber("CenterX", target.getCenterX());
			nt.putNumber("CenterY", target.getCenterY());
			nt.putNumber("Width", target.getWidth());
			nt.putNumber("upperLeftX", target.getUpperLeftX());
			nt.putNumber("upperLeftY", target.getUpperLeftY());
			nt.putNumber("lowerRightX", target.getLowerRightX());
			nt.putNumber("lowerRightY", target.getLowerRightY());
			nt.putNumber("imageWidth", target.getImageWidth());
			nt.putNumber("imageHeight", target.getImageHeight());
			nt.putNumber("updated", target.getUpdated());
		}else{
			nt.putNumber("CenterX", 0);
			nt.putNumber("CenterY", 0);
			nt.putNumber("Width", 0);
			nt.putNumber("upperLeftX", 0);
			nt.putNumber("upperLeftY", 0);
			nt.putNumber("lowerRightX", 0);
			nt.putNumber("lowerRightY", 0);
			nt.putNumber("imageWidth", 0);
			nt.putNumber("imageHeight", 0);
			nt.putNumber("updated", 0);
		}
		
		
		
	}
}

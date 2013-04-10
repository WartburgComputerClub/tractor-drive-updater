package com.andrewreisner.tractorDrive;

public class Driver {

    public static void main(String[] args) {
	try {
	    TractorRepo tr = new TractorRepo("/home/andrew/code/tractorProd");
	    System.out.println(tr.getReleaseNotes("1.0.0"));
	}catch (Exception ex) {
	    System.out.println(ex);
	}
	
    }

}

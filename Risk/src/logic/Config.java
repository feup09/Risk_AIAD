package logic;

import java.util.ArrayList;

public class Config implements java.io.Serializable {
	
	//numero jogadores
	private int nPlayers = 6;
	
	
	private String P1 = "p1";
	private String P2 = "p2";
	private String P3 = "p3";
	private String P4 = "p4";
	private String P5 = "p5";
	private String P6 = "p6";
	
	
	private int C1 = 1;
	private int C2 = 2;
	private int C3 = 3;
	private int C4 = 4;
	private int C5 = 5;
	private int C6 = 6;
	
	private int T1 = 2;
	private int T2 = 2;
	private int T3 = 2;
	private int T4 = 2;
	private int T5 = 2;
	private int T6 = 2;
        
        private double O1 = 1;
        private double O2 = 0.8;
        private double O3 = 0.6;
        private double O4 = 0.4;
        private double O5 = 0.2;
        private double O6 = 0;
        
        private double I1 = 1;
        private double I2 = 1;
        private double I3 = 1;
        private double I4 = 1;
        private double I5 = 1;
        private double I6 = 1;
        
        private double V1 = 1;
        private double V2 = 1;
        private double V3 = 1;
        private double V4 = 1;
        private double V5 = 1;
        private double V6 = 1;
        
	
        
	
	private ArrayList<String> P = new ArrayList<String>();
	private ArrayList<Integer> C = new ArrayList<Integer>();
	private ArrayList<Integer> T = new ArrayList<Integer>();
        private ArrayList<Double> I = new ArrayList<Double>();
        private ArrayList<Double> O = new ArrayList<Double>();
        private ArrayList<Double> V = new ArrayList<Double>();
        
	
	
	public Config(){
		
		P.add(P1);
		P.add(P2);
		P.add(P3);
		P.add(P4);
		P.add(P5);
		P.add(P6);
		
		C.add(C1);
		C.add(C2);
		C.add(C3);
		C.add(C4);
		C.add(C5);
		C.add(C6);
		
		T.add(T1);
		T.add(T2);
		T.add(T3);
		T.add(T4);
		T.add(T5);
		T.add(T6);
                
                I.add(I1);
		I.add(I2);
		I.add(I3);
		I.add(I4);
		I.add(I5);
                I.add(I6);
                
                O.add(O1);
		O.add(O2);
		O.add(O3);
		O.add(O4);
		O.add(O5);
		O.add(O6);
                
                V.add(V1);
		V.add(V2);
		V.add(V3);
		V.add(V4);
		V.add(V5);
		V.add(V6);
		
	
	}
	
	public int getNPlayers(){
		return this.nPlayers;
	}
	
	public ArrayList<String> getP(){
		return this.P;
	}
	
	public ArrayList<Integer> getC(){
		return this.C;
	}
	
	public ArrayList<Integer> getT(){
		return this.T;
	}
        
        public ArrayList<Double> getI(){
		return this.I;
	}
	
	public ArrayList<Double> getO(){
		return this.O;
	}
	
	public ArrayList<Double> getV(){
		return this.V;
	}
	

}

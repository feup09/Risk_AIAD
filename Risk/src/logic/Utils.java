package logic;

import java.util.ArrayList;
import java.util.Random;

public class Utils implements java.io.Serializable{

	public ArrayList<Integer> throwDices(int n, boolean attacker){
		int m = 0;
		if(attacker && n > 3){	
			m = 3;
		}
		else if(!attacker && n > 2){
			m = 2;
		}
		else{
			m = n;
		}
		
		ArrayList<Integer> dices = new ArrayList<Integer>();
		Random rn = new Random();
		for(int i = 0; i < m;++i){
			int dice = rn.nextInt(6) + 1;
			dices.add(dice);
		}
		
		return dices;
	}
	
	public ArrayList<Integer> combateLosses(ArrayList<Integer> attacker, ArrayList<Integer> defender){
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		values.add(0);
		values.add(0);
		int minArmies = 0;
		
		if(attacker.size() < defender.size()){
			minArmies = attacker.size();
			defender = getBestTroops(defender,minArmies);
		}
		else{
			minArmies = defender.size();
			attacker = getBestTroops(attacker,minArmies);
		}
		//compare
		for(int j = 0; j < minArmies; ++j){
			
			if(attacker.get(j) > defender.get(j)){
				int n = values.get(0);
				values.set(0, n+1);
			}
			else{
				int n = values.get(1);
				values.set(1, n+1);
			}
		}
		
		return values;
	}
	
	//filter wanted
	public ArrayList<Integer> getBestTroops(ArrayList<Integer> attacker, int n){
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		for(int i = 0; i < attacker.size();++i){
			values.add(attacker.get(i));
		}
		
		return values;
	}
	
}

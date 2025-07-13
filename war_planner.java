

import java.util.*;

class Platoon{
    String type;
    int count;
    public Platoon(String type, int count){
        this.type = type;
        this.count = count;
    } 
}
public class war_planner {
    static HashMap<String, List<String>> advantages = new HashMap<>();
    static ArrayList<Platoon> ourForce,oppForce;
    static {
        advantages.put("Militia", Arrays.asList("Spearmen", "LightCavalry"));
        advantages.put("Spearmen", Arrays.asList("LightCavalry", "HeavyCavalry"));
        advantages.put("LightCavalry", Arrays.asList("FootArcher", "CavalryArcher"));
        advantages.put("HeavyCavalry", Arrays.asList("Militia", "FootArcher", "LightCavalry"));
        advantages.put("CavalryArcher", Arrays.asList("Spearmen", "HeavyCavalry"));
        advantages.put("FootArcher", Arrays.asList("Militia", "CavalryArcher"));
    }

    public ArrayList<Platoon> parsePlatoons(String forces){
        String[] split_by_semi = forces.split(";");
        ArrayList<Platoon> res = new ArrayList<>();
        for(String val : split_by_semi){
            String[] split_by_hash = val.split("#");
            Platoon troop = new Platoon(split_by_hash[0], Integer.parseInt(split_by_hash[1]));
            res.add(troop); 
        }
        return res;
    }
    public String getMaximumWin(){

        if(ourForce.size() <=0 || oppForce.size() <=0)return "Please enter the list of platoons: ";
        else{
            ArrayList<ArrayList<Platoon>> result = new ArrayList<>();
            int maxWin = 0;
            String output = "";
            generateAllPermutation(new ArrayList<>(this.ourForce), 0, result);
            for(ArrayList<Platoon> permuatedForce : result){
                int win_counter = 0;
                for(int ind = 0;ind<permuatedForce.size(); ind++){
                    if(isWin(permuatedForce.get(ind), oppForce.get(ind)))win_counter++;
                }
                if(win_counter>maxWin){
                    maxWin = win_counter;
                    
                    output = generateOutput(permuatedForce);
                }
            }

            if(maxWin>=3)return output;
            return "There is no chance of winning";
        }
    }
    private String generateOutput(ArrayList<Platoon> res){
        String output = "";
        for(Platoon sol : res){
           output+=sol.type+"#"+sol.count+";";
        }
        return output;
    }
    public boolean isWin(Platoon own, Platoon opponent){
        int ownPower = own.count;
        int oppPower = opponent.count;
        for(String sol : advantages.get(own.type)){
            if(sol.equals(opponent.type)){
                ownPower = ownPower*2;

            }
        }
        for(String sol : advantages.get(opponent.type)){
            if(sol.equals(own.type)){
                oppPower = oppPower * 2;
            }
        }
        return ownPower>oppPower;
    }
    public void generateAllPermutation(ArrayList<Platoon> forces, int index, ArrayList<ArrayList<Platoon>> result){
        if(index==forces.size()){
            result.add(new ArrayList<>(forces));
            return ;
        }
        else{
            for(int i = index; i<forces.size(); i++){
                Collections.swap(forces, i, index);
                generateAllPermutation(forces, index+1, result);
                Collections.swap(forces, i, index);
            }
        }
    }
    public String greedyApproach(){
        HashSet<String> visited = new HashSet<>();
        ArrayList<Platoon> res = new ArrayList<>();

        for(int i = 0;i<oppForce.size(); i++){
            boolean flag = true;
            Platoon opponent = oppForce.get(i);
            int oppPower = opponent.count;
            for(int j = 0;j<ourForce.size(); j++){
                Platoon own = ourForce.get(j);
                int ownPower = own.count;
                for(String k : advantages.get(opponent.type)){
                    if(k.equals(own.type)){
                        oppPower = oppPower*2;
                    }
                }
                for(String k : advantages.get(own.type)){
                    if(k.equals(opponent.type)){
                        ownPower = ownPower*2;
                    }
                }
                if( ownPower>oppPower && !visited.contains(own.type)){
                    res.add(own);
                    visited.add(own.type);
                    flag = false;
                    break;
                    
                }
            }
            if(flag){
                for(Platoon sol : ourForce){
                    if(!visited.contains(sol.type)){
                        res.add(sol);
                        visited.add(sol.type);
                        break;
                    }
                    
                }
            }
        }
        return generateOutput(res);
    }

    
}

import java.util.*;
public class Main {
    public static void main(String[] arg){
        Scanner sc = new Scanner(System.in);
        war_planner war = new war_planner();
        String own = sc.nextLine();
        String opp = sc.nextLine();
        war.ourForce =  war.parsePlatoons(own);

        war.oppForce = war.parsePlatoons(opp);
        System.out.println(war.getMaximumWin());
    }        
}

package thirdPartyToken;
import java.text.SimpleDateFormat;
/**
 //* Created by toti on 13.03.18.
 */
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class tokenCollection {
    private static Map<Integer,tokenModel> coll=new ConcurrentHashMap<Integer,tokenModel>();
    private static int num = 0;
    int key = 0;
    private static int row = 0;
    static DefaultTableModel model=new DefaultTableModel(new Object[] {"username","token","start time","end time"},0);
	static JTable table = new JTable(model);
    public tokenCollection(){
    }
    public static Map getColl(){
        return coll;
    }
    public void addToJTable(Object[] obj1) {
    		model.addRow(obj1);
    }
    public static Boolean ifExists(int token, String username) {
        Iterator it = coll.entrySet().iterator();
        Boolean ifExists = false;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            tokenModel tknMdl = (tokenModel) pair.getValue();
            if(tknMdl.getToken()==token&&tknMdl.getUsername().contains(username)){
                ifExists= true;
                break;
            }
        }
        return ifExists;
    }
    public static Boolean ifExistsByUser(String usrn){
        Iterator it = coll.entrySet().iterator();
        Boolean ifExists = false;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            tokenModel tknMdl = (tokenModel) pair.getValue();
            if(tknMdl.getUsername().contains(usrn)){
                ifExists= true;
                break;
            }
        }
        return ifExists;
    }
    public static Boolean ifExists(int id, String usrn, int token){
        Iterator it = coll.entrySet().iterator();
        Boolean ifExists = false;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            tokenModel tknMdl = (tokenModel) pair.getValue();
            if(tknMdl.getId()==id&&tknMdl.getUsername()==usrn&&tknMdl.getToken()==token){
                ifExists=true;
                break;
            }
        }
        return ifExists;
    }
    public static void printTokens(){
        Iterator it = coll.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Integer key = (Integer)pair.getKey();
            tokenModel tknMdl = (tokenModel) pair.getValue();
            System.out.println("Key is: "+key+"; token is: "+tknMdl.getToken()+" and username is:"+tknMdl.getUsername());

        }
    }

    public static Boolean generateToken(String user){
        StringBuilder token = new StringBuilder(8);
        Random rmd = new Random();
        for(int i=0;i<8;i++){
            token.append(rmd.nextInt(9));
        }
        String tokenStr=token.toString();
        int tokenInt = Integer.parseInt(tokenStr);
        if(ifExists(tokenInt,user)==true){
            generateToken(user);
        }
        long startTime = System.currentTimeMillis();
        long endTime = startTime+60000;
        tokenModel newToken= new tokenModel(user,startTime,endTime,tokenInt);
        if(putInColl(newToken)==false){
            return false;
        }
        System.out.println("Token :"+tokenInt);
        model.addRow(new Object[]{user,tokenInt,convertDate(startTime),convertDate(endTime)});
        timerToDelete(newToken);
        return true;
        
    }
    private static void timerToDelete(final tokenModel token) {
        final Timer timer1 = new Timer();
        timer1.schedule(new TimerTask(){
            @Override
            public void run(){

                Iterator<Entry<Integer, tokenModel>> it = coll.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry<Integer,tokenModel> item = (Map.Entry<Integer,tokenModel>) it.next();
                    Integer key = (int)item.getKey();
                    tokenModel tknMdl = (tokenModel)item.getValue();
                    if(tknMdl.getToken()==token.getToken()) {
                        coll.remove(key);
                        regenerateTable();
                    }
                }
                System.out.println("Token deleted now!");
                timer1.cancel();

        };
    },60*1000);
    }
    private static void regenerateTable() {
    	Object[][] data =new Object[tokenCollection.getColl().size()][5];
		int row = 0;
		model.setRowCount(0);
		for(Object key:tokenCollection.getColl().keySet()) {
			tokenModel tkn = (tokenModel)tokenCollection.getColl().get(key);
			data[row][0]=tkn.getId();
			data[row][1]=tkn.getUsername();
			data[row][2]=tkn.getToken();
			data[row][3]=convertDate(tkn.getStartTime());
			data[row][4]=convertDate(tkn.getEndTime());
			model.addRow(data[row]);
			row++;
		}
		model.fireTableStructureChanged();
    }
    private static Boolean putInColl(tokenModel token) {
        try {
            coll.put(num, token);
            num++;
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    public static String convertDate(long nm) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(nm);
		return sdf.format(resultdate);
		
	}
}

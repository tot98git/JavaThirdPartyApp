package thirdPartyToken;

/**
 * Created by toti on 13.03.18.
 */

public class tokenModel {
        private static int id=0;
        String username;
        long startTime;
        long endTime;
        int token;
        public tokenModel(){

        }
        public tokenModel(String usr, long strt, long endt, int tkn){
            username=usr;
            startTime=strt;
            endTime=endt;
            token=tkn;
            id++;
        }
        public int getId(){
            return id;
        }
        public String getUsername(){
            return username;
        }
        public void setUsername(String usrn){
            username=usrn;
        }
        public long getStartTime(){
            return startTime;
        }
        public void setStartTime(int strt ){
            startTime=strt;
        }
        public long getEndTime(){
            return endTime;
        }
        public void setEndTime(int endt){
            endTime=endt;

        }
        public int getToken(){
            return token;
        }
        public void setToken(int tkn){
            token=tkn;
        }
}

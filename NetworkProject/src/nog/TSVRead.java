package nog;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
 
 
public class TSVRead extends Thread {
        private Socket clientSocket;
       
        private BufferedReader in = null;
    private ObjectOutputStream out = null;
   
    private StringTokenizer st;
    private BufferedReader TSVFile;
    private Hashtable<Integer, Participant> ht;
    private Participant participant;
       
        public TSVRead(Socket clientSocket){
                this.clientSocket = clientSocket;
                ht = new Hashtable<Integer, Participant>();
               
                try {
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new ObjectOutputStream(clientSocket.getOutputStream());
                       
                } catch (IOException e) {
                        System.out.println(e);
                        return;
                }
        }
       
       
        public void run(){
                try {
                        String input = in.readLine().trim();
                        st = new StringTokenizer(input,";");
                        String command = (String)st.nextElement();
                       
                        switch(command){
                                case "GET":
                                        getRecords();
                                case "ADD":
                                        addRecord((int)st.nextElement(),(String)st.nextElement(),(String)st.nextElement(),(String)st.nextElement(),
                                                        (String)st.nextElement(),(double)st.nextElement(),(double)st.nextElement(),(String)st.nextElement());
                                case "REMOVE":
                                        removeRecord((int)st.nextElement());
                                case "EDIT":
                                        editRecord((int)st.nextElement(),(String)st.nextElement(),(String)st.nextElement(),(String)st.nextElement(),
                                                        (String)st.nextElement(),(double)st.nextElement(),(double)st.nextElement(),(String)st.nextElement());
                        }
                       
                } catch(Exception e){
                        System.out.println(e);
                }
        }
        
        public void getRecords(){
                try {
                        TSVFile = new BufferedReader(new FileReader("C:\\participants.tsv"));
                        String dataRow = TSVFile.readLine();
                       
                        while (dataRow != null){
                                st = new StringTokenizer(dataRow,"\t");
                                List<Object> templist = new ArrayList<Object>();
                                while(st.hasMoreElements()){
                                        templist.add(st.nextElement());
                                }
                                System.out.println("Before participant creation");
                                System.out.println((String)templist.get(1));
                                int idTemp = Integer.parseInt((String)templist.get(0));
                                System.out.println("WHAT IS IT?: "+idTemp);
                                participant = new Participant(idTemp,
                                                                                                (String)templist.get(1),
                                                                                                (String)templist.get(2),
                                                                                                (String)templist.get(3),
                                                                                                (String)templist.get(4),
                                                                                                Double.parseDouble((String)templist.get(5)),
                                                                                                Double.parseDouble((String)templist.get(6)),
                                                                                                (String)templist.get(7));
                                System.out.println("After participant creation");
                                ht.put(participant.getId(),participant);                 
                                dataRow = TSVFile.readLine();
                                System.out.println("Next Row");
                }
                TSVFile.close();
                out.writeObject(ht);
                } catch(Exception e) {
                        System.out.println(e);
                }
        }
       
        public void addRecord(int id, String name, String gender, String country, String date,
                                                        double height, double weight, String sport){
                try {
                        FileWriter fw = new FileWriter("C:\\participants.tsv");
                    PrintWriter pw = new PrintWriter(fw);
                   
                    pw.print(id+"\t");
                    pw.print(name+"\t");
                    pw.print(gender+"\t");
                    pw.print(country+"\t");
                    pw.print(date+"\t");
                    pw.print(height+"\t");
                    pw.print(weight+"\t");
                    pw.print(sport+"\t");
                    pw.println();
                   
                    pw.close();
                    fw.close();
                }catch(Exception e) {
                        System.out.println(e);
                }
        }
       
        public void removeRecord(int idOfRowToRemove){
                try {
                        File realFile = new File("C:\\participants.tsv");
                        File newFile = new File("C:\\participantstemp.tsv");
                       
                        BufferedReader br = new BufferedReader(new FileReader(realFile));
                        PrintWriter pw = new PrintWriter(new FileWriter(newFile));
                       
                        String row = null;
                       
                        while((row = br.readLine()) != null) {
                                st = new StringTokenizer(row,";");
                                if (!st.nextElement().equals(idOfRowToRemove)){
                                        pw.println(row);
                                        pw.flush();
                        }
                    }
                    pw.close();
                    br.close();
                       
                    if (!realFile.delete()) {
                        System.out.println("Could not delete file");
                        return;
                    }
                   
                    if (!newFile.renameTo(realFile))
                        System.out.println("Could not rename file");
                   
                }catch(Exception e) {
                        System.out.println(e);
                }
        }
       
        public void editRecord(Integer id, String name, String gender, String country, String date,
                                                        double height, double weight, String sport){
                try {
                        File realFile = new File("C:\\participants.tsv");
                        File newFile = new File("C:\\participantstemp.tsv");
                       
                        BufferedReader br = new BufferedReader(new FileReader(realFile));
                        PrintWriter pw = new PrintWriter(new FileWriter(newFile));
                       
                        String row = null;
                       
                        while((row = br.readLine()) != null) {
                                st = new StringTokenizer(row,";");
                                if (!st.nextElement().equals(id)){
                                        pw.println(row);
                                        pw.flush();
                        }
                                else {
                                        pw.print(id);
                                        pw.print(name);
                                        pw.print(gender);
                                        pw.print(country);
                                        pw.print(date);
                                        pw.print(height);
                                        pw.print(weight);
                                        pw.print(sport);
                                        pw.println();
                                }
                    }
                    pw.close();
                    br.close();
                       
                    if (!realFile.delete()) {
                        System.out.println("Could not delete file");
                        return;
                    }
                   
                    if (!newFile.renameTo(realFile))
                        System.out.println("Could not rename file");
                }catch(Exception e) {
                        System.out.println(e);
                }
        }
       
        public void close(){
                try {
                        out.close();
                        in.close();
                        TSVFile.close();
                        clientSocket.close();
                } catch (IOException e) {
                        System.out.println(e);
                }
        }
}
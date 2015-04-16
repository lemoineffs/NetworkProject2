package nog;

import java.io.Serializable;
 
 
public class Participant implements Serializable{
 
        /**
	 * 
	 */
	private static final long serialVersionUID = -2752369471822347974L;
		/**
         * @param args
         */
       
        int id;
        String name;
        String gender;
        String country;
        String dateofbirth;
        double height;
        double weight;
        String sport;
       
        public Participant(int id,
                String name,
                String gender,
                String country,
                String date,
                double height,
                double weight,
                String sport){
                this.id = id;
                this.name = name;
                this.gender = gender;
                this.country = country;
                this.dateofbirth = date;
                this.height = height;
                this.weight = weight;
                this.sport = sport;
               
        }
 
        public int getId(){
                return id;
        }
       
        public String getName(){
                return name;
        }
       
        public String getGender(){
                return gender;
        }
       
        public String getCountry(){
                return country;
        }
       
        public String getDate(){
                return dateofbirth;
        }
       
        public double getHeight(){
                return height;
        }
       
        public double getWeight(){
                return weight;
        }
       
        public String getSport(){
                return sport;
        }
       
}
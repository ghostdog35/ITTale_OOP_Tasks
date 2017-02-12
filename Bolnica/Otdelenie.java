package Bolnica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import Hospital_Persons.Doctor;
import Hospital_Persons.Nurse;
import Hospital_Persons.Pacient;

public class Otdelenie {
	private String name;
	private ArrayList<Doctor> doctori;
	private Nurse sestra;
	private TreeMap<Integer, HashMap<Integer, Pacient>> stai;
	
	public Otdelenie(String name) {
		this.name = name;
		this.doctori = new ArrayList<>();
		this.stai = new TreeMap<>();
		for (int i = 1; i < 11; i++) {
			this.stai.put(i, new HashMap<>());
			for (int j = 1; j < 4; j++) {
				this.stai.get(i).put(j, null);
			}
		}
		doctori.add(new Doctor("Ivan", "Ivanov", "0888123456", this.name));
		doctori.add(new Doctor("Ivan", "Petrov", "0888123456", this.name));
		sestra = new Nurse("Genka", "Shekerova", "0888123456", 15);
	}

	public void addPacient(Pacient b, String diagnoza) {
		System.out.println("Otdelenie "+this.name);
		for (int i = 1; i < 11; i++) {
			if(this.isRoomAvailable(i, b)){
				for (int j = 1; j < 4; j++) {
					if(this.stai.get(i).get(j)==null){
						this.stai.get(i).put(j, b);
						for (Doctor doctor : doctori) {
							if(doctor.checkIfAvailable()){
								doctor.addPacient(b, diagnoza,i,j);
								System.out.println("Пациент " +b.getFirstName() +" " + b.getLastName()+" от пол " +b.getGender()+ " e приет с диагноза "+diagnoza+". Лекуващ лекар: д-р" + doctor.getFirstName() +" " + doctor.getLastName()+".");
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
	}
	
	public boolean isRoomFree(Integer num){
		for (Entry<Integer, Pacient> bed : stai.get(num).entrySet()) {
			if(bed.getValue()!=null){
				return false;
			}
		}
		return true;
	}
	public boolean isRoomFull(Integer num){
		for (Entry<Integer, Pacient> bed : stai.get(num).entrySet()) {
			if(bed.getValue()==null){
				return false;
			}
		}
		return true;
	}
	public boolean isRoomAvailable(Integer num, Pacient b){
		if(this.isRoomFree(num))
			return true;
		if(this.isRoomFull(num))
			return false;
		boolean isAvailable = false;
		for (Entry<Integer, Pacient> bed : stai.get(num).entrySet()) {
			if(bed.getValue()!=null && bed.getValue().getGender()!=b.getGender()){
				isAvailable=false;
				break;
			}
			if(bed.getValue()!=null && bed.getValue().getGender()==b.getGender()){
				isAvailable=true;
			}
		}
		return isAvailable;
	}

	public void showInfo() {
		System.out.println("Otdelenie: "+this.name);
		for (Doctor doctor : doctori) {
			for (Carton carton : doctor.getPacienti()) {
				System.out.println(carton);
			}
		}
		
	}
	public void setBedFree(Integer room, Integer bed){
		this.stai.get(room).replace(bed, null);
	}
	public void izpisvane() {
		System.out.println("izpisvane na pacienti ot Otdelenie "+ this.name);
		for (Doctor doctor : doctori) {
			for (int i=0; i< doctor.getPacienti().size();i++) {
				Carton carton = doctor.getPacienti().get(i);
				if(carton!=null){
					if(carton.getDaysTreatment()<=0){
						System.out.println("Пациент " +carton.getPacient().getFirstName() +" " +carton.getPacient().getLastName()+" от пол " +carton.getPacient().getGender()+ " с диагноза "+carton.getdiagnoza()+" беше изписан.");
						this.setBedFree(carton.getStaq(), carton.getLeglo());
						doctor.getPacienti().remove(i);
					}
				}
				
			}
		}
		
	}

	public void razdavaneLekarstva() {
		System.out.println("Razdavane na lekarstva na pacienti v Otdelenie "+ this.name);
		for (Doctor doctor : doctori) {
			for (Carton carton : doctor.getPacienti()) {
				if(carton!=null){
					System.out.println("Сестра "+sestra.getFirstName() +" "+sestra.getLastName()+"даде на пациент " +carton.getPacient().getFirstName() +" " +carton.getPacient().getLastName()+" в стая " +carton.staq+ " от отделение "+this.name+" лекарство.");
				}
			}
		}
		
	}

	public void vizitaciq() {
		System.out.println("Vizitaciq na pacienti v Otdelenie "+ this.name);
		for (Doctor doctor : doctori) {
			for (Carton carton : doctor.getPacienti()) {
				if(carton!=null){
					System.out.println("Лекар "+doctor.getFirstName() +" "+doctor.getLastName()+" посети пациент " +carton.getPacient().getFirstName() +" " +carton.getPacient().getLastName()+" в стая " +carton.staq+ " от отделение "+this.name+".");
					carton.reduceDaysTreatment();
				}
			}
		}
	}
		
	public void getFreeBeds() {
		// TODO Auto-generated method stub
		
	}

	public void getCountOfPacients() {
		// TODO Auto-generated method stub
		
	}

	public void predstoqshtoIzpisvane() {
		System.out.println("Predstoqshto izpisvane na pacienti ot Otdelenie "+ this.name);
		for (Doctor doctor : doctori) {
			for (Carton carton : doctor.getPacienti()) {
				if(carton!=null){
					if(carton.getDaysTreatment()==1){
						System.out.println("Пациент " +carton.getPacient().getFirstName() +" " +carton.getPacient().getLastName()+" от пол " +carton.getPacient().getGender()+ " с диагноза "+carton.getdiagnoza()+" ще бъде изписан след 1 ден.");
						this.setBedFree(carton.getStaq(), carton.getLeglo());
					}
				}
			}
		}
		
	}
	
	

}

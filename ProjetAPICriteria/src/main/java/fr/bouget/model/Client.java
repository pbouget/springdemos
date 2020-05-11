package fr.bouget.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Client implements Serializable {
	public static final long serialVersionUID=987654378L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 45)
	private String nom;
	
	@Column(length = 45)
	
	private String prenom;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="ADDRESS_ID")
	private Adresse adresse;
	
	// Relation de 1 � plusieurs : un client peut avoir aucun ou plusieurs num�ros de t�l�phone
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private Collection<Telephone> telephones =new ArrayList<>();
	 
	// chiffre d'affaires 
	private Integer ca; 

	public Client(){}

	public Client(String nom, String prenom){
		this(nom, prenom, null);
	}

	public Client(String nom, String prenom, Adresse adresse){
		this(nom, prenom, adresse, 0);
	}
	
	public Client(String nom, String prenom, Adresse adresse, Integer ca){
		this.nom=nom;
		this.prenom=prenom;
		this.adresse=adresse;
		this.ca=ca;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	public Collection<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(Collection<Telephone> telephones) {
		this.telephones = telephones;
	}
	
	public Integer getCa() {
		return ca;
	}

	public void setCa(Integer ca) {
		this.ca = ca;
	}

	@Override
	    public String toString(){
		 
	        StringBuilder sb = new StringBuilder();
	        sb.append(nom+" "+prenom);
	        if(adresse!=null)
	        {
	        	sb.append(" "+adresse.toString());
	        }
	        if(telephones!=null && !telephones.isEmpty()){
	        	sb.append(telephones.toString());
	        }
	        return sb.toString();
	    }
}

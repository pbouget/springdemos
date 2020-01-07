package fr.bouget.jpa.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Croisiere implements Serializable {
	public static final long serialVersionUID=987654378L;

	private int id;
	private String nom;
	private int duree;
	// une croisi�re est reli�e � un et un seul Paquebot
	private Paquebot paquebot;
	
	// une croisi�re fait l'objet de plusieurs r�servations
	private Collection<Reservation> reservations;

	public Croisiere(){
		this(null, 0, null);
	}

	public Croisiere(String nom, int duree)
	{
		this(nom, duree, null);
	}

	public Croisiere(String nom, int duree, Paquebot paquebot){
		this.nom=nom;
		this.duree=duree;
		this.paquebot=paquebot;
		this.reservations = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	@Column(length = 45)
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	// Plusieurs croisi�res sont li�es � un paquebot
	//@ManyToOne(fetch=FetchType.EAGER) @ManyToOne(cascade={CascadeType.ALL})
	@ManyToOne 
	@JoinColumn(name="PAQUEBOT_ID")
	public Paquebot getPaquebot() {
		return paquebot;
	}

	public void setPaquebot(Paquebot paquebot) {
		this.paquebot = paquebot;
	}
	
	// croisiere est le nom de l'attribut dans l'entit� reservation
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="croisiere")
	@JsonIgnore
	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString(){
		return "Croisi�re : "+id+" "+nom+" "+duree+" (jours) "+paquebot;
	}
}

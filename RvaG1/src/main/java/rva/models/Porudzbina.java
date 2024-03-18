package rva.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Porudzbina implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PORUDZBINA_SEQ_GENERATOR", sequenceName = "PORUDZBINA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PORUDZBINA_SEQ_GENERATOR")
	private int id;
	private Date datum;
	private Date isporuceno;
	private double iznos;
	private boolean placeno;

	@OneToMany(mappedBy = "porudzbina", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<StavkaPorudzbine> stavke;

	@ManyToOne
	@JoinColumn(name = "dobavljac")
	private Dobavljac dobavljac;

	public Porudzbina() {

	}

	public Porudzbina(int id, Date datum, Date isporuceno, double iznos, boolean placeno) {
		this.id = id;
		this.datum = datum;
		this.isporuceno = isporuceno;
		this.iznos = iznos;
		this.placeno = placeno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Date getIsporuceno() {
		return isporuceno;
	}

	public void setIsporuceno(Date isporuceno) {
		this.isporuceno = isporuceno;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public boolean isPlaceno() {
		return placeno;
	}

	public void setPlaceno(boolean placeno) {
		this.placeno = placeno;
	}

	public List<StavkaPorudzbine> getStavke() {
		return stavke;
	}

	public void setStavke(List<StavkaPorudzbine> stavke) {
		this.stavke = stavke;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

}

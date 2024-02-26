package rva.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Artikl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ARTIKL_SEQ_GENERATOR", sequenceName = "ARTIKL_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTIKL_SEQ_GENERATOR")
	private int id;
	private String naziv;
	private String proizvodjac;
	
	@OneToMany(mappedBy = "artikl")
	private List<StavkaPorudzbine> stavke;

	public Artikl() {

	}

	public Artikl(int id, String naziv, String proizvodjac) {
		this.id = id;
		this.naziv = naziv;
		this.proizvodjac = proizvodjac;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

}

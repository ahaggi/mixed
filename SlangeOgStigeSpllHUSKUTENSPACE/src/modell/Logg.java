package modell;

 
import utsyn.StigeOgSlange;

/**
 * @author Peter Boer, Daniel Moberg og Abdella Haji
 *
 */
 
public class Logg {
 	private Integer loggID;
	private Integer spiller;
	private Integer spill;
	private Integer terning;
	private Integer fraRute;
	private Integer tilRute;
	private String handling;

	/**
	 * lager en ny Logg
	 * 
	 * @param loggID
	 * @param spiller
	 * @param spill
	 * @param terning
	 * @param fraRute
	 * @param tilRute
	 * @param handling
	 */
	public Logg(Integer loggID, Integer spiller, Integer spill, Integer terning, Integer fraRute, Integer tilRute,
			String handling) {
		super();
		this.loggID = loggID;
		this.spiller = spiller;
		this.spill = spill;
		this.terning = terning;
		this.fraRute = fraRute;
		this.tilRute = tilRute;
		this.handling = handling;

		// sjekker om spilleren har vunnet
		if (fraRute == 100) {
			System.out.println("Spilleren: " + spiller + " har vunnet");
		} else {
			System.out.println("Spillerrn: " + spiller + " fikk " + terning + " på terning" + " ForrigeRute: " + fraRute
					+ " FlyttetTilRute: " + tilRute + " Handling: " + handling);
		}

		StigeOgSlange.oppdater_komponenter(this);

	}

	/**
	 * tom konstruktør
	 */
	public Logg() {
		super();
	}

	public Integer getLoggID() {
		return loggID;
	}

	public void setLoggID(Integer loggID) {
		this.loggID = loggID;
	}

	public Integer getSpiller() {
		return spiller;
	}

	public void setSpiller(Integer spiller) {
		this.spiller = spiller;
	}

	public Integer getSpill() {
		return spill;
	}

	public void setSpill(Integer spill) {
		this.spill = spill;
	}

	public Integer getTerning() {
		return terning;
	}

	public void setTerning(Integer terning) {
		this.terning = terning;
	}

	public Integer getFraRute() {
		return fraRute;
	}

	public void setFraRute(Integer fraRute) {
		this.fraRute = fraRute;
	}

	public Integer getTilRute() {
		return tilRute;
	}

	public void setTilRute(Integer tilRute) {
		this.tilRute = tilRute;
	}

	public String getHandling() {
		return handling;
	}

	public void setHandling(String handling) {
		this.handling = handling;
	}

}

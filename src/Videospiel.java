
public class Videospiel implements Medium {

	private String _Titel;
	private String _OS;
	private String _Kommentar;
	
	
    /**
     * Initialisiert ein neues Exemplar.
     * 
     * @param titel Der Titel des Videospiels
     * @param kommentar Ein Kommentar zu dem Videospiel
     * @param os Das Betriebssystem auf dem das Videospiel läuft
     * 
     * @require titel != null
     * @require kommentar != null
     * @require os != null
     * 
     * @ensure getTitel() == titel
     * @ensure getKommentar() == kommentar
     * @ensure getPlattform() == os
     */
	public Videospiel(String titel, String kommentar, String os)
	{
        assert titel != null : "Vorbedingung verletzt: titel != null";
        assert kommentar != null : "Vorbedingung verletzt: kommentar != null";
        assert os != null : "Vorbedingung verletzt: os != null";
		
		
		_Titel = titel;
		_OS = os;
		_Kommentar = kommentar;
		
	
	}
	@Override
    /**
     * Gibt den Kommentar zu dem Videospiel zurück.
     * 
     * @return Den Kommentar zum Videospiel.
     * 
     * @ensure result != null
     */
	public String getKommentar() {
	
		return _Kommentar;
	}

	@Override
    /**
     * Gibt den Typ des Mediums zurück.
     * 
     * @return Typ des Mediums.
     * 
     * @ensure result != null
     */
	public String getMedienBezeichnung() {
	
		return "Videospiel";
	}

	@Override
    /**
     * Gibt den Titel des Videospiels zurück.
     * 
     * @return Den Titel des Videospiels
     * 
     * @ensure result != null
     */
	public String getTitel() {
		
		return _Titel;
	}
	
    /**
     * Gibt die Plattform auf der das Videospiel läuft zurück.
     * 
     * @return Die Plattform des Videospiels.
     * 
     * @ensure result != null
     */
	public String getPlattform()
	{
		return _OS;
	}
	
	public String getFormatierterString()
	{
		return getTitel()  +  "\n" +  getPlattform() +  "\n" +  getKommentar();
	}

}

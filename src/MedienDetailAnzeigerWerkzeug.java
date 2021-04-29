import java.util.List;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * Ein MedienDetailAnzeigerWerkzeug ist ein Werkzeug um die Details von Medien
 * anzuzeigen.
 * 
 * @author SE2-Team, PM2-Team
 * @version SoSe 2021
 */
class MedienDetailAnzeigerWerkzeug
{
    private MedienDetailAnzeigerUI _ui;

    /**
     * Initialisiert ein neues MedienDetailAnzeigerWerkzeug.
     */
    public MedienDetailAnzeigerWerkzeug()
    {
        _ui = new MedienDetailAnzeigerUI();
    }

    /**
     * Setzt die Liste der Medien deren Details angezeigt werden sollen.
     * 
     * @param medien Eine Liste von Medien.
     * 
     * @require (medien != null)
     */
    public void setMedien(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: (medien != null)";
    	
    	for(Medium medium : medien)
    	{
    	        TextArea selectedMedienTextArea = _ui.getMedienAnzeigerTextArea();
    	        selectedMedienTextArea.setText(medium.getFormatierterString());
       	}

    }
    
//  public void setMedien(List<Medium> medien)
//  {
//      assert medien != null : "Vorbedingung verletzt: (medien != null)";
//
//  	for(Medium medium : medien)
//  	{
//  		if(medium instanceof Videospiel)
//  		{
//  			Videospiel lokal = (Videospiel) medium;
//  	        TextArea selectedMedienTextArea = _ui.getMedienAnzeigerTextArea();
//  	        selectedMedienTextArea.setText( "Titel : " + lokal.getTitel()  +  "\n" + "System: " + lokal.getSystem() +  "\n" + "Kommentar: " + lokal.getKommentar());
//  		}
//  		else if(medium instanceof CD)
//  		{
//  			CD lokal = (CD) medium;
//  	        TextArea selectedMedienTextArea = _ui.getMedienAnzeigerTextArea();
//  	        selectedMedienTextArea.setText("Titel: " + lokal.getTitel()  +  "\n" +"Interpret: " + lokal.getInterpret() +  "\n" +"Länge: " + lokal.getSpiellaenge() +  "\n" + "Kommentar: " + lokal.getKommentar());
//  		}
//  		else if(medium instanceof DVD)
//  		{
//  			DVD lokal = (DVD) medium;
//  	        TextArea selectedMedienTextArea = _ui.getMedienAnzeigerTextArea();
//  	        selectedMedienTextArea.setText("Titel: " + lokal.getTitel()  + "\n" +  "Regisseur: " + lokal.getRegisseur() +  "\n" + "Laufzeit: " + lokal.getLaufzeit()+  "\n" + "Kommentar: " + lokal.getKommentar());
//  		}
//
//  	}
//
//  }

    /**
     * Gibt das Panel dieses Subwerkzeugs zurück.
     * 
     * @ensure result != null
     */
    public Pane getUIPane()
    {
        return _ui.getUIPane();
    }
}

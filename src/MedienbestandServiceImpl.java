import java.util.ArrayList;
import java.util.List;

/**
 * Ein Medienbestand enthält alle zur Verfügung stehenden Medien. Diese können
 * in den Bestand eingepflegt, gelöscht und verändert werden. Zu einem
 * bestimmten Titel kann es mehrere Medien-Objekte im Bestand geben. So kann
 * z.B. die gleiche CD mehrfach vorhanden sein.
 * 
 * @author SE2-Team, PM2-Team
 * @version SoSe 2021
 */
class MedienbestandServiceImpl extends AbstractObservableService
        implements MedienbestandService
{

    /**
     * Eine Liste aller Medien
     */
    private List<Medium> _medienbestand;

    /**
     * Initialisiert einen neuen Medienbestand.
     * 
     * @param medien Der initiale Medienbestand.
     */
    public MedienbestandServiceImpl(List<Medium> medien)
    {
        _medienbestand = new ArrayList<Medium>(medien);
    }

    @Override
    /**
     * Entfernt ein Medium aus dem Medienbestand, z.B. wenn es verloren gegangen
     * ist oder so veraltet, dass es von den Mediathek-Kunden nicht mehr
     * nachgefragt wird.
     * 
     * @param medium Ein zu entfernendes Medium
     * 
     * @require enthaeltMedium(medium)
     * 
     * @ensure !enthaeltMedium(medium)
     */
    public void entferneMedium(Medium medium)
    {
    	assert enthaeltMedium(medium);
    	
        _medienbestand.remove(medium);

        informiereUeberAenderung();
    }

    @Override
    /**
     * Gibt Auskunft, ob ein Medium im Medienbestand enthalten ist.
     * 
     * @param medium Ein Medium
     * @return true, wenn Medium im Medienbestand enthalten ist, andernfalls
     *         false.
     *         
     * @require medium != null 
     * 
     *  
     */
    public boolean enthaeltMedium(Medium medium)
    {
    	assert medium != null;
    	
        return _medienbestand.contains(medium);
    }

    @Override
    public void fuegeMediumEin(Medium neuesMedium)
    {
    	assert !enthaeltMedium(neuesMedium);
    	
        _medienbestand.add(neuesMedium);

        informiereUeberAenderung();
    }

    @Override
    /**
     * Liefert alle vorhandenen Medien.
     * 
     * @ensure result != null
     * 
     * @return Eine Kopie der Liste mit allen vorhandenen Medien.
     */
    public List<Medium> getMedien()
    {
        return new ArrayList<Medium>(_medienbestand);
    }

    @Override
    /**
     * Informiert diesen Service darüber, dass Medien von einem Werkzeug
     * geändert wurden. Eine Implementation wird daraufhin wahrscheinlich alle
     * ServiceBeobachter darüber informieren.
     */
    public void medienWurdenGeaendert()
    {
        informiereUeberAenderung();
    }

}

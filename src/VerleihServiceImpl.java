import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team, PM2-Team
 * @version SoSe 2021
 */
class VerleihServiceImpl extends AbstractObservableService implements
        VerleihService
{
    /**
     * Diese Map speichert für jedes eingefügte Medium die dazugehörige
     * Verleihkarte. Ein Zugriff auf die Verleihkarte ist dadurch leicht über
     * die Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
     */
    private Map<Medium, Verleihkarte> _verleihkarten;

    /**
     * Der Medienbestand.
     */
    private MedienbestandService _medienbestand;

    /**
     * Der Kundenstamm.
     */
    private KundenstammService _kundenstamm;

    /**
     * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
     * 
     * @param kundenstamm Der KundenstammService.
     * @param medienbestand Der MedienbestandService.
     * @param initialBestand Der initiale Bestand.
     * 
     */
    public VerleihServiceImpl(KundenstammService kundenstamm,
            MedienbestandService medienbestand,
            List<Verleihkarte> initialBestand)
    {
        _verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
        _kundenstamm = kundenstamm;
        _medienbestand = medienbestand;
    }

    /**
     * Erzeugt eine neue HashMap aus dem Initialbestand.
     */
    private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(
            List<Verleihkarte> initialBestand)
    {
        HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
        for (Verleihkarte verleihkarte : initialBestand)
        {
            result.put(verleihkarte.getMedium(), verleihkarte);
        }
        return result;
    }

    @Override
    /**
     * @return Eine Listenkopie aller Verleihkarten. Für jedes ausgeliehene
     *         Medium existiert eine Verleihkarte. Ist kein Medium verliehen,
     *         wird eine leere Liste zurückgegeben.
     * 
     * @ensure result != null
     */
    public List<Verleihkarte> getVerleihkarten()
    {
        return new ArrayList<Verleihkarte>(_verleihkarten.values());
    }

    @Override
    /**
     * Prüft ob das angegebene Medium verliehen ist.
     * 
     * @param medium Ein Medium, für das geprüft werden soll ob es verliehen
     *            ist.
     * @return true, wenn das gegebene medium verliehen ist, sonst false.
     * 
     * @require mediumImBestand(medium)
     */
    public boolean istVerliehen(Medium medium)
    {
    	assert mediumImBestand(medium) == true;
        return _verleihkarten.get(medium) != null;
    }

    @Override
    /**
     * Prüft ob die ausgewählten Medium für den Kunde ausleihbar sind
     * 
     * @param kunde Der Kunde für den geprüft werden soll
     * @param medien Die medien
     * 
     * 
     * @return true, wenn das Entleihen für diesen Kunden möglich ist, sonst
     *         false
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     * 
     */
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
    {
    	assert kundeImBestand(kunde) == true;
    	assert medienImBestand(medien) == true;
        return sindAlleNichtVerliehen(medien);
    }

    @Override
    /**
     * Nimmt zuvor ausgeliehene Medien zurück. Die entsprechenden Verleihkarten
     * werden gelöscht.
     * 
     * @param medien Die Medien.
     * @param rueckgabeDatum Das Rückgabedatum.
     * 
     * @require sindAlleVerliehen(medien)
     * @require rueckgabeDatum != null
     * 
     * @ensure sindAlleNichtVerliehen(medien)
     */
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
    {
    	assert sindAlleVerliehen(medien) == true;
    	assert rueckgabeDatum != null;
        for (Medium medium : medien)
        {
            _verleihkarten.remove(medium);
        }
        informiereUeberAenderung();
    }

    @Override
    /**
     * Prüft ob alle angegebenen Medien nicht verliehen sind.
     * 
     * @param medien Eine Liste von Medien.
     * @return true, wenn alle gegebenen Medien nicht verliehen sind, sonst
     *         false (auch wenn die Liste leer ist).
     * 
     * @require medienImBestand(medien)
     */
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {
    	assert medienImBestand(medien) == true;
        boolean result = true;
        for (Medium medium : medien)
        {
            if (istVerliehen(medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    /**
     * Prüft ob alle angegebenen Medien verliehen sind.
     * 
     * @param medien Eine Liste von Medien.
     * 
     * @return true, wenn alle gegebenen Medien verliehen sind, sonst false
     *         (auch wenn die Liste leer ist).
     * 
     * @require medienImBestand(medien)
     */
    public boolean sindAlleVerliehen(List<Medium> medien)
    {
    	assert medienImBestand(medien) == true;
        boolean result = true;
        for (Medium medium : medien)
        {
            if (!istVerliehen(medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    /**
     * Verleiht Medien an einen Kunden. Dabei wird für jedes Medium eine neue
     * Verleihkarte angelegt.
     * 
     * @param kunde Ein Kunde, an den ein Medium verliehen werden soll
     * @param medien Die Medien, die verliehen werden sollen
     * @param ausleihDatum Der erste Ausleihtag
     * 
     * @require kundeImBestand(kunde)
     * @require sindAlleNichtVerliehen(medien)
     * @require ausleihDatum != null
     * 
     * @ensure sindAlleVerliehen(medien)
     */
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
    {
    	assert kundeImBestand(kunde) == true;
    	assert sindAlleNichtVerliehen(medien) == true;
    	assert ausleihDatum != null;
    	
        for (Medium medium : medien)
        {
            Verleihkarte karte = new Verleihkarte(kunde, medium, ausleihDatum);
            _verleihkarten.put(medium, karte);

        }
       
        informiereUeberAenderung();
        
    }

    @Override
    /**
     * Prüft ob der angebene Kunde existiert. Ein Kunde existiert, wenn er im
     * Kundenstamm enthalten ist.
     * 
     * @param kunde Ein Kunde.
     * @return true wenn der Kunde existiert, sonst false.
     * 
     * @require kunde != null
     */
    public boolean kundeImBestand(Kunde kunde)
    {
    	assert kunde != null;
        return _kundenstamm.enthaeltKunden(kunde);
    }

    @Override
    /**
     * Prüft ob das angebene Medium existiert. Ein Medium existiert, wenn es im
     * Medienbestand enthalten ist.
     * 
     * @param medium Ein Medium.
     * @return true wenn das Medium existiert, sonst false.
     * 
     * @require medium != null
     */
    public boolean mediumImBestand(Medium medium)
    {
    	assert medium != null;
        return _medienbestand.enthaeltMedium(medium);
    }

    @Override
    /**
     * Prüft ob die angebenen Medien existierien. Ein Medium existiert, wenn es
     * im Medienbestand enthalten ist.
     * 
     * @param medien Eine Liste von Medien.
     * @return true wenn die Medien existieren, sonst false.
     * 
     * @require medien != null
     * @require !medien.isEmpty()
     */
    public boolean medienImBestand(List<Medium> medien)
    {
    	assert medien != null;
    	assert !medien.isEmpty() == true;
    	
        boolean result = true;
        for (Medium medium : medien)
        {
            if (!mediumImBestand(medium))
            {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    /**
     * Liefert alle Medien, die von dem gegebenen Kunden ausgeliehen sind.
     * 
     * @param kunde Der Kunde.
     * @return Alle Medien, die von dem gegebenen Kunden ausgeliehen sind.
     *         Liefert eine leere Liste, wenn der Kunde aktuell nichts
     *         ausgeliehen hat.
     * 
     * @require kundeImBestand(kunde)
     * 
     * @ensure result != null
     */
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
    {
    	assert kundeImBestand(kunde) == true;
        List<Medium> result = new ArrayList<Medium>();
        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher().equals(kunde))
            {
                result.add(verleihkarte.getMedium());
            }
        }
        return result;
    }

    @Override
    /**
     * Liefert den Entleiher des angegebenen Mediums.
     * 
     * @param medium Das Medium.
     * 
     * @return Den Entleiher des Mediums.
     * 
     * @require istVerliehen(medium)
     * 
     * @ensure result != null
     */
    public Kunde getEntleiherFuer(Medium medium)
    {
    	assert istVerliehen(medium) == true;
        Verleihkarte verleihkarte = _verleihkarten.get(medium);
        return verleihkarte.getEntleiher();
    }

    @Override
    /**
     * Gibt die Verleihkarte für das angegebene Medium zurück, oder null wenn
     * das Medium nicht verliehen ist.
     * 
     * @param medium Ein Medium.
     * @return Die Verleihkarte für das angegebene Medium.
     * 
     * @require istVerliehen(medium)
     * 
     * @ensure (result != null)
     */
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
    	assert istVerliehen(medium) == true;
        return _verleihkarten.get(medium);
    }

    @Override
    /**
     * Gibt alle Verleihkarten für den angegebenen Kunden zurück.
     * 
     * @param kunde Ein Kunde.
     * @return Alle Verleihkarten des angebenen Kunden. Eine leere Liste, wenn
     *         der Kunde nichts entliehen hat.
     * 
     * @require kundeImBestand(kunde)
     * 
     * @ensure result != null
     */
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
    {
    	assert kundeImBestand(kunde) == true;
        List<Verleihkarte> result = new ArrayList<Verleihkarte>();
        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher().equals(kunde))
            {
                result.add(verleihkarte);
            }
        }
        return result;
    }

}
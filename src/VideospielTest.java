import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VideospielTest {

	private static final String OS = "Operating System";
	private static final String Titel = "Titel";
	private static final String Kommentar ="Kommentar zum Spiel";
	private static final String Bezeichnung = "Videospiel";
	private Videospiel _spiel1;
	private Videospiel _spiel2;
	
	
	public VideospielTest()
	{
		_spiel1 = getMedium();
		_spiel2 = getMedium();
	}
	
	
    @Test
    public void testGetMedienBezeichnung()
    {
        assertEquals(Bezeichnung, _spiel1.getMedienBezeichnung());
    }

    @Test
    public void testKonstruktor()
    {
        assertEquals(Titel, _spiel1.getTitel());
        assertEquals(Kommentar, _spiel1.getKommentar());
        assertEquals(OS, _spiel1.getPlattform());
    }
	
	
    @Test
    /*
     * Von ein und dasselbe Videospiel kann es mehrere Exemplare geben, die von
     * unterschiedlichen Personen ausgeliehen werden können.
     */
    public void testEquals()
    {
        assertFalse("Mehrere Exemplare des gleichen Videospiels sind ungleich",
                _spiel1.equals(_spiel2));
        assertTrue("Dasselbe Exemplare der gleichen Videospiels ist gleich",
                _spiel1.equals(_spiel1));
    }

	
	private Videospiel getMedium()
	{
		return new Videospiel(Titel, Kommentar, OS);
	}
}

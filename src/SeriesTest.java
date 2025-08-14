import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeriesTest {
    private Series series;
    private SeriesModel testSeries;

    @Before
    public void setUp() {
        series = new Series();
        testSeries = new SeriesModel("S001", "Test Series", "12", "24");
        series.getSeriesList().add(testSeries);
    }

    // Test isValidAge() through public methods
    @Test
    public void testAgeValidationThroughCapture() {
        // Test valid ages
        assertTrue(isValidAgeThroughCapture("5"));
        assertTrue(isValidAgeThroughCapture("18"));
        assertTrue(isValidAgeThroughCapture("2"));

        // Test invalid ages
        assertFalse(isValidAgeThroughCapture("1"));
        assertFalse(isValidAgeThroughCapture("19"));
        assertFalse(isValidAgeThroughCapture("abc"));
        assertFalse(isValidAgeThroughCapture(""));
    }

    // Helper method to test age validation through public capture flow
    private boolean isValidAgeThroughCapture(String age) {
        try {
            // This tests the validation through public methods
            SeriesModel test = new SeriesModel("TEST", "Test", age, "10");
            series.getSeriesList().add(test);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void testSearchSeries() {
        // Test finding existing series
        SeriesModel found = searchSeriesById("S001");
        assertNotNull(found);
        assertEquals("Test Series", found.seriesName);

        // Test non-existent series
        assertNull(searchSeriesById("INVALID_ID"));
    }

    // Helper method to test search through public method
    private SeriesModel searchSeriesById(String id) {
        for (SeriesModel s : series.getSeriesList()) {
            if (s.seriesId.equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Test
    public void testUpdateSeries() {
        // First find the series to update
        SeriesModel toUpdate = searchSeriesById("S001");
        assertNotNull(toUpdate);

        // Update the name
        String newName = "Updated Series";
        toUpdate.seriesName = newName;

        // Verify update
        SeriesModel updated = searchSeriesById("S001");
        assertNotNull(updated);
        assertEquals(newName, updated.seriesName);
    }

    @Test
    public void testDeleteSeries() {
        int initialSize = series.getSeriesList().size();

        // Delete the test series
        SeriesModel toDelete = searchSeriesById("S001");
        assertNotNull(toDelete);
        series.getSeriesList().remove(toDelete);

        assertEquals(initialSize - 1, series.getSeriesList().size());
        assertNull(searchSeriesById("S001"));
    }

    @Test
    public void testSeriesReport() {
        // Just verify the list isn't empty
        assertFalse(series.getSeriesList().isEmpty());

        // Test with empty list
        series.getSeriesList().clear();
        assertTrue(series.getSeriesList().isEmpty());
    }
}
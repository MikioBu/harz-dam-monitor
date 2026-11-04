package de.ostfalia.application.data.querybuilder;

import de.ostfalia.application.data.entity.Talsperre;
import de.ostfalia.application.data.records.TalsperreRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Beispieldaten fuer den Fall, dass keine Verbindung zur Influx DB konfiguriert ist.
 * Die Werte sind erfunden, aber in einer realistischen Groessenordnung.
 */
public final class DemoData {

    // Talsperre -> ungefaehrer Inhalt in Mio. m3
    private static final Map<String, Double> RESERVOIRS = Map.of(
            "eckertalsperre", 12.0,
            "granetalsperre", 40.0,
            "innerstetalsperre", 17.0,
            "odertalsperre", 25.0,
            "okertalsperre", 42.0,
            "soesetalsperre", 22.0
    );

    private static final int DAYS = 14;
    private static final int HOURS_PER_POINT = 6;

    private DemoData() {
    }

    public static List<Talsperre> reservoirs() {
        List<Talsperre> result = new ArrayList<>();
        RESERVOIRS.keySet().stream().sorted().forEach(name -> result.add(new Talsperre(name)));
        return result;
    }

    /**
     * Messwerte der letzten 14 Tage fuer eine Talsperre und ein Feld (inflow, outflow oder storage)
     */
    public static List<TalsperreRecord> records(String reservoir, Set<String> fields) {
        List<TalsperreRecord> result = new ArrayList<>();
        if (reservoir == null || !RESERVOIRS.containsKey(reservoir)) {
            return result;
        }
        for (String field : fields) {
            result.addAll(series(reservoir, field));
        }
        return result;
    }

    private static List<TalsperreRecord> series(String reservoir, String field) {
        List<TalsperreRecord> result = new ArrayList<>();
        // gleicher Seed => bei jedem Aufruf dieselben Werte
        Random random = new Random((reservoir + field).hashCode());
        Instant stop = Instant.now().truncatedTo(ChronoUnit.HOURS);
        Instant start = stop.minus(DAYS, ChronoUnit.DAYS);
        int points = DAYS * 24 / HOURS_PER_POINT;
        double base = RESERVOIRS.get(reservoir);

        for (int i = 0; i <= points; i++) {
            double wave = Math.sin(i / 6.0);
            double value;
            switch (field) {
                case "storage" -> value = base * (0.8 + 0.05 * wave) + random.nextDouble() * 0.2;
                case "inflow" -> value = base / 20 * (1 + 0.4 * wave) + random.nextDouble() * 0.3;
                default -> value = base / 25 + random.nextDouble() * 0.2;
            }
            TalsperreRecord record = new TalsperreRecord();
            record.setReservoir(reservoir);
            record.setStart(start);
            record.setStop(stop);
            record.setTime(start.plus((long) i * HOURS_PER_POINT, ChronoUnit.HOURS));
            record.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP));
            result.add(record);
        }
        return result;
    }
}

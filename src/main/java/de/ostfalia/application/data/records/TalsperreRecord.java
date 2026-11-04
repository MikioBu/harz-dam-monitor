package de.ostfalia.application.data.records;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Diese Klasse stellt einen einzelnen Messwert (Datenpunkt) einer Talsperre dar
 */
@Measurement(name = "reservoir_data")
public class TalsperreRecord {
    @Column(name = "value")
    private BigDecimal value;
    @Column(timestamp = true)
    private Instant time;
    // Beginn der Messung
    @Column(name = "start")
    private Instant start;
    // Ende der Messung
    @Column(name = "stop")
    private Instant stop;
    // name der Talsperre
    @Column
    private String reservoir;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStop() {
        return stop;
    }

    public void setStop(Instant stop) {
        this.stop = stop;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getReservoir() {
        return reservoir;
    }

    public void setReservoir(String reservoir) {
        this.reservoir = reservoir;
    }

    @Override
    public String toString() {
        return "TalsperreRecord{" +
                "value=" + value +
                ", time=" + time +
                ", start=" + start +
                ", stop=" + stop +
                ", reservoir='" + reservoir + '\'' +
                '}';
    }

}

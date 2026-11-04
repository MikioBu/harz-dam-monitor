package de.ostfalia.application.data.service;

import com.influxdb.query.FluxTable;
import de.ostfalia.application.data.querybuilder.DemoData;
import de.ostfalia.application.data.querybuilder.TalsperreQuery;
import de.ostfalia.application.data.entity.Talsperre;
import de.ostfalia.application.data.records.TalsperreRecord;

import java.util.ArrayList;
import java.util.List;


public class TalsperrenService {

    public Talsperre past14Days(String talsperrenName){
        List<TalsperreRecord> inflow =new TalsperreQuery().reservoirName(talsperrenName)
                .start("-14d").inflow().create();
        List<TalsperreRecord> outflow = new TalsperreQuery().reservoirName(talsperrenName)
                .start("-14d").outflow().create();
        List<TalsperreRecord> storage = new TalsperreQuery().reservoirName(talsperrenName)
                .start("-14d").storage().create();
        Talsperre talsperre = new Talsperre(talsperrenName);
        talsperre.setInflowValues(inflow);
        talsperre.setOutflowValues(outflow);
        talsperre.setStorageValues(storage);
        return talsperre;
    }

    public List<Talsperre> getAll(){
        // hier wurde nicht mit dem querybuilder TalsperreQuery gearbeitet wie oben, durch die menge an edge cases

        if (!TalsperreQuery.isConfigured()) {
            return DemoData.reservoirs();
        }

        // range start muss gesetzt sein, sonst koennen keine werte aus influx gelesen werden
        List<FluxTable> sperren = TalsperreQuery.client().getQueryApi().query(
                "from(bucket: \"Harzerwasserwerke\")\n" +
                        "|> range(start: -30d)\n" +
                        "|> keyValues(keyColumns: [\"reservoir\"])\n" +
                        "|> group()\n" +
                        "|> keep(columns: [\"reservoir\"])\n" +
                        "|> distinct(column: \"reservoir\")");

        ArrayList<Talsperre> result = new ArrayList<>();
        for (FluxTable table: sperren) {
            table.getRecords().forEach(fluxRecord -> {
                result.add(new Talsperre(fluxRecord.getValue().toString()));
            });
        }

        return result;
    }
}

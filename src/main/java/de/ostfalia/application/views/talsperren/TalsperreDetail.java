package de.ostfalia.application.views.talsperren;

import com.storedobject.chart.*;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.ostfalia.application.data.entity.Talsperre;
import de.ostfalia.application.data.service.TalsperrenService;
import de.ostfalia.application.views.BasicLayout;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Route(value = "/detail")
@PageTitle("detailseite")
public class TalsperreDetail extends BasicLayout implements HasUrlParameter<String> {
    private TalsperrenService service;
    private Talsperre talsperre;


    /**
     *
    wird automatisch nach den Kontruktor aufgerufen
     */
    @Override
    public void setParameter(BeforeEvent event, String name) {
        this.service = new TalsperrenService();
        this.talsperre = service.past14Days(name);
        setContent(soChart());
        this.getTitle().setText("Detailseite");


    }
    public SOChart soChart(){

        SOChart soChart = new SOChart();
        soChart.setSize("100%", "500px");
        soChart.add(new Title(this.talsperre.captializedName() + " 14 Tage Übersicht"));

        TimeData xValues = new TimeData();
        Data abgabe = new Data();
        Data zufluss = new Data();
        Data inhalt = new Data();
        this.talsperre.getInflowValues().forEach(record -> {
            zufluss.add(record.getValue());
            xValues.add(LocalDateTime.ofInstant(record.getTime(), ZoneId.of("Europe/Berlin")));
        });
        this.talsperre.getOutflowValues().forEach(record -> {
            abgabe.add(record.getValue());
            xValues.add(LocalDateTime.ofInstant(record.getTime(), ZoneId.of("Europe/Berlin")));
        });
        this.talsperre.getStorageValues().forEach(record ->{
            inhalt.add(record.getValue());
            xValues.add(LocalDateTime.ofInstant(record.getTime(), ZoneId.of("Europe/Berlin")));
        });


     //Line chart mit x und y werten fuellen
        LineChart lineAbgabe = new LineChart(xValues, abgabe);
        lineAbgabe.setName("Wasserabgabe");
        LineChart lineZufluss = new LineChart(xValues,zufluss);
        lineZufluss.setName("Zufluss");
        LineChart lineInhalt = new LineChart(xValues, inhalt);
        lineInhalt.setName("Inhalt");

        // x achse fuer das frontend
        XAxis xAxis = new XAxis(DataType.DATE);
        //xAxis.setDivisions(24);// 24 stunden
        xAxis.setName("Tag"); // da nur ein Tag angezeigt
        // y achse im frontend
        YAxis yAxis = new YAxis(DataType.NUMBER);
        yAxis.setName("Wassermenge");
        // rechtwinklige achsen
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        // line chart im rechtwinkligen koordinatensystem zeichnen
        lineAbgabe.plotOn(rc);
        lineZufluss.plotOn(rc);
        lineInhalt.plotOn(rc);

        // line charts der umfassenden Eltern Componente hinzufuegen
        soChart.add(lineAbgabe);
        soChart.add(lineZufluss);
        soChart.add(lineInhalt);

        return  soChart;
    }

}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class MapPortsPageBean {
    private MapModel simpleModel;
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private List<PortDetailsDto> activePorts;

    private Marker marker;
    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        this.activePorts = portEndpoint.getAllPorts().stream().filter(portDetailsDto -> portDetailsDto.getActive().equals(true)).collect(Collectors.toList());

        for (PortDetailsDto portDetailsDto: activePorts){
            simpleModel.addOverlay(new Marker(new LatLng(portDetailsDto.getLat().floatValue(),portDetailsDto.getLong1().floatValue()), portDetailsDto.getName()));
        }

    }
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
}

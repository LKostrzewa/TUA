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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class MapPortsPageBean implements Serializable {
    private MapModel simpleModel;
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private List<PortDetailsDto> activePorts;

    private Marker marker;
    private PortDetailsDto selectedPort;
    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        this.activePorts = portEndpoint.getAllPorts().stream().filter(portDetailsDto -> portDetailsDto.getActive().equals(true)).collect(Collectors.toList());

        StringBuilder ruta = new StringBuilder();
        ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        ruta.append("/resources/images/markerPort.png");

        for (PortDetailsDto portDetailsDto: activePorts){
            Marker marker = new Marker(new LatLng(portDetailsDto.getLat().floatValue(),portDetailsDto.getLong1().floatValue()), portDetailsDto.getName());
            marker.setIcon(String.valueOf(ruta));
            simpleModel.addOverlay(marker);
        }

    }
    public void onMarkerSelect(OverlaySelectEvent event) throws Exception {
        marker = (Marker) event.getOverlay();
        selectedPort = (PortDetailsDto) activePorts.stream().filter(n -> n.getName().equals(marker.getTitle())).findFirst().orElseThrow(Exception::new);
    }

    public PortDetailsDto getSelectedPort() {
        return selectedPort;
    }

    public Marker getMarker() {
        return marker;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
}

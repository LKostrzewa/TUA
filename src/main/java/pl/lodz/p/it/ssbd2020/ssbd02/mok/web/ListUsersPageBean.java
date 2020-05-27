package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ListUsersDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Klasa do obsługi widoku listy użytkowników i sortowania użytkowników
 */
@Named
@ViewScoped
public class ListUsersPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private LazyDataModel<ListUsersDto> model;

    /**
     * Metoda inicjalizująca komponent
     */
    @PostConstruct
    public void init() {
        model = new LazyDataModel<>() {
            @Override
            public List<ListUsersDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
                model.setRowCount(userEndpoint.getFilteredRowCount(filters));
                return userEndpoint.getResultList(first, pageSize, filters);
            }
        };
    }

    public LazyDataModel<ListUsersDto> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<ListUsersDto> model) {
        this.model = model;
    }
}


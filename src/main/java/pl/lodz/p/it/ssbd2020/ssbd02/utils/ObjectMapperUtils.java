package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa mapująca korzystająca z biblioteki zewnętrznej ModelMapper.
 */
public class ObjectMapperUtils {
    private static final ModelMapper modelMapper;

    /**
     * Ustawianie domyślnej strategii dopasowywania właściwości klas na Strict
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Prywatny konstruktor do uniemożliwienia publicznego dostępu.
     */
    private ObjectMapperUtils() {
    }

    /**
     * Meteoda mapuje obiekt na obiekt o podanym typie.
     *
     * @param <D>      typ obiektu wynikowego
     * @param <T>      typ mapowanego obiektu źródłowego
     * @param entity   encja, która ma zostać zmapowana
     * @param outClass klasa obiektu wynikowego
     * @return nowy obiekt typu <code>outClass</code>
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * Meteoda mapuje obiekty na obiekty o podanym typie.
     *
     * @param entityList lista encji, które mają zostać zmapowane
     * @param outCLass   klasa elementu listy wynikowej
     * @param <D>        typ obiektów z listy wynikowej
     * @param <T>        typ encji <code>entityList</code>
     * @return lista zmapowanych obiektów typu <code><D></code>
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}

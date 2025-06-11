package ar.com.gabriel.cart.helpers;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gabriel Gonzalez
 */
public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}

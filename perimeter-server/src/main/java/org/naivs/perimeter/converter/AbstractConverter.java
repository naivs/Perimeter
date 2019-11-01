package org.naivs.perimeter.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.naivs.perimeter.exception.ConverterException;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.rest.to.PhotoDto;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AbstractConverter {

    private ModelMapper modelMapper;

    public AbstractConverter() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(PhotoDto.class, org.naivs.perimeter.smarthome.data.entity.Photo.class);
        modelMapper.createTypeMap(org.naivs.perimeter.smarthome.data.entity.Photo.class, PhotoDto.class)
                .addMappings(mapping -> mapping.map(
                        org.naivs.perimeter.smarthome.data.entity.Photo::getThumbnail,
                        PhotoDto::setThumbnailName));
    }

    public <D> D convert(Object source, Type destinationType) {
        if (Objects.isNull(source)) {
            return null;
        } else {
            check(source, destinationType);
            return modelMapper.map(source, destinationType);
        }
    }

    public <D, T> List<D> convert(final Collection<T> source, Class<D> destination) {
        check(source, destination);
        return source.stream()
                .map(src -> modelMapper.map(src, destination))
                .collect(Collectors.toList());
    }

    public <D, T> Set<D> convert(final Set<T> source, Class<D> destination) {
        check(source, destination);
        return source.stream()
                .map(src -> modelMapper.map(src, destination))
                .collect(Collectors.toSet());
    }

    public List<PhotoIndex> convert(Path path) {
        ArrayList<PhotoIndex> indexList = new ArrayList<>();
        String[] indexNames = path.normalize().toString().split("[/]");
        for (String indexName : indexNames) {
            if (!indexName.isEmpty()) {
                PhotoIndex photoIndex = new PhotoIndex();
                photoIndex.setName(indexName);
                indexList.add(photoIndex);
            }
        }
        return indexList;
    }

    public String convert(List<String> indexes) {
        StringBuilder path = new StringBuilder();
        for (String index : indexes) {
            if (!index.isEmpty()) {
                path.append(index).append("/");
            }
        }
        return path.toString();
    }

    private synchronized <D> void check(Object source, D destination) {
        final Class<?> clazz;
        if (source instanceof Collection<?>) {
            if (((Collection) source).iterator().hasNext()) {
                clazz = ((Collection) source).iterator().next().getClass();
            } else {
                return;
            }
        } else {
            clazz = source.getClass();
        }

        if (modelMapper.getTypeMaps().stream().noneMatch(typeMap ->
                typeMap.getSourceType().equals(clazz) &&
                        typeMap.getDestinationType().equals(destination)
        )) throw new ConverterException(clazz, destination);
    }
}

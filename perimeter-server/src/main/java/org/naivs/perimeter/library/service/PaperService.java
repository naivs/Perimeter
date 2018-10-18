package org.naivs.perimeter.library.service;

import org.naivs.perimeter.library.data.PaperEntity;
import org.naivs.perimeter.library.data.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public void add(PaperEntity paperEntity) {
        paperRepository.save(paperEntity);
    }

    public void remove(Long id) {
        paperRepository.deleteById(id);
    }

    private List<PaperEntity> getAll() {
        return paperRepository.findAll();
    }
}

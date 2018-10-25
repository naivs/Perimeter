package org.naivs.perimeter.library.service;

import org.naivs.perimeter.library.data.AuthorEntity;
import org.naivs.perimeter.library.data.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void add(AuthorEntity authorEntity) {
        authorRepository.save(authorEntity);
    }

    public AuthorEntity get(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
}

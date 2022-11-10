package com.project2.musicstorerecommendations.controller;

import com.google.inject.internal.cglib.core.$CodeGenerationException;
import com.project2.musicstorerecommendations.model.AlbumRecommendation;
import com.project2.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumrecommendation")
public class AlbumRecommendationController {

    @Autowired
    AlbumRecommendationRepository repo;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAllAlbumRecommendationById(@PathVariable Long id) {
        Optional<AlbumRecommendation> returnVal = repo.findById(id);
        return returnVal.isPresent() ? returnVal.get() : null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecomm) {
        return repo.save(albumRecomm);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecomm) {
        if(albumRecomm.getId() != null){
            repo.save(albumRecomm);
        } else {
            throw new IllegalArgumentException("album Recommendation id not present");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

package com.project2.musicstorerecommendations.controller;

import com.project2.musicstorerecommendations.model.ArtistRecommendation;
import com.project2.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistrecommendation")
public class ArtistRecommendationController {

    @Autowired
    ArtistRecommendationRepository repo;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getAllArtistRecommendationById(@PathVariable Long id) {
        Optional<ArtistRecommendation> returnVal = repo.findById(id);
        return returnVal.isPresent() ? returnVal.get() : null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation addArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecomm) {
        return repo.save(artistRecomm);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecomm) {

        if(artistRecomm.getId()!= null) {
            repo.save(artistRecomm);
        } else {
            throw new IllegalArgumentException("artist recommendation is not present");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

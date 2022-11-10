package com.project2.musicstorerecommendations.repository;

import com.project2.musicstorerecommendations.model.ArtistRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistRecommendationRepositoryTest {

    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;
    private ArtistRecommendation artistRecomm1;

    private ArtistRecommendation artistRecomm2;

    @Before
    public void setUp() throws Exception {
        artistRecommendationRepository.deleteAll();

        artistRecomm1 = new ArtistRecommendation(10L, 20L, true);
        artistRecomm2 = new ArtistRecommendation(20L, 30L, false);
    }

    @Test
    public void shouldCreateFindDeleteArtist() {
        ArtistRecommendation savedRecomm = artistRecommendationRepository.save(artistRecomm1);

        Optional<ArtistRecommendation> foundRecomm = artistRecommendationRepository.findById(savedRecomm.getId());

        assertTrue(foundRecomm.isPresent());
        assertEquals(savedRecomm, foundRecomm.get());

        artistRecommendationRepository.deleteById(savedRecomm.getId());

        foundRecomm = artistRecommendationRepository.findById(savedRecomm.getId());
        assertFalse(foundRecomm.isPresent());

    }

    @Test
    public void shouldFindAllArtists() {

        ArtistRecommendation savedRecomm1 = artistRecommendationRepository.save(artistRecomm1);
        ArtistRecommendation savedRecomm2 = artistRecommendationRepository.save(artistRecomm2);

        Set<ArtistRecommendation> savedRecommSet = new HashSet<>();

        savedRecommSet.add(savedRecomm1);
        savedRecommSet.add(savedRecomm2);

        Set<ArtistRecommendation> outputSet = new HashSet<>(artistRecommendationRepository.findAll());
        assertEquals(savedRecommSet.size(), outputSet.size());
        assertEquals(savedRecommSet, outputSet);

    }

    @Test
    public void shouldUpdateArtistById() {
        ArtistRecommendation savedRecomm = artistRecommendationRepository.save(artistRecomm1);

        savedRecomm.setArtistId(42L);
        savedRecomm.setUserId(42L);
        savedRecomm.setLiked(false);

        artistRecommendationRepository.save(savedRecomm);

        Optional<ArtistRecommendation> updateRecomm = artistRecommendationRepository.findById(savedRecomm.getId());
        assertEquals(savedRecomm, updateRecomm.get());
    }

}
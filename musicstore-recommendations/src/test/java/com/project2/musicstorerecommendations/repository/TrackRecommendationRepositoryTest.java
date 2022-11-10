package com.project2.musicstorerecommendations.repository;

import com.project2.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRecommendationRepositoryTest {

    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;
    private TrackRecommendation trackRecomm1;

    private TrackRecommendation trackRecomm2;

    @Before
    public void setUp() throws Exception {
        trackRecommendationRepository.deleteAll();

        trackRecomm1 = new TrackRecommendation(10L,  20L, true);
        trackRecomm2 = new TrackRecommendation(20L, 30L, false);
    }

    @Test
    public void shouldCreateFindDeleteArtist() {
        TrackRecommendation savedRecomm = trackRecommendationRepository.save(trackRecomm1);

        Optional<TrackRecommendation> foundRecomm = trackRecommendationRepository.findById(savedRecomm.getId());

        assertTrue(foundRecomm.isPresent());
        assertEquals(savedRecomm, foundRecomm.get());

        trackRecommendationRepository.deleteById(savedRecomm.getId());

        foundRecomm = trackRecommendationRepository.findById(savedRecomm.getId());
        assertFalse(foundRecomm.isPresent());

    }

    @Test
    public void shouldFindAllArtists() {

        TrackRecommendation savedRecomm1 = trackRecommendationRepository.save(trackRecomm1);
        TrackRecommendation savedRecomm2 = trackRecommendationRepository.save(trackRecomm2);

        Set<TrackRecommendation> savedRecommSet = new HashSet<>();

        savedRecommSet.add(savedRecomm1);
        savedRecommSet.add(savedRecomm2);

        Set<TrackRecommendation> outputSet = new HashSet<>(trackRecommendationRepository.findAll());
        assertEquals(savedRecommSet.size(), outputSet.size());
        assertEquals(savedRecommSet, outputSet);

    }

    @Test
    public void shouldUpdateArtistById() {
        TrackRecommendation savedRecomm = trackRecommendationRepository.save(trackRecomm1);

        savedRecomm.setTrackId(42L);
        savedRecomm.setUserId(42L);
        savedRecomm.setLiked(false);

        trackRecommendationRepository.save(savedRecomm);

        Optional<TrackRecommendation> updateRecomm = trackRecommendationRepository.findById(savedRecomm.getId());
        assertEquals(savedRecomm, updateRecomm.get());
    }
}
package com.project2.musicstorerecommendations.repository;

import com.project2.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {

    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;
    private AlbumRecommendation albumRecomm1;
    private AlbumRecommendation albumRecomm2;

    @Before
    public void setUp() throws Exception {
        albumRecommendationRepository.deleteAll();

        albumRecomm1= new AlbumRecommendation(10L,  20L, true);
        albumRecomm2 = new AlbumRecommendation(20L, 30L, false);
    }

    @Test
    public void shouldCreateFindDeleteArtist() {
        AlbumRecommendation savedRecomm = albumRecommendationRepository.save(albumRecomm1);

        Optional<AlbumRecommendation> foundRecomm = albumRecommendationRepository.findById(savedRecomm.getId());

        assertTrue(foundRecomm.isPresent());
        assertEquals(savedRecomm, foundRecomm.get());

        albumRecommendationRepository.deleteById(savedRecomm.getId());

        foundRecomm = albumRecommendationRepository.findById(savedRecomm.getId());
        assertFalse(foundRecomm.isPresent());

    }

    @Test
    public void shouldFindAllArtists() {

        AlbumRecommendation savedRecomm1 = albumRecommendationRepository.save(albumRecomm1);
        AlbumRecommendation savedRecomm2 = albumRecommendationRepository.save(albumRecomm2);

        Set<AlbumRecommendation> savedRecommSet = new HashSet<>();

        savedRecommSet.add(savedRecomm1);
        savedRecommSet.add(savedRecomm2);

        Set<AlbumRecommendation> outputSet = new HashSet<>(albumRecommendationRepository.findAll());
        assertEquals(savedRecommSet.size(), outputSet.size());
        assertEquals(savedRecommSet, outputSet);

    }

    @Test
    public void shouldUpdateArtistById() {
        AlbumRecommendation savedRecomm = albumRecommendationRepository.save(albumRecomm1);

        savedRecomm.setAlbumId(42L);
        savedRecomm.setUserId(42L);
        savedRecomm.setLiked(false);

        albumRecommendationRepository.save(savedRecomm);

        Optional<AlbumRecommendation> updateRecomm = albumRecommendationRepository.findById(savedRecomm.getId());
        assertEquals(savedRecomm, updateRecomm.get());
    }
}
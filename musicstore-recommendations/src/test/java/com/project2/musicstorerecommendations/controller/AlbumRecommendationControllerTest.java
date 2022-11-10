package com.project2.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.musicstorerecommendations.model.AlbumRecommendation;
import com.project2.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AlbumRecommendationRepository albumRecommRepo;

    private AlbumRecommendation inputRecomm1;

    private AlbumRecommendation inputRecomm2;

    private AlbumRecommendation outputRecomm1;

    private AlbumRecommendation outputRecomm2;

    private List<AlbumRecommendation> albumRecommendationList;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();

        inputRecomm1= new AlbumRecommendation(10L,  20L, true);
        inputRecomm2 = new AlbumRecommendation(20L, 30L, false);

        outputRecomm1= new AlbumRecommendation(10L,  20L, true);
        outputRecomm1.setId(1L);

        outputRecomm2 = new AlbumRecommendation(20L, 30L, false);
        outputRecomm2.setId(2L);

        albumRecommendationList = new ArrayList<>();

        albumRecommendationList.add(outputRecomm1);
        albumRecommendationList.add(outputRecomm2);
    }

    @Test
    public void shouldReturnAllAlbumRecomm() throws Exception {

        doReturn(albumRecommendationList).when(albumRecommRepo).findAll();

        String outJson = mapper.writeValueAsString(albumRecommendationList);

        mockMvc.perform(
                        get("/albumrecommendation")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturnAlbumRecommById() throws Exception {
        when(albumRecommRepo.findById((long)1)).thenReturn(Optional.of(outputRecomm1));
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        get("/albumrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));
    }

    @Test
    public void shouldReturnNewAlbumRecomm() throws Exception {

        when(albumRecommRepo.save(inputRecomm1)).thenReturn(outputRecomm1);

        String inJson = mapper.writeValueAsString(inputRecomm1);
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        post("/albumrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturn204StatusWithUpdate() throws Exception {
        when(albumRecommRepo.save(inputRecomm2)).thenReturn(null);
        String inJson = mapper.writeValueAsString(inputRecomm2);
        mockMvc.perform(
                        put("/albumrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldFailWhenUpdateWithBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("No id found, unable to update")).when(albumRecommRepo).save(inputRecomm1);
        String inJson = mapper.writeValueAsString(inputRecomm1);
        mockMvc.perform(
                        put("/albumrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnNoContentWithDelete() throws Exception {
        doNothing().when(albumRecommRepo).deleteById((long)1);

        mockMvc.perform(
                        delete("/albumrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
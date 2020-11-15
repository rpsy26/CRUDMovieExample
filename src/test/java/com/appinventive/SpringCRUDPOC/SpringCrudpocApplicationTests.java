package com.appinventive.SpringCRUDPOC;

import com.appinventive.SpringCRUDPOC.controller.MovieController;
import com.appinventive.SpringCRUDPOC.model.Movie;
import com.appinventive.SpringCRUDPOC.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class SpringCrudpocApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    Movie movie = new Movie("1","3Idiots","Bollywood",5);

    @Test
    public void getMovieByID() throws Exception {
        Mockito.when(movieRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.ofNullable(movie));
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/movie/1").accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult=mockMvc.perform(requestBuilder).andReturn();
        String expected = "{id:\"1\",title:\"3Idiots\",category:\"Bollywood\",rating:5.0}";
        System.out.println(mvcResult.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected,mvcResult.getResponse().getContentAsString(),false);
    }
}

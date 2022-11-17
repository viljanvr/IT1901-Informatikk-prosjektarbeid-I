package tacocalc.restapi;


import tacocalc.core.Recipe;
import tacocalc.data.RecipeFileHandler;
import tacocalc.core.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;
import com.google.gson.Gson;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = RecipecalcController.class)
@AutoConfiguration
public class RecipeCalcControllerTest {

  private final static String mockEntryAsString =
      """
          {"list": [{"name": "bacon terninger","perPersonAmount": 1.0,"measuringUnit": "stk","roundUpTo": 1.0,"bought": false}],"name": "Add-duplicate-ingredient-test","numberOfPeople": 4}
          """
          .strip();
  private final static String path = "/api/v1/recipes/";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private RecipecalcController controller;

  @MockBean
  private RecipecalcService service;

  @MockBean
  private RecipeFileHandler handler;

  @Test
  public void testGetAllRecipes() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("api/v1/recipes/"))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }


}

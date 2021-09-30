package com.appManager.controller;

import com.appManager.controller.filter.AppFilter;
import com.appManager.model.AppModel;
import com.appManager.model.AppTestBuilder;
import com.appManager.model.TypeModel;
import com.appManager.model.TypeTestBuilder;
import com.appManager.service.AppService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.appManager.utils.JSONParser.toJSON;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.appManager.controller.AppController;

@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {

    private static final String URL = "/app";

    private static final Long APP_ID = 1L;

    private static final Long TYPE_ID = 3L;

    @InjectMocks
    private AppController appController;

    @Mock
    private AppService appService;

    private MockMvc mockMvc;

    private TypeModel typeModel;

    private AppModel appModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
        mockMvc = MockMvcBuilders.standaloneSetup(appController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void createAppShouldSucceed() throws Exception {

        this.typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        this.appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        this.appModel.setType(typeModel);

        when(appService.createApp(this.appModel)).thenReturn(this.appModel);

        mockMvc.perform(post(URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSON(appModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void findByNameAndTypeShouldSucceed() throws Exception {

        this.typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        this.appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        this.appModel.setType(typeModel);

        AppFilter filter = AppFilter
                .builder()
                .name(this.appModel.getName())
                .type(this.typeModel.getName())
                .build();

        when(appService.findByNameAndType(filter)).thenReturn(Collections.singletonList(this.appModel));

        mockMvc.perform(get(URL + "/find-by-name-and-type/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCheapestByTypeShouldSucceed() throws Exception {

        this.typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(TYPE_ID)
                .build();

        this.appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .id(APP_ID)
                .build();

        this.appModel.setType(typeModel);

        when(appService.findCheaperByType(typeModel.getName())).thenReturn(appModel);

        mockMvc.perform(get(URL + "/cheapest-by-type/" + typeModel.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

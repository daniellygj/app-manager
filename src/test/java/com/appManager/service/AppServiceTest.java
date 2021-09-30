package com.appManager.service;

import com.appManager.controller.filter.AppFilter;
import com.appManager.model.AppModel;
import com.appManager.model.AppTestBuilder;
import com.appManager.model.TypeModel;
import com.appManager.model.TypeTestBuilder;
import com.appManager.repository.AppRepository;
import com.appManager.repository.TypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppServiceTest {

    private static final Long APP_ID = 1L;

    private static final Long TYPE_ID = 3L;

    @InjectMocks
    private AppService appService;

    @Mock
    private AppRepository appRepository;

    @Mock
    private TypeRepository typeRepository;

    private TypeModel typeModel;

    private AppModel appModel;

    @Test
    public void addNewAppWithoutTypeSavedShouldSucceed() {
        TypeModel typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        AppModel appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        when(appRepository.save(appModel)).thenReturn(appModel);

        AppModel appModelSaved = appService.createApp(appModel);

        assertEquals(appModel.getId(), appModelSaved.getId());
        assertEquals(appModel.getName(), appModelSaved.getName());
        assertEquals(appModel.getPrice(), appModelSaved.getPrice());
    }

    @Test
    public void addNewAppWithTypeSavedShouldSucceed() {
        TypeModel typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        AppModel appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        when(appRepository.save(appModel)).thenReturn(appModel);
        when(typeRepository.findByName(typeModel.getName())).thenReturn(typeModel);

        AppModel appModelSaved = appService.createApp(appModel);

        assertEquals(appModel.getId(), appModelSaved.getId());
        assertEquals(appModel.getName(), appModelSaved.getName());
        assertEquals(appModel.getPrice(), appModelSaved.getPrice());
    }

    @Test
    public void findByNameAndTypeShouldSucceed() {
        TypeModel typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        AppModel appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        AppFilter filter = AppFilter
                .builder()
                .name(appModel.getName())
                .type(typeModel.getName())
                .build();

//        final Pageable pageable = PageRequest.of(0, 15);
//        final Page<AppModel> page = new PageImpl<AppModel>(Collections.singletonList(appModel));

        when(appRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(appModel));

        List<AppModel> result = appService.findByNameAndType(filter);

        assertEquals(1, result.size());
        assertEquals(appModel.getPrice(), result.get(0).getPrice());
        assertEquals(appModel.getName(), result.get(0).getName());
        assertEquals(appModel.getType(), result.get(0).getType());
    }

    @Test
    public void findCheaperByTypeShouldSucceed() {
        TypeModel typeModel = TypeTestBuilder
                .init()
                .withDefaultValues()
                .id(APP_ID)
                .build();

        AppModel appModel = AppTestBuilder
                .init()
                .withDefaultValues(typeModel)
                .build();

        when(appRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(appModel));


        AppModel result = appService.findCheaperByType(typeModel.getName());

        assertEquals(appModel.getPrice(), result.getPrice());
        assertEquals(appModel.getName(), result.getName());
        assertEquals(appModel.getType(), result.getType());
    }
}

package com.appManager.service;

import com.appManager.controller.filter.AppFilter;
import com.appManager.model.AppModel;
import com.appManager.model.TypeModel;
import com.appManager.repository.AppRepository;
import com.appManager.repository.TypeRepository;
import com.appManager.repository.filterQuery.AppFilterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private TypeRepository typeRepository;

    public AppModel createApp(AppModel appModel) {
        String typeName = appModel.getType().getName().toLowerCase();
        appModel.getType().setName(typeName);

        TypeModel type = typeRepository.findByName(typeName);
        if (type != null) {
            appModel.setType(type);
        }

        return appRepository.save(appModel);
    }

    public List<AppModel> findByNameAndType(AppFilter filter) {
        return appRepository.findAll(new AppFilterQuery(filter).buildFilter());
    }

    public AppModel findCheaperByType(String type) {
        AppFilter filter = new AppFilter();
        filter.setType(type);
        return appRepository.findAll(new AppFilterQuery(filter).buildFilter()).get(0);
    }
}

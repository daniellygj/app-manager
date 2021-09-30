package com.appManager.model;

import com.appManager.model.AppModel.AppModelBuilder;
import liquibase.pro.packaged.L;

public class AppTestBuilder {

    private static final String DEFAULT_NAME = "app-test";

    private static final Float DEFAULT_PRICE = 5.80f;

    public static AppTestBuilder init() {
        return new AppTestBuilder();
    }

    public AppModelBuilder withDefaultValues(TypeModel typeModel) {
        return AppModel.builder()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .type(typeModel);
    }
}

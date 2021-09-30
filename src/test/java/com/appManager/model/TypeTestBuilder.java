package com.appManager.model;
import com.appManager.model.TypeModel.TypeModelBuilder;
public class TypeTestBuilder {

    private static final String DEFAULT_NAME = "type-test";

    public static TypeTestBuilder init() {
        return new TypeTestBuilder();
    }

    public TypeModelBuilder withDefaultValues() {
        return TypeModel.builder()
                .name(DEFAULT_NAME);
    }
}

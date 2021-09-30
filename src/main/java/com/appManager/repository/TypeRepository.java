package com.appManager.repository;

import com.appManager.model.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository  extends JpaRepository<TypeModel, Long>, JpaSpecificationExecutor<TypeModel> {

    TypeModel findByName(String typeName);

}

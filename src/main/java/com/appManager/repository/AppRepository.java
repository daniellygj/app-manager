package com.appManager.repository;

import com.appManager.model.AppModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository  extends JpaRepository<AppModel, Long>, JpaSpecificationExecutor<AppModel> {

}

package org.ud.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ud.springboot.entity.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}

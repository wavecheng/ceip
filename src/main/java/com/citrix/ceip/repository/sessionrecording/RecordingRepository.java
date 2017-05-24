package com.citrix.ceip.repository.sessionrecording;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.sessionrecording.Recording;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, String> {

}

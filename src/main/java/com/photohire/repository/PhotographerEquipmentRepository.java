package com.photohire.repository;

import com.photohire.entity.PhotographerEquipment;
import com.photohire.entity.PhotographerProfile;
import com.photohire.enums.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotographerEquipmentRepository
        extends JpaRepository<PhotographerEquipment, Long> {

    List<PhotographerEquipment> findByPhotographerProfile(PhotographerProfile profile);

    List<PhotographerEquipment> findByEquipmentType(EquipmentType equipmentType);

}
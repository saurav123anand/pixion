package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}

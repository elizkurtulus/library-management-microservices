package com.turkcell.fine_services.repository;

import com.turkcell.fine_services.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FineRepository extends JpaRepository<Fine, UUID> {

    List<Fine> findByMemberId(UUID memberId);

    List<Fine> findByPaid(boolean paid);

    List<Fine> findByMemberIdAndPaid(UUID memberId, boolean paid);
}

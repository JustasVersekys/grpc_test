package com.telesoftas.client.repo;

import com.telesoftas.client.model.TstTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TstTableRepo extends JpaRepository<TstTable, Long> {
}

package com.amisoft.axon.poc.demo.axonpoc.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository  extends JpaRepository<TransactionHistory,Long> {


}

package com.tre.demo.payment.paymentserv.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tre.demo.payment.paymentserv.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

}

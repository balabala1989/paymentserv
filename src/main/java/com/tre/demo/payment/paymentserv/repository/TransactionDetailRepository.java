package com.tre.demo.payment.paymentserv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tre.demo.payment.paymentserv.entity.TransactionDetails;

@Repository
public interface TransactionDetailRepository extends CrudRepository<TransactionDetails, String> {


	@Query(value="select * from transaction_detail td where td.payment_id = :paymentId",nativeQuery=true)
	List<TransactionDetails> findTransactionDetailsByPaymentId(@Param("paymentId") String paymentId);
}

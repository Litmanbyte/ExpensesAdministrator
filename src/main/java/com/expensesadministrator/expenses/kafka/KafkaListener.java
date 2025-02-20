package com.expensesadministrator.expenses.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class KafkaListener implements MessageListener<String, String> {

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        System.out.println("Mensagem recebida: " + record.value());
        // Aqui você pode adicionar sua lógica de processamento
    }
    
}

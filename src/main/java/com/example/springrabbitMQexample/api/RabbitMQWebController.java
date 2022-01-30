package com.example.springrabbitMQexample.api;

import com.example.springrabbitMQexample.model.Employee;
import com.example.springrabbitMQexample.procuder.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RabbitMQWebController {

    private final RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/producer",produces = { "application/json" })
    public ResponseEntity<String> producer(@RequestBody Employee employee) {
        rabbitMQSender.send(employee);
        String message = "Message sent to the RabbitMQ" +
                " Employee id : " +employee.getEmpId() +" Employee name : " + employee.getEmpName() + " Successfully :)";
        return ResponseEntity.ok(message);
    }

}

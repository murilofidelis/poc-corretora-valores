package br.com.github.homebroker.api.controller;

import br.com.github.homebroker.api.domain.dto.TopicConfigDto;
import br.com.github.homebroker.api.service.KafkaConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaConfigController {

    private final KafkaConfigService service;

    @PutMapping("/increment/{topicName}/partitions/{numPartitions}")
    public ResponseEntity<Void> incrementPartition(@PathVariable("topicName") String topicName,
                                                   @PathVariable("numPartitions") Integer numPartitions) {
        service.incrementPartition(topicName, numPartitions);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/update/config")
    public ResponseEntity<Void> updateConfig(@RequestBody TopicConfigDto config) {
        service.updateTopicConfig(config);
        return ResponseEntity.ok().build();
    }
}

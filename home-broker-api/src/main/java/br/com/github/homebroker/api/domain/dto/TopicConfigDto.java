package br.com.github.homebroker.api.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.AlterConfigOp;

import java.util.Map;

@Getter
@Setter
public class TopicConfigDto {
    private String topic;
    private AlterConfigOp.OpType operationType;
    private Map<String, String> config;
}

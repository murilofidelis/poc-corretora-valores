package br.com.github.homebroker.api.service;

import br.com.github.homebroker.api.domain.dto.TopicConfigDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConfigService {

    private final AdminClient adminClient;

    public void incrementPartition(String topicName, int numPartitions) {
        Map<String, NewPartitions> newPartitionSet = new HashMap<>();
        newPartitionSet.put(topicName, NewPartitions.increaseTo(numPartitions));
        adminClient.createPartitions(newPartitionSet);
    }

    public void updateTopicConfig(TopicConfigDto topicConfigDto) {
        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topicConfigDto.getTopic());
        Collection<AlterConfigOp> configEntries = new ArrayList<>();
        Map<String, String> configs = topicConfigDto.getConfig();
        for (Map.Entry<String, String> topicConfig : configs.entrySet()) {
            configEntries.add(new AlterConfigOp(new ConfigEntry(topicConfig.getKey(), topicConfig.getValue()), topicConfigDto.getOperationType()));
        }
        Map<ConfigResource, Collection<AlterConfigOp>> configResourceMap = new HashMap<>();
        configResourceMap.put(configResource, configEntries);
        adminClient.incrementalAlterConfigs(configResourceMap);
    }

}

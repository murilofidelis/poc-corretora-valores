package br.com.github.order.service.serialization;

import br.com.github.order.service.domain.dto.OrderDto;
import br.com.github.order.service.domain.dto.OrderExecutionDto;
import br.com.github.order.service.domain.dto.ShareDto;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SuppressWarnings("squid:S100")
public class JsonSenders {

    private JsonSenders() {
    }

    public static Serde<OrderDto> Order() {
        JsonSerializer<OrderDto> serializer = new JsonSerializer<>();
        JsonDeserializer<OrderDto> deserializer = new JsonDeserializer<>(OrderDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<ShareDto> Share() {
        JsonSerializer<ShareDto> serializer = new JsonSerializer<>();
        JsonDeserializer<ShareDto> deserializer = new JsonDeserializer<>(ShareDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<OrderExecutionDto> OrderExecution() {
        JsonSerializer<OrderExecutionDto> serializer = new JsonSerializer<>();
        JsonDeserializer<OrderExecutionDto> deserializer = new JsonDeserializer<>(OrderExecutionDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}

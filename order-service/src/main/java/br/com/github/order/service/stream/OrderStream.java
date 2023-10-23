package br.com.github.order.service.stream;

import br.com.github.order.service.domain.dto.OrderDto;
import br.com.github.order.service.domain.dto.OrderExecutionDto;
import br.com.github.order.service.domain.dto.ShareDto;
import br.com.github.order.service.serialization.JsonSenders;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderStream {

    @Value("${spring.kafka.topics.order.name}")
    private String orderTopic;

    @Value("${spring.kafka.topics.share.name}")
    private String shareTopic;

    @Value("${spring.kafka.topics.order-execution.name}")
    private String orderExecutionTopic;

    @Bean
    public KStream<String, OrderExecutionDto> orderKsStream(StreamsBuilder streamsBuilder) {

        KStream<String, OrderDto> orderStream = streamsBuilder.stream(this.orderTopic, Consumed.with(Serdes.String(), JsonSenders.Order()))
                .selectKey((k, v) -> v.getShareCod())
                .peek((k, v) -> log.info("Consumindo tópico: {}, {}", k, v));

        KTable<String, ShareDto> shareStream = streamsBuilder.table(this.shareTopic, Consumed.with(Serdes.String(), JsonSenders.Share()));

        Joined<String, OrderDto, ShareDto> orderShareParams = Joined.with(Serdes.String(), JsonSenders.Order(), JsonSenders.Share());

        ValueJoiner<OrderDto, ShareDto, OrderExecutionDto> orderShareJoiner = (order, share) -> OrderExecutionDto.builder().order(order).share(share).build();

        KStream<String, OrderExecutionDto> orderShare = orderStream.join(shareStream, orderShareJoiner, orderShareParams);

//        shareStream.toStream().print(Printed.<String, ShareDto>toSysOut().withLabel("SHARE"));
//        orderShare.print(Printed.<String, OrderExecutionDto>toSysOut().withLabel("ORDER-EXECUTION:"));

        orderShare.to(this.orderExecutionTopic, Produced.with(Serdes.String(), JsonSenders.OrderExecution()));

        return orderShare;
    }

}

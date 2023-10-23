package br.com.github.homebroker.api.schedule;

import br.com.github.homebroker.api.domain.Share;
import br.com.github.homebroker.api.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateShareSchedule {

    @Value("${spring.kafka.topics.share.name}")
    private String shareTopic;

    private final ShareService shareService;
    private final KafkaTemplate<Object, Object> template;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    @Scheduled(fixedDelay = 60000L)
    public void updateShareSchedule() {
        List<Share> shares = shareService.gteAll();
        for (Share share : shares) {
            int percentRandom = (int) (Math.random() * 10 + 1);
            BigDecimal lastPrice = share.getLastPrice();
            BigDecimal divide = lastPrice.multiply(new BigDecimal(percentRandom)).divide(ONE_HUNDRED).setScale(2, ROUND_HALF_DOWN);
            BigDecimal newValue;
            if (isPrime(percentRandom)) {
                newValue = lastPrice.subtract(divide);
            } else {
                newValue = lastPrice.add(divide);
            }
            share.setLastPrice(newValue);
            template.send(this.shareTopic,share.getCod(), share);
            log.info("({}) - {}: {} -> {}", share.getDescription(), share.getCod(), lastPrice, share.getLastPrice());
        }
        log.info("\n\t");
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= num / 2; i++) {
            if ((num % i) == 0)
                return false;
        }
        return true;
    }

}

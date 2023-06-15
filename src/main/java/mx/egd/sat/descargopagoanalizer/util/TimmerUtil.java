package mx.egd.sat.descargopagoanalizer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TimmerUtil {

	public static LocalDateTime timmer(String topic, LocalDateTime startTime, long count, long total) {
		LocalDateTime now = LocalDateTime.now();
		long diffSecconds = startTime.until(now, ChronoUnit.SECONDS);
		if (diffSecconds > 20) {
			BigDecimal res = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_DOWN)
					.multiply(BigDecimal.valueOf(100));
			res.setScale(2);
			log.info("[{}] Avance: {} de {} : {}%", topic, count, total, res.toString());
			return now;
		} else {
			return startTime;
		}
	}

}

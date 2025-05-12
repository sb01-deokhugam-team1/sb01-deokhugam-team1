package com.codeit.duckhu.global.type;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public enum PeriodType {
  DAILY,
  WEEKLY,
  MONTHLY,
  ALL_TIME;

  public Instant toStartInstant(Instant now) {
    ZoneId zoneId = ZoneId.of("Asia/Seoul");
    ZonedDateTime nowInKST = now.atZone(zoneId);
    
    return switch (this) {
      case DAILY -> {
        // 현재 날짜의 자정(00:00:00)을 기준으로 계산
        ZonedDateTime startOfDay = nowInKST.toLocalDate().atStartOfDay(zoneId);
        yield startOfDay.toInstant();
      }
      case WEEKLY -> now.minusSeconds(60 * 60 * 24 * 7); // 1주일 전
      case MONTHLY -> now.minusSeconds(60L * 60 * 24 * 30); // 30일 전
      case ALL_TIME -> Instant.EPOCH; // 올타임
    };
  }
}

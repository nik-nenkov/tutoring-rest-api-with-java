package com.epam.training.revision;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

@Data
public class Revision {

    @JsonPropertyOrder("revision_id")
    private Integer revisionId;

    @JsonProperty("total_quantities")
    private int totalQuantities;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("revision_started")
    private Timestamp revisionStart;

    @JsonProperty("revision_ended")
    private Timestamp revisionEnd;

    public Revision(Integer revisionId, int totalQuantities, BigDecimal totalPrice, Timestamp revisionStart, Timestamp revisionEnd) {
        setRevisionId(revisionId);
        setTotalQuantities(totalQuantities);
        setTotalPrice(totalPrice.setScale(2, RoundingMode.CEILING));
        setRevisionStart(revisionStart);
        setRevisionEnd(revisionEnd);
    }

}

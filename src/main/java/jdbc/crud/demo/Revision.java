package jdbc.crud.demo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Revision {

    @JsonPropertyOrder("revision_id")
    private Integer revisionId;

    @JsonProperty("total_quantities")
    private int totalQuantities;

    @JsonProperty("total_price")
    private float totalPrice;

    @JsonProperty("revision_started")
    private Timestamp revisionStart;

    @JsonProperty("revision_ended")
    private Timestamp revisionEnd;


}

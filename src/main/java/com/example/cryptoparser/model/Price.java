package com.example.cryptoparser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "prices")
public class Price {
    @Id
    private String id;
    @SerializedName("lprice")
    private BigDecimal lastPrice;
    @SerializedName("curr1")
    private Currency firstCurrency;
    @SerializedName("curr2")
    private Currency secondCurrency;
    private transient LocalDateTime time;
}

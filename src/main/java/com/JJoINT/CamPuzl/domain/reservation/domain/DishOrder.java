package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@NoArgsConstructor
public class DishOrder extends BaseEntity {

    @Min(value = 1,message = "최소 하나 이상의 주문을 해주세요")
    private int quantity;
    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    private PubTable pubTable;

    //이건 생성자를 써도 무방함, 나는 다른 메서드이름과 초기값을 지정하려고 사용함
    public static DishOrder order(int quantity, Menu menu, PubTable pupTable){
        DishOrder dishOrder = new DishOrder();
        dishOrder.quantity = quantity;
        dishOrder.menu = menu;
        dishOrder.pubTable = pupTable;
        return dishOrder;
    }

    //카톡방에서 설명한 업데이트 메서드
    public DishOrder updateQuantity(int quantity){
        this.quantity += quantity;
        return this;
    }
}

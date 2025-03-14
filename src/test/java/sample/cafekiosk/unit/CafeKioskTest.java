package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

class CafeKioskTest {

    @Test
    void add() {
        // console 로 찍어서 사람이 테스트를 확인
        // 다른 사람이 이 테스트 코드를 봤을 때 어떤게 맞고, 어떤게 틀린지를 파악할 수 없다
        // => 수동 테스트와 자동화된 테스트를 구분해야 한다
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : "+cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : "+cafeKiosk.getBeverages().get(0).getName());
    }

}
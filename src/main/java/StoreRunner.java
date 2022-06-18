import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StoreRunner
{
    public static void main(String[] args)
    {

        Tool toolJAKR = new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID, BigDecimal.valueOf(2.99), true,
                false, false, true);
        Tool toolLADW = new Tool("LADW", ToolType.LADDER, ToolBrand.WERNER, BigDecimal.valueOf(1.99), true,
                true, false, true);
        Tool toolCHNS = new Tool("CHNS", ToolType.CHAINSAW, ToolBrand.STIHL, BigDecimal.valueOf(1.49), true,
                false, true, true);

        Map<String, Tool> toolsByToolCode = new HashMap<>();

        toolsByToolCode.put("JAKR", toolJAKR);
        toolsByToolCode.put("LADW", toolLADW);
        toolsByToolCode.put("CHNS", toolCHNS);

        RentalStore store = new RentalStore(toolsByToolCode);

        RentalAgreement rentalAgreement = store.checkout("JAKR", 2500, 20, LocalDate.now());

        System.out.println(rentalAgreement.toString());


    }
}

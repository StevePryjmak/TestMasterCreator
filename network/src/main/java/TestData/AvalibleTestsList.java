package TestData;

import java.io.Serializable;
import java.util.List;

public class AvalibleTestsList implements Serializable {
    List<TestInfoData> tests;

    public AvalibleTestsList(List<TestInfoData> tests) {
        this.tests = tests;
    }

    public List<TestInfoData> getTests() {
        return tests;
    }
}

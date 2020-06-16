package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VegetablesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vegetables.class);
        Vegetables vegetables1 = new Vegetables();
        vegetables1.setId(1L);
        Vegetables vegetables2 = new Vegetables();
        vegetables2.setId(vegetables1.getId());
        assertThat(vegetables1).isEqualTo(vegetables2);
        vegetables2.setId(2L);
        assertThat(vegetables1).isNotEqualTo(vegetables2);
        vegetables1.setId(null);
        assertThat(vegetables1).isNotEqualTo(vegetables2);
    }
}

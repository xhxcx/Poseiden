package com.nnk.springboot.integration;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@Sql({"/sql/IT_data.sql"})
public class RuleNameServiceIT {

    @Autowired
    private RuleNameServiceImpl ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void getAllIT(){
        List<RuleName> result = ruleNameService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("name 1");
    }

    @Test
    public void createRuleNameIT(){
        RuleName newRuleName = new RuleName();
        newRuleName.setName("new rule name");

        RuleName result = ruleNameService.createRuleName(newRuleName);

        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getName()).isEqualTo(newRuleName.getName());
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateRuleNameIT(){
        RuleName ruleNameToUpdate = ruleNameRepository.findById(1).orElse(new RuleName());
        ruleNameToUpdate.setName("updated name");

        RuleName result = ruleNameService.updateRuleName(ruleNameToUpdate);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("updated name");
        assertThat(ruleNameRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteRuleNameIT(){
        RuleName ruleNameToDelete = ruleNameRepository.findById(2).orElse(new RuleName());
        ruleNameService.deleteRuleName(ruleNameToDelete);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(1);
        assertThat(ruleNameRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        RuleName result = ruleNameService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("name 1");
    }

}

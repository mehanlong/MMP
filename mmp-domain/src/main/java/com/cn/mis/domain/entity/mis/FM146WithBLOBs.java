package com.cn.mis.domain.entity.mis;

public class FM146WithBLOBs extends FM146 {
    private String evaluationResults;

    private String improvementSuggestion;

    private String enclosure;

    private String employeeSelfevaluation;

    public String getEvaluationResults() {
        return evaluationResults;
    }

    public void setEvaluationResults(String evaluationResults) {
        this.evaluationResults = evaluationResults == null ? null : evaluationResults.trim();
    }

    public String getImprovementSuggestion() {
        return improvementSuggestion;
    }

    public void setImprovementSuggestion(String improvementSuggestion) {
        this.improvementSuggestion = improvementSuggestion == null ? null : improvementSuggestion.trim();
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure == null ? null : enclosure.trim();
    }

    public String getEmployeeSelfevaluation() {
        return employeeSelfevaluation;
    }

    public void setEmployeeSelfevaluation(String employeeSelfevaluation) {
        this.employeeSelfevaluation = employeeSelfevaluation == null ? null : employeeSelfevaluation.trim();
    }
}
package com.example.paperplane.domain.category.entity;

public enum CategoryName {

    IT_TREND("IT/트렌드"),
    JOB_ACTIVITY("취업/대외활동"),
    LIFE_ECONOMY("생활/경제"),
    DESIGN_TEMPLATE("디자인/템플릿"),
    EXAM_ASSIGNMENT("시험/과제"),
    OTHERS("기타");

    private final String displayName;

    CategoryName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}



package com.example.paperplane.domain.idea.entity;


public enum Category {
    IT_TREND("IT/트렌드"),
    JOB_ACTIVITY("취업/대외활동"),
    LIFE_ECONOMY("생활/경제"),
    DESIGN_TEMPLATE("디자인/템플릿"),
    EXAM_ASSIGNMENT("시험/과제"),
    OTHERS("기타");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}




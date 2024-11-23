package com.example.paperplane.domain.idea.entity;


public enum Category {
    IT_TREND("IT/트렌드"),
    JOB_ACTIVITY("대외활동"),
    DESIGN_TEMPLATE("디자인"),
    LIFE("생활"),
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




package com.letv.component.upgrade.core.upgrade;

public enum CheckUpgradeController {
    CHECK_BY_CLIENT("checkByClient"),
    CHECK_BY_SELF("checkBySelf");
    
    private String checkUpgradeController;

    private CheckUpgradeController(String category) {
        this.checkUpgradeController = category;
    }

    public String toString() {
        return this.checkUpgradeController;
    }

    public CheckUpgradeController fromString(String category) {
        CheckUpgradeController[] states = values();
        for (int i = 0; i < states.length; i++) {
            if (states[i].checkUpgradeController.equalsIgnoreCase(category)) {
                return states[i];
            }
        }
        return null;
    }
}
